package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.*;
import com.template.eazypos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ReturnPembelianService {

    @Autowired
    private TransaksiBeliRepository transaksiBeliRepository;

    @Autowired
    private BarangTransaksiBeliRepository barangTransaksiBeliRepository;

    @Autowired
    private BarangRepository barangRepository;

    @Autowired
    private StokKeluarRepository stokKeluarRepository;

    @Autowired
    private PersediaanRepository persediaanRepository;

    @Autowired
    private PersediaanAwalRepository persediaanAwalRepository;

    // Mengambil daftar transaksi pembelian untuk produk Excelcom
    public List<TransaksiBeli> getAllExcelcom() {
        return transaksiBeliRepository.findTransaksiBeliExcelcom();
    }

    // Mengambil daftar transaksi pembelian untuk produk Dinarpos
    public List<TransaksiBeli> getAllDinarpos() {
        return transaksiBeliRepository.findTransaksiBeliDinarpos();
    }

    // Mengambil transaksi pembelian berdasarkan ID
    public TransaksiBeli get(Long id) {
        return transaksiBeliRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
    }

    // Memproses pengembalian barang dari transaksi pembelian
    public TransaksiBeli returnHistoriTransaksiPembelian(Long idTransaksiBeli) {
        TransaksiBeli transaksi = transaksiBeliRepository.findById(idTransaksiBeli)
                .orElseThrow(() -> new NotFoundException("Id transaksi tidak ditemukan"));
        transaksi.setDelFlag(0);

        List<BarangTransaksiBeli> barangTransaksiList = barangTransaksiBeliRepository.findBarangTransaksiByIdTransaksi(idTransaksiBeli);

        List<StokKeluar> stokKeluarList = new ArrayList<>();
        List<Barang> barangUpdateList = new ArrayList<>();

        for (BarangTransaksiBeli barangTransaksi : barangTransaksiList) {
            Barang barang = barangRepository.findByBarcodeBarang(barangTransaksi.getBarcodeBarang())
                    .orElseThrow(() -> new NotFoundException("Barcode not found: " + barangTransaksi.getBarcodeBarang()));

            barang.setJumlahStok(barang.getJumlahStok() - barangTransaksi.getQty());
            barangUpdateList.add(barang);

            StokKeluar stokKeluar = new StokKeluar();
            stokKeluar.setBarang(barang);
            stokKeluar.setJumlahStok(String.valueOf(barangTransaksi.getQty()));
            stokKeluar.setKeteranganStokKeluar("Return barang " + transaksi.getNoFaktur());
            stokKeluarList.add(stokKeluar);
        }

        if (!stokKeluarList.isEmpty()) {
            stokKeluarRepository.saveAll(stokKeluarList);
        }

        if (!barangUpdateList.isEmpty()) {
            barangRepository.saveAll(barangUpdateList);
        }

        updatePembelianTabelPersediaan(transaksi.getTanggal(), barangTransaksiList);

        return transaksiBeliRepository.save(transaksi);
    }

    private void updatePembelianTabelPersediaan(Date date, List<BarangTransaksiBeli> barangTransaksiList) {
        Persediaan persediaan = persediaanRepository.findByTanggal(date)
                .orElseGet(() -> new Persediaan(persediaanAkhirToAwal(date), date));

        long total = 0;
        for (BarangTransaksiBeli barangTransaksi : barangTransaksiList) {
            long hargaBeli = tampilHargaBeliBarangByBarcode(barangTransaksi.getBarcodeBarang()) * barangTransaksi.getQty();
            total += hargaBeli;
        }

        persediaan.setPenjualan(String.valueOf(total));
        long p = Long.parseLong(persediaan.getBarangSiapJual());
        persediaan.setPersediaanAkhir(String.valueOf(p - total));

        persediaanRepository.save(persediaan);
    }

    private long persediaanAkhirToAwal(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        Date previousDate = calendar.getTime();

        Persediaan persediaanSebelumnya = persediaanRepository.findByTanggal(previousDate)
                .orElseThrow(() -> new NotFoundException("Persediaan sebelumnya tidak ditemukan"));

        return Long.parseLong(persediaanSebelumnya.getPersediaanAkhir());
    }

    private long tampilHargaBeliBarangByBarcode(String barcode) {
        Barang barang = barangRepository.findByBarcodeBarang(barcode)
                .orElseThrow(() -> new NotFoundException("Barcode not found: " + barcode));

        return Long.parseLong(barang.getHargaBeli());
    }
    // Menghapus transaksi pembelian berdasarkan ID
    public Map<String, Boolean> delete(Long id) {
        try {
            TransaksiBeli update = transaksiBeliRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
            update.setDelFlag(0);
            transaksiBeliRepository.save(update);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
