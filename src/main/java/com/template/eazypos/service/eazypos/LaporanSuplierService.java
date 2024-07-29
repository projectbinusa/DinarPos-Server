package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.BarangTransaksiBeli;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.model.TransaksiBeli;
import com.template.eazypos.repository.BarangTransaksiBeliRepository;
import com.template.eazypos.repository.TransaksiBeliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LaporanSuplierService {
    @Autowired
    private BarangTransaksiBeliRepository barangTransaksiBeliRepository;

    @Autowired
    private TransaksiBeliRepository transaksiBeliRepository;

    // Mengambil semua barang transaksi beli dari kategori Excelcom untuk periode bulan dan tahun saat ini
    public List<BarangTransaksiBeli> getAllExcelcom() {
        int tahun = new Date().getYear() + 1900; // Tambahkan 1900 untuk mendapatkan tahun yang benar
        int bulan = new Date().getMonth() + 1; // Tambahkan 1 untuk mendapatkan bulan yang benar
        return barangTransaksiBeliRepository.findBarangTransaksiExcelcomByPeriode(bulan, tahun);
    }

    // Mengambil semua barang transaksi beli dari kategori Dinarpos untuk periode bulan dan tahun saat ini
    public List<BarangTransaksiBeli> getAllDinarpos() {
        int tahun = new Date().getYear() + 1900; // Tambahkan 1900 untuk mendapatkan tahun yang benar
        int bulan = new Date().getMonth() + 1; // Tambahkan 1 untuk mendapatkan bulan yang benar
        return barangTransaksiBeliRepository.findBarangTransaksiDinarposByPeriode(bulan, tahun);
    }

    // Mendapatkan entitas BarangTransaksiBeli berdasarkan ID
    public BarangTransaksiBeli get(Long id) {
        return barangTransaksiBeliRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
    }

    // Menghapus entitas BarangTransaksiBeli berdasarkan ID
    public Map<String, Boolean> delete(Long id) {
        try {
            BarangTransaksiBeli update = barangTransaksiBeliRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
            update.setDelFlag(0);
            barangTransaksiBeliRepository.save(update);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }

    // Mengambil daftar transaksi beli dari kategori Excelcom berdasarkan rentang tanggal dan ID suplier
    public List<TransaksiBeli> getByTanggalExcelcom(Date tanggalAwal, Date tanggalAkhir, Long suplierId) {
        String status = "excelcom";
        return transaksiBeliRepository.findByTanggal(tanggalAwal, tanggalAkhir, suplierId, status);
    }

    // Mengambil daftar transaksi beli dari kategori Dinarpos berdasarkan rentang tanggal dan ID suplier
    public List<TransaksiBeli> getByTanggalDinarpos(Date tanggalAwal, Date tanggalAkhir, Long suplierId) {
        String status = "dinarpos";
        return transaksiBeliRepository.findByTanggal(tanggalAwal, tanggalAkhir, suplierId, status);
    }
}
