package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Suplier;
import com.template.eazypos.repository.SuplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplierService {
    @Autowired
    private SuplierRepository suplierRepository;

    public Suplier add(Suplier supplier){
        supplier.setDelFlag(1);
        return suplierRepository.save(supplier);
    }
    public Suplier get(Long id) {
        return suplierRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }
    public List<Suplier> getAll(){
        return suplierRepository.findAllSuplier();
    }
    public Suplier edit(Suplier suplier , Long id){
        Suplier update = suplierRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setKodeSuplier(suplier.getKodeSuplier());
        update.setAlamatSuplier(suplier.getAlamatSuplier());
        update.setKeterangan(suplier.getKeterangan());
        update.setNamaSuplier(suplier.getNamaSuplier());
        update.setNoTelpSuplier(suplier.getNoTelpSuplier());
        return suplierRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id ) {
        try {
            suplierRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
