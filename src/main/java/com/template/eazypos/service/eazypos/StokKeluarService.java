package com.template.eazypos.service.eazypos;

import com.template.eazypos.dto.StokKeluarDTO;
import com.template.eazypos.dto.StokMasukDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.StokKeluar;
import com.template.eazypos.model.StokMasuk;
import com.template.eazypos.repository.BarangRepository;
import com.template.eazypos.repository.StokKeluarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StokKeluarService {
    @Autowired
    private StokKeluarRepository stokKeluarRepository;

    @Autowired
    private BarangRepository barangRepository;

    public StokKeluar add(StokKeluarDTO stokKeluarDTO) {
        StokKeluar add = new StokKeluar();
        add.setKeteranganStokKeluar(stokKeluarDTO.getKeterangan());
        add.setJumlahStok(stokKeluarDTO.getJumlah_stok());
        add.setBarang(barangRepository.findById(stokKeluarDTO.getId_barang()).get());
        return stokKeluarRepository.save(add);
    }

    public StokKeluar get(Long id) {
        return stokKeluarRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }

    public List<StokKeluar> getAll() {
        return stokKeluarRepository.findAll();
    }

    public StokKeluar edit(StokKeluarDTO stokKeluarDTO, Long id) {
        StokKeluar update = stokKeluarRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setKeteranganStokKeluar(stokKeluarDTO.getKeterangan());
        update.setJumlahStok(stokKeluarDTO.getJumlah_stok());
        update.setBarang(barangRepository.findById(stokKeluarDTO.getId_barang()).get());
        return stokKeluarRepository.save(update);
    }

    public Map<String, Boolean> delete(Long id) {
        try {
            stokKeluarRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
