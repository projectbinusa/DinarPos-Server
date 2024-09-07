package com.template.eazypos.service.itc;

import com.template.eazypos.dto.KecDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Kec;
import com.template.eazypos.repository.KabKotRepository;
import com.template.eazypos.repository.KecRepository;
import com.template.eazypos.repository.ProvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KecService {
    @Autowired
    private KecRepository kecRepository;
    @Autowired
    private KabKotRepository kabKotRepository;

    @Autowired
    private ProvRepository provRepository;

    public Kec add(KecDTO kecDTO){
        Kec kec = new Kec();
        kec.setProv(provRepository.findById(kecDTO.getId_prov()).orElseThrow(() -> new NotFoundException("Id Prov Not Found")));
        kec.setKabKot(kabKotRepository.findById(kecDTO.getId_kabkot()).orElseThrow(() -> new NotFoundException("Id Kab Kot Not Found")));
        kec.setNama_kec(kecDTO.getNama_kec());
        return kecRepository.save(kec);
    }

    public List<Kec> getAll(){
        return kecRepository.findAll();
    }

    public Kec getById(Long id){
        return kecRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }
    public Kec edit(KecDTO kec , Long id){
        Kec update = kecRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        update.setNama_kec(kec.getNama_kec());
        update.setKabKot(kabKotRepository.findById(kec.getId_kabkot()).orElseThrow(() -> new NotFoundException("Id Kab Kot Not Found")));
        update.setProv(provRepository.findById(kec.getId_prov()).orElseThrow(() -> new NotFoundException("Id Prov Not Found")));
        return kecRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id ) {
        try {
            kecRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
