package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.BarangTransaksi;
import com.template.eazypos.model.BarangTransaksiBeli;
import com.template.eazypos.repository.BarangTransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReturnPenjualanBarangService {

    @Autowired
    private BarangTransaksiRepository barangTransaksiRepository;

    public List<BarangTransaksi> getAllExcelcom() {
        return barangTransaksiRepository.findBarangTransaksiExcelcom();
    }
    public List<BarangTransaksi> getAllDinarpos() {
        return barangTransaksiRepository.findBarangTransaksiDinarpos();
    }

    public BarangTransaksi get(Long id) {
        return barangTransaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }

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
