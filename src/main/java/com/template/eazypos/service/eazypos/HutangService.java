package com.template.eazypos.service.eazypos;

import com.template.eazypos.dto.PelunasanDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Hutang;
import com.template.eazypos.model.TransaksiBeli;
import com.template.eazypos.repository.HutangRepository;
import com.template.eazypos.repository.TransaksiBeliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HutangService {
    @Autowired
    private HutangRepository hutangRepository;

    @Autowired
    private TransaksiBeliRepository transaksiBeliRepository;

    public Hutang pelunasan(PelunasanDTO pelunasanDTO , Long id) {
        Hutang pelunasan = hutangRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        pelunasan.setPelunasan(pelunasanDTO.getPelunasan());
       return hutangRepository.save(pelunasan);
    }
    public List<TransaksiBeli> getAll(){
        return transaksiBeliRepository.findAllHutang();
    }

    public Hutang getById(Long id){
        return hutangRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }
}
