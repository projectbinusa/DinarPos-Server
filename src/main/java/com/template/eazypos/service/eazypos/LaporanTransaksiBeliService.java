package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.model.TransaksiBeli;
import com.template.eazypos.repository.TransaksiBeliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LaporanTransaksiBeliService {
    @Autowired
    private TransaksiBeliRepository transaksiBeliRepository;

    public List<TransaksiBeli> getAllExelcom() {
        int tahun = new Date().getYear() + 1900; // Tambahkan 1900 untuk mendapatkan tahun yang benar
        int bulan = new Date().getMonth() + 1; // Tambahkan 1 untuk mendapatkan bulan yang benar
        return transaksiBeliRepository.findTransaksiBeliExcelcomByPeriode(bulan , tahun);
    }
    public List<TransaksiBeli> getAllDinarpos( ) {
        int tahun = new Date().getYear() + 1900; // Tambahkan 1900 untuk mendapatkan tahun yang benar
        int bulan = new Date().getMonth() + 1; // Tambahkan 1 untuk mendapatkan bulan yang benar
        return transaksiBeliRepository.findTransaksiBeliDinarposByPeriode(bulan , tahun);
    }

    public TransaksiBeli get(Long id) {
        return transaksiBeliRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }

    public TransaksiBeli edit(Long id) {
        TransaksiBeli update = transaksiBeliRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setDelFlag(0);
        return transaksiBeliRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id ) {
        try {
            transaksiBeliRepository.deleteById(id);
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
