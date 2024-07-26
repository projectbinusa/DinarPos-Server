package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.*;
import com.template.eazypos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ReturnPenjualanService {
    @Autowired
    private TransaksiRepository transaksiRepository;
    @Autowired
    private BarangTransaksiRepository barangTransaksiRepository;

    @Autowired
    private BarangRepository barangRepository;

    @Autowired
    private StokMasukRepository stokMasukRepository;
    @Autowired
    private PersediaanRepository persediaanRepository;

    // Mengambil daftar transaksi penjualan untuk produk Excelcom
    public List<Transaksi> getAllExcelcom() {
        return transaksiRepository.findTransaksiExcelcom();
    }

    // Mengambil daftar transaksi penjualan untuk produk Dinarpos
    public List<Transaksi> getAllDinarpos() {
        return transaksiRepository.findTransaksiDinarpos();
    }

    // Mengambil transaksi penjualan berdasarkan ID
    public Transaksi get(Long id) {
        return transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
    }

    // Memproses pengembalian transaksi penjualan
    @Transactional
    public Transaksi returnHistoriTransaksi(Long idTransaksi) {
        Transaksi transaksi = transaksiRepository.findById(idTransaksi)
                .orElseThrow(() -> new NotFoundException("Id transaksi not found"));
        transaksi.setDelFlag(0);

        List<BarangTransaksi> barangTransaksiList = barangTransaksiRepository.findBarangTransaksiByIdTransaksi2(idTransaksi);

        List<StokMasuk> stokMasukList = new ArrayList<>();
        List<Barang> barangUpdateList = new ArrayList<>();

        for (BarangTransaksi barangTransaksi : barangTransaksiList) {
            Barang barang = barangRepository.findByBarcode(barangTransaksi.getBarcodeBarang());
            if (barang != null) {
                barang.setJumlahStok(barang.getJumlahStok() + barangTransaksi.getQty());
                barangUpdateList.add(barang);

                StokMasuk stokMasuk = new StokMasuk();
                stokMasuk.setDelFlag(1);
                stokMasuk.setSuplier(null);
                stokMasuk.setBarang(barang);
                stokMasuk.setJumlahStok(String.valueOf(barangTransaksi.getQty()));
                stokMasuk.setKeteranganStokMasuk("Return Barang " + transaksi.getNoFaktur());
                stokMasukList.add(stokMasuk);
            }
            barangTransaksi.setDelFlag(0);
            barangTransaksiRepository.save(barangTransaksi);
        }

        if (!stokMasukList.isEmpty()) {
            stokMasukRepository.saveAll(stokMasukList);
        }

        if (!barangUpdateList.isEmpty()) {
            barangRepository.saveAll(barangUpdateList);
        }

        transaksiRepository.save(transaksi);

        updatePenjualanTabelPersediaan(transaksi.getTanggal());

        return transaksi;
    }

    private void updatePenjualanTabelPersediaan(Date date) {
        Persediaan persediaan = persediaanRepository.findByTanggal(date)
                .orElseGet(() -> new Persediaan(persediaanAkhirToAwal(date), date));

        List<BarangTransaksi> totalPenjualan = barangTransaksiRepository.findByTanggal(date);

        long total = totalPenjualan.stream()
                .mapToLong(BarangTransaksi::getHargaBrng)
                .sum();

        persediaan.setPenjualan(String.valueOf(total));
        long p = Long.parseLong(persediaan.getBarangSiapJual());
        long p2 = p - total;
        persediaan.setPersediaanAkhir(String.valueOf(p2));

        persediaanRepository.save(persediaan);
    }

    private long persediaanAkhirToAwal(Date date) {
        // Menghitung persediaan awal berdasarkan persediaan akhir hari sebelumnya
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        Date previousDate = calendar.getTime();

        Persediaan persediaanSebelumnya = persediaanRepository.findByTanggal(previousDate)
                .orElseThrow(() -> new NotFoundException("Persediaan sebelumnya tidak ditemukan"));

        return Long.parseLong(persediaanSebelumnya.getPersediaanAkhir());
    }

    // Menghapus transaksi penjualan berdasarkan ID
    public Map<String, Boolean> delete(Long id ) {
        try {
            Transaksi update = transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
