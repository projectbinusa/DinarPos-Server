package com.template.eazypos.service.itc;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Customer;
import com.template.eazypos.model.Prov;
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
public class ProvService {
    @Autowired
    private ProvRepository provRepository;

    public Prov add(Prov prov){
        return provRepository.save(prov);
    }

    public List<Prov> getAll(){
        return provRepository.findAll();
    }
    public Page<Prov> getAllWithPagination(Long page, Long limit, String sort) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (sort.startsWith("-")) {
            sort = sort.substring(1);
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(Math.toIntExact(page - 1), Math.toIntExact(limit), direction, sort);
            return provRepository.findAll(pageable);
    }

    public Prov getById(Long id){
        return provRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }
    public Prov edit(Prov prov , Long id){
        Prov update = provRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        update.setNamaProv(prov.getNamaProv());
        return provRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id ) {
        try {
            provRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
