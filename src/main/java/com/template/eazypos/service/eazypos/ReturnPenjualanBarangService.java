package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.*;
import com.template.eazypos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ReturnPenjualanBarangService {

    @Autowired
    private BarangTransaksiRepository barangTransaksiRepository;

    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private BarangRepository barangRepository;

    @Autowired
    private StokMasukRepository stokMasukRepository;

    @Autowired
    private PersediaanRepository persediaanRepository;

    @Autowired
    private PersediaanAwalRepository persediaanAwalRepository;

    // Mengambil daftar barang transaksi penjualan untuk produk Excelcom
    public List<BarangTransaksi> getAllExcelcom() {
        return barangTransaksiRepository.findBarangTransaksiExcelcom();
    }

    // Mengambil daftar barang transaksi penjualan untuk produk Dinarpos
    public List<BarangTransaksi> getAllDinarpos() {
        return barangTransaksiRepository.findBarangTransaksiDinarpos();
    }

    // Mengambil barang transaksi penjualan berdasarkan ID
    public BarangTransaksi get(Long id) {
        return barangTransaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
    }

    // Memproses pengembalian barang dari transaksi penjualan
    @Transactional
    public BarangTransaksi retur(Long id) {
        Optional<BarangTransaksi> barangTransaksiOptional = barangTransaksiRepository.findById(id);
        if (barangTransaksiOptional.isPresent()) {
            BarangTransaksi barangTransaksi = barangTransaksiOptional.get();
            Transaksi transaksi = transaksiRepository.findById(barangTransaksi.getTransaksi().getIdTransaksi())
                    .orElseThrow(() -> new NotFoundException("Transaksi tidak ditemukan"));

            // Mengembalikan stok barang
            int jumlahRetur = barangTransaksi.getQty();
            Barang barang = barangRepository.findByBarcode(barangTransaksi.getBarcodeBarang());
            barang.setJumlahStok(barang.getJumlahStok() + jumlahRetur);
            barangRepository.save(barang);

            // Menyimpan data ke tabel StokMasuk
            StokMasuk stokMasuk = new StokMasuk();
            stokMasuk.setDelFlag(1);
            stokMasuk.setSuplier(null);
            stokMasuk.setBarang(barang);
            stokMasuk.setJumlahStok(String.valueOf(jumlahRetur));
            stokMasuk.setKeteranganStokMasuk("Return Barang " + transaksi.getNoFaktur());
            stokMasukRepository.save(stokMasuk);

            // Menghapus barang transaksi
            barangTransaksi.setDelFlag(0);
            barangTransaksi = barangTransaksiRepository.save(barangTransaksi);

            // Perbarui tabel persediaan
            updatePenjualanTabelPersediaan(transaksi.getTanggal(), barangTransaksi);

            // Periksa apakah semua barang dalam transaksi telah diretur
            List<BarangTransaksi> allBarangTransaksi = barangTransaksiRepository.findBarangTransaksiByIdTransaksi2(transaksi.getIdTransaksi());
            boolean allBarangRetur = allBarangTransaksi.stream().allMatch(bt -> bt.getDelFlag() == 0);
            if (allBarangRetur) {
                transaksi.setDelFlag(0);
                transaksiRepository.save(transaksi);
            }

            return barangTransaksi;
        } else {
            throw new NotFoundException("Barang transaksi tidak ditemukan");
        }
    }
    private void updatePenjualanTabelPersediaan(Date date, BarangTransaksi barangTransaksi) {
        Persediaan persediaan = persediaanRepository.findByTanggal(date)
                .orElseGet(() -> new Persediaan(persediaanAkhirToAwal(date), date));

        long nominal = tampilNominalPersediaanAwalTransaksiById(barangTransaksi.getTransaksi().getIdTransaksi());
        long hargabeli = (long) (tampilHargaBeliBarangByBarcode(barangTransaksi.getBarcodeBarang()) * barangTransaksi.getQty());
        long p2 = (long) Double.parseDouble(persediaan.getPenjualan());
        long p = p2 - hargabeli;
        persediaan.setPenjualan(String.valueOf(p));

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

        return (long) Double.parseDouble(persediaanSebelumnya.getPersediaanAkhir());
    }

    private long tampilNominalPersediaanAwalTransaksiById(Long idTransaksi) {
        // Mendapatkan nominal persediaan awal berdasarkan ID transaksi
        Optional<PersediaanAwal> persediaanOptional = persediaanAwalRepository.findByIdTransaksi(idTransaksi);
        return (long) Double.parseDouble(persediaanOptional.map(PersediaanAwal::getNominal).orElseThrow(() -> new NotFoundException("Nominal persediaan awal tidak ditemukan")));
    }

    private long tampilHargaBeliBarangByBarcode(String barcode) {
        // Mendapatkan harga beli barang berdasarkan barcode
        Barang barang = barangRepository.findByBarcode(barcode);
        if (barang == null) {
            throw new NotFoundException("Barang tidak ditemukan dengan barcode: " + barcode);
        }
        return (long) Double.parseDouble(barang.getHargaBeli());
    }

    // Mengambil daftar barang yang telah diretur berdasarkan ID transaksi
    public List<BarangTransaksi> getAllBarangReturn(Long idTransaksi) {
        return barangTransaksiRepository.findBarangTransaksiReturnByIdTransaksi(idTransaksi);
    }

    // Menghapus barang transaksi penjualan berdasarkan ID
    public Map<String, Boolean> delete(Long id ) {
        try {
            barangTransaksiRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
