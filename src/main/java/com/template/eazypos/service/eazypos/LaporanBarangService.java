package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.BarangTransaksi;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.repository.BarangTransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LaporanBarangService {
    @Autowired
    private BarangTransaksiRepository barangTransaksiRepository;

    public List<BarangTransaksi> getAllExcelcom(int bulan) {
        return barangTransaksiRepository.findBarangTransaksiExcelcomByPeriode(bulan);
    }
    public List<BarangTransaksi> getAllDinarpos(int bulan) {
        return barangTransaksiRepository.findBarangTransaksiExcelcomByPeriode(bulan);
    }

    public BarangTransaksi get(Long id) {
        return barangTransaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }

    public BarangTransaksi edit(Long id) {
        BarangTransaksi update = barangTransaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setDelFlag(0);
        return barangTransaksiRepository.save(update);
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