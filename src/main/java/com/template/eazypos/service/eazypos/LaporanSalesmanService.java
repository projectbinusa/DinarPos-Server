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

    public List<Transaksi> getAllExelcom() {
        int tahun = new Date().getYear();
        int bulan = new Date().getMonth();
        return transaksiRepository.findTransaksiExcelcomByPeriode(bulan ,tahun);
    }
    public List<Transaksi> getAllDinarpos() {
        int tahun = new Date().getYear();
        int bulan = new Date().getMonth();
        return transaksiRepository.findTransaksiDinarposByPeriode(bulan , tahun);
    }

    public Transaksi get(Long id) {
        return transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }

    public Transaksi edit(Long id) {
        Transaksi update = transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setDelFlag(0);
        return transaksiRepository.save(update);
    }
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
    public List<Transaksi> getByTanggalExcelcom(Date tanggalAwal , Date tanggalAkhir , Long idSalesman){
        String  status = "excelcom";
        return transaksiRepository.findByTanggal(tanggalAwal , tanggalAkhir , idSalesman , status);
    }
    public List<Transaksi> getByTanggalDinarpos(Date tanggalAwal , Date tanggalAkhir , Long idSalesman){
        String  status = "dinarpos";
        return transaksiRepository.findByTanggal(tanggalAwal , tanggalAkhir , idSalesman , status);
    }
}
