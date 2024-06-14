package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LaporanCustomerService {
    @Autowired
    private TransaksiRepository transaksiRepository;

    // Mendapatkan semua transaksi dari kategori Excelcom untuk periode bulan dan tahun saat ini
    public List<Transaksi> getAllExelcom() {
        int tahun = new Date().getYear() + 1900; // Tambahkan 1900 untuk mendapatkan tahun yang benar
        int bulan = new Date().getMonth() + 1; // Tambahkan 1 untuk mendapatkan bulan yang benar
        return transaksiRepository.findTransaksiExcelcomByPeriode(bulan , tahun);
    }

    // Mendapatkan semua transaksi dari kategori Dinarpos untuk periode bulan dan tahun saat ini
    public List<Transaksi> getAllDinarpos() {
        int tahun = new Date().getYear() + 1900; // Tambahkan 1900 untuk mendapatkan tahun yang benar
        int bulan = new Date().getMonth() + 1; // Tambahkan 1 untuk mendapatkan bulan yang benar
        return transaksiRepository.findTransaksiDinarposByPeriode(bulan , tahun);
    }

    // Mendapatkan entitas Transaksi berdasarkan ID
    public Transaksi get(Long id) {
        return transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
    }

    // Menghapus entitas Transaksi berdasarkan ID
    public Map<String, Boolean> delete(Long id ) {
        try {
            transaksiRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }

    // Mengambil daftar transaksi dari kategori Excelcom berdasarkan rentang tanggal dan ID customer
    public List<Transaksi> getByTanggalExcelcom(Date tanggalAwal , Date tanggalAkhir , Long idCustomer){
        String status = "excelcom";
        return transaksiRepository.findCustomerByTanggal(tanggalAwal , tanggalAkhir , idCustomer , status);
    }

    // Mengambil daftar transaksi dari kategori Dinarpos berdasarkan rentang tanggal dan ID customer
    public List<Transaksi> getByTanggalDinarpos(Date tanggalAwal , Date tanggalAkhir , Long idCustomer){
        String status = "dinarpos";
        return transaksiRepository.findCustomerByTanggal(tanggalAwal , tanggalAkhir , idCustomer , status);
    }
}
