package com.template.eazypos.service.itc;

import com.template.eazypos.dto.KabKotDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.KabKot;
import com.template.eazypos.model.Prov;
import com.template.eazypos.repository.KabKotRepository;
import com.template.eazypos.repository.ProvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KabKotService {
    @Autowired
    private KabKotRepository kabKotRepository;

    @Autowired
    private ProvRepository provRepository;

    public KabKot add(KabKotDTO kabKotDTO){
        KabKot kabKot = new KabKot();
        kabKot.setProv(provRepository.findById(kabKotDTO.getId_prov()).orElseThrow(() -> new NotFoundException("Id Prov Not Found")));
        kabKot.setNama_kabkot(kabKotDTO.getNama_kabkot());
        return kabKotRepository.save(kabKot);
    }

    public List<KabKot> getAll(){
        return kabKotRepository.findAll();
    }
    public Page<KabKot> getAllWithPagination(Long page, Long limit, String sort, Long id_prov) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (sort.startsWith("-")) {
            sort = sort.substring(1);
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(Math.toIntExact(page - 1), Math.toIntExact(limit), direction, sort);
            return kabKotRepository.findByIdProv(id_prov,pageable);

    }

    public KabKot getById(Long id){
        return kabKotRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }
    public KabKot edit(KabKotDTO kabKot , Long id){
        KabKot update = kabKotRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        update.setNama_kabkot(kabKot.getNama_kabkot());
        update.setProv(provRepository.findById(kabKot.getId_prov()).orElseThrow(() -> new NotFoundException("Id Prov Not Found")));
        return kabKotRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id ) {
        try {
            kabKotRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
