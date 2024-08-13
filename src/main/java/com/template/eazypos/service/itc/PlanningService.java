package com.template.eazypos.service.itc;

import com.template.eazypos.model.Planning;
import com.template.eazypos.repository.PlanningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Service
public class PlanningService {
    @Autowired
    private PlanningRepository planningRepository;

    public List<Planning> getAll(){
        return planningRepository.findAll();
    }
    public List<Planning> getByTanggal(Date tglAwal , Date tglAkhir){
        return planningRepository.findByTgl(tglAwal , tglAkhir);
    }
    public List<Planning> getByCustomer(Long id){
        return planningRepository.findByIdCustomer(id);
    }
}
