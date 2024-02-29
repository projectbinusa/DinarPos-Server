package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.BarangTransaksiBeli;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.repository.BarangTransaksiBeliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LaporanSuplierService {
    @Autowired
    private BarangTransaksiBeliRepository barangTransaksiBeliRepository;

    public List<BarangTransaksiBeli> getAllExcelcom(int bulan) {
        return barangTransaksiBeliRepository.findBarangTransaksiExcelcomByPeriode(bulan);
    }
    public List<BarangTransaksiBeli> getAllDinarpos(int bulan) {
        return barangTransaksiBeliRepository.findBarangTransaksiDinarposByPeriode(bulan);
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
}
