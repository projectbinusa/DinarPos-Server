package com.template.eazypos.service.itc;

import com.template.eazypos.model.Deal;
import com.template.eazypos.model.Finish;
import com.template.eazypos.model.Kunjungan;
import com.template.eazypos.repository.DealRepository;
import com.template.eazypos.repository.FinishRepository;
import com.template.eazypos.repository.KunjunganRepository;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealService {
    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private FinishRepository finishRepository;

    public List<Deal> getDealPO(){
        return dealRepository.findAll();
    }
    public List<Deal> getDealPOByCustomer(Long id){
        return dealRepository.findByCustomerId(id);
    }
    public List<Finish> getDealFinish(){
        return finishRepository.findAll();
    }
    public List<Finish> getDealFinishByCustomer(Long id){
        return finishRepository.findByCustomerId(id);
    }
}
