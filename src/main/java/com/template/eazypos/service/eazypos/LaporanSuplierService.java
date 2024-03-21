package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.BarangTransaksi;
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

    public List<BarangTransaksiBeli> getAllExcelcom() {
        int tahun = new Date().getYear();
        int bulan = new Date().getMonth();
        return barangTransaksiBeliRepository.findBarangTransaksiExcelcomByPeriode(bulan ,tahun);
    }
    public List<BarangTransaksiBeli> getAllDinarpos() {
        int tahun = new Date().getYear();
        int bulan = new Date().getMonth();
        return barangTransaksiBeliRepository.findBarangTransaksiDinarposByPeriode(bulan, tahun);
    }

    public BarangTransaksiBeli get(Long id) {
        return barangTransaksiBeliRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }

    public Map<String, Boolean> delete(Long id ) {
        try {
            barangTransaksiBeliRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
    public List<TransaksiBeli> getByTanggalExcelcom(Date tanggalAwal , Date tanggalAkhir , Long suplierId){
        String  status = "excelcom";
        return transaksiBeliRepository.findByTanggal(tanggalAwal , tanggalAkhir , suplierId , status);
    }
    public List<TransaksiBeli> getByTanggalDinarpos(Date tanggalAwal , Date tanggalAkhir , Long suplierId){
        String  status = "dinarpos";
        return transaksiBeliRepository.findByTanggal(tanggalAwal , tanggalAkhir , suplierId , status);
    }
}
