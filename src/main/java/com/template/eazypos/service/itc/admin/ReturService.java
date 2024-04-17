package com.template.eazypos.service.itc.admin;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Retur;
import com.template.eazypos.model.ServiceBarang;
import com.template.eazypos.repository.ReturRepository;
import com.template.eazypos.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReturService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ReturRepository returRepository;

    public Retur retur(Long id) {
        ServiceBarang retur = serviceRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Service Not Found"));
        retur.setStatusEnd("return");
        serviceRepository.save(retur);
        Retur retur1 = new Retur();
        retur1.setTanggalRetur(new Date());
        retur1.setTTLama(serviceRepository.findById(id).get());
        return returRepository.save(retur1);
    }

}
