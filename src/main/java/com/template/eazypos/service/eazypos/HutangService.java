package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Hutang;
import com.template.eazypos.repository.HutangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HutangService {
    @Autowired
    private HutangRepository hutangRepository;

    public Hutang pelunasan(Hutang hutang , Long id) {
        Hutang pelunasan = hutangRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        pelunasan.setPelunasan(hutang.getPelunasan());
       return hutangRepository.save(pelunasan);
    }
    public List<Hutang> getAll(){
        return hutangRepository.findAll();
    }

    public Hutang getById(Long id){
        return hutangRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }
}
