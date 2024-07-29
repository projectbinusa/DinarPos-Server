package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.StokMasuk;
import com.template.eazypos.model.Suplier;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LaporanSalesmanService {
    @Autowired
    private TransaksiRepository transaksiRepository;

    // Mengambil semua transaksi dari kategori Excelcom untuk periode bulan dan tahun
    public List<Transaksi> getAllExelcom() {
        int tahun = new Date().getYear() + 1900; // Tambahkan 1900 untuk mendapatkan tahun yang benar
        int bulan = new Date().getMonth() + 1; // Tambahkan 1 untuk mendapatkan bulan yang benar
        return transaksiRepository.findTransaksiExcelcomByPeriode(bulan ,tahun);
    }

    // Mengambil semua transaksi dari kategori Dinarpos untuk periode bulan dan tahun
    public List<Transaksi> getAllDinarpos() {
        int tahun = new Date().getYear() + 1900; // Tambahkan 1900 untuk mendapatkan tahun yang benar
        int bulan = new Date().getMonth() + 1; // Tambahkan 1 untuk mendapatkan bulan yang benar
        return transaksiRepository.findTransaksiDinarposByPeriode(bulan , tahun);
    }

    // Mendapatkan entitas Transaksi berdasarkan ID
    public Transaksi get(Long id) {
        return transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }

    // Mengedit entitas Transaksi dengan mengubah flag hapus menjadi 0
    public Transaksi edit(Long id) {
        Transaksi update = transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setDelFlag(0);
        return transaksiRepository.save(update);
    }

    // Menghapus entitas Transaksi berdasarkan ID
    public Map<String, Boolean> delete(Long id ) {
        try {
            Transaksi update = transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
            update.setDelFlag(0);
            transaksiRepository.save(update);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }

    // Mengambil daftar transaksi dari kategori Excelcom berdasarkan rentang tanggal dan ID salesman
    public List<Transaksi> getByTanggalExcelcom(Date tanggalAwal , Date tanggalAkhir , Long idSalesman){
        String  status = "excelcom";
        return transaksiRepository.findByTanggal(tanggalAwal , tanggalAkhir , idSalesman , status);
    }

    // Mengambil daftar transaksi dari kategori Dinarpos berdasarkan rentang tanggal dan ID salesman
    public List<Transaksi> getByTanggalDinarpos(Date tanggalAwal , Date tanggalAkhir , Long idSalesman){
        String  status = "dinarpos";
        return transaksiRepository.findByTanggal(tanggalAwal , tanggalAkhir , idSalesman , status);
    }
}
