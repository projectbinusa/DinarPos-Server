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
public class ReturnPenjualanService {
    @Autowired
    private TransaksiRepository transaksiRepository;

    public List<Transaksi> getAllExcelcom() {
        return transaksiRepository.findTransaksiExcelcom();
    }
    public List<Transaksi> getAllDinarpos() {
        return transaksiRepository.findTransaksiDinarpos();
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
