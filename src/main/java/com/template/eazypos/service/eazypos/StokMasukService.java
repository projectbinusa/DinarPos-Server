package com.template.eazypos.service.eazypos;

import com.template.eazypos.dto.CustomerDTO;
import com.template.eazypos.dto.StokMasukDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Customer;
import com.template.eazypos.model.StokMasuk;
import com.template.eazypos.repository.BarangRepository;
import com.template.eazypos.repository.StokMasukRepository;
import com.template.eazypos.repository.SuplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StokMasukService {
    @Autowired
    private StokMasukRepository stokMasukRepository;

    @Autowired
    private SuplierRepository suplierRepository;

    @Autowired
    private BarangRepository barangRepository;

    public StokMasuk add(StokMasukDTO stokMasukDTO){
        StokMasuk add = new StokMasuk();
        add.setKeteranganStokMasuk(stokMasukDTO.getKeterangan());
        add.setJumlahStok(stokMasukDTO.getJumlah_stok());
        add.setBarang(barangRepository.findById(stokMasukDTO.getId_barang()).get());
        add.setSuplier(suplierRepository.findById(stokMasukDTO.getId_suplier()).get());
        return stokMasukRepository.save(add);
    }
    public StokMasuk get(Long id) {
        return stokMasukRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }
    public List<StokMasuk> getAll(){
        return stokMasukRepository.findAll();
    }
    public StokMasuk edit(StokMasukDTO stokMasukDTO , Long id){
        StokMasuk update = stokMasukRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setKeteranganStokMasuk(stokMasukDTO.getKeterangan());
        update.setJumlahStok(stokMasukDTO.getJumlah_stok());
        update.setBarang(barangRepository.findById(stokMasukDTO.getId_barang()).get());
        update.setSuplier(suplierRepository.findById(stokMasukDTO.getId_suplier()).get());
        return stokMasukRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id ) {
        try {
            stokMasukRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
