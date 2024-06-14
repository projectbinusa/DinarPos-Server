package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.BarangTransaksi;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.repository.BarangTransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LaporanBarangService {
    @Autowired
    private BarangTransaksiRepository barangTransaksiRepository;

    // Mendapatkan semua barang transaksi dari kategori Excelcom untuk periode bulan dan tahun
    public List<BarangTransaksi> getAllExcelcom() {
        int tahun = new Date().getYear() + 1900; // Tambahkan 1900 untuk mendapatkan tahun yang benar
        int bulan = new Date().getMonth() + 1; // Tambahkan 1 untuk mendapatkan bulan yang benar
        return barangTransaksiRepository.findBarangTransaksiExcelcomByPeriode(bulan , tahun);
    }

    // Mendapatkan semua barang transaksi dari kategori Dinarpos untuk periode bulan dan tahun
    public List<BarangTransaksi> getAllDinarpos() {
        int tahun = new Date().getYear() + 1900; // Tambahkan 1900 untuk mendapatkan tahun yang benar
        int bulan = new Date().getMonth() + 1; // Tambahkan 1 untuk mendapatkan bulan yang benar
        return barangTransaksiRepository.findBarangTransaksiDinarposByPeriode(bulan , tahun);
    }

    // Mendapatkan entitas BarangTransaksi berdasarkan ID
    public BarangTransaksi get(Long id) {
        return barangTransaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }

    // Mengedit entitas BarangTransaksi dengan mengubah nilai delFlag menjadi 0
    public BarangTransaksi edit(Long id) {
        BarangTransaksi update = barangTransaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setDelFlag(0);
        return barangTransaksiRepository.save(update);
    }

    // Menghapus entitas BarangTransaksi berdasarkan ID
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

    // Mengambil daftar barang transaksi dari kategori Excelcom berdasarkan rentang tanggal dan barcode
    public List<BarangTransaksi> getByTanggalExcelcom(Date tanggalAwal , Date tanggalAkhir , String barcode){
        String  status = "excelcom";
        return barangTransaksiRepository.findByTanggal(tanggalAwal , tanggalAkhir , barcode , status);
    }

    // Mengambil daftar barang transaksi dari kategori Dinarpos berdasarkan rentang tanggal dan barcode
    public List<BarangTransaksi> getByTanggalDinarpos(Date tanggalAwal , Date tanggalAkhir , String barcode){
        String  status = "dinarpos";
        return barangTransaksiRepository.findByTanggal(tanggalAwal , tanggalAkhir , barcode , status);
    }
}
