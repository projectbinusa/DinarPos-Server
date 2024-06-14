package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Barang;
import com.template.eazypos.model.BarangTransaksi;
import com.template.eazypos.model.BarangTransaksiBeli;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.repository.BarangRepository;
import com.template.eazypos.repository.BarangTransaksiRepository;
import com.template.eazypos.repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReturnPenjualanBarangService {

    @Autowired
    private BarangTransaksiRepository barangTransaksiRepository;

    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private BarangRepository barangRepository;

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
    public BarangTransaksi retur(Long id){
        Optional<BarangTransaksi> barangTransaksiOptional = barangTransaksiRepository.findById(id);
        if (barangTransaksiOptional.isPresent()) {
            BarangTransaksi barangTransaksi = barangTransaksiOptional.get();
            Transaksi transaksi = transaksiRepository.findById(barangTransaksi.getTransaksi().getIdTransaksi())
                    .orElseThrow(() -> new NotFoundException("Transaksi tidak ditemukan"));

            // Mengembalikan stok barang
            // Ambil jumlah barang yang diretur
            int jumlahRetur = barangTransaksi.getQty();
            Barang barang = barangRepository.findByBarcode(barangTransaksi.getBarcodeBarang());
            barang.setJumlahStok(barang.getJumlahStok() - jumlahRetur);
            // Simpan perubahan stok barang
            barangRepository.save(barang);

            // Menghapus barang transaksi
            barangTransaksi.setDelFlag(0);
            barangTransaksi = barangTransaksiRepository.save(barangTransaksi);

            // Periksa apakah semua barang dalam transaksi telah diretur
            List<BarangTransaksi> allBarangTransaksi = barangTransaksiRepository.findBarangTransaksiByIdTransaksi2(transaksi.getIdTransaksi());
            boolean allBarangRetur = allBarangTransaksi.stream().allMatch(bt -> bt.getDelFlag() == 0);
            if (allBarangRetur) {
                // Jika semua barang telah diretur, atur delFlag transaksi menjadi 0
                transaksi.setDelFlag(0);
                transaksiRepository.save(transaksi);
            }

            return barangTransaksi;
        } else {
            throw new NotFoundException("Barang transaksi tidak ditemukan");
        }
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
