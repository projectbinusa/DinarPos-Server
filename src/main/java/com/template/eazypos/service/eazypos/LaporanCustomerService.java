package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LaporanCustomerService {
    @Autowired
    private TransaksiRepository transaksiRepository;

    public List<Transaksi> getAllExelcom(int bulan) {
        return transaksiRepository.findTransaksiExcelcomByPeriode(bulan);
    }
    public List<Transaksi> getAllDinarpos(int bulan) {
        return transaksiRepository.findTransaksiDinarposByPeriode(bulan);
    }

    public Transaksi get(Long id) {
        return transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
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
}
