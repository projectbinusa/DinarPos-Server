package com.template.eazypos.service.itc;

import com.template.eazypos.dto.OmzetDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Omzet;
import com.template.eazypos.repository.CustomerRepository;
import com.template.eazypos.repository.OmzetRepository;
import com.template.eazypos.repository.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OmzetService {
    @Autowired
    private OmzetRepository omzetRepository;

    @Autowired
    private SalesmanRepository salesmanRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Omzet add(OmzetDTO dto){
        Omzet omzet = new Omzet();
        omzet.setOmzet(dto.getOmzet());
        omzet.setSalesman(salesmanRepository.findById(dto.getId_salesman()).orElseThrow(() -> new NotFoundException("Id Salesman Not Found")));
        omzet.setCustomer(customerRepository.findById(dto.getId_customer()).orElseThrow(() -> new NotFoundException("Id Customer Not Found")));
        omzet.setTgl(dto.getTgl());
        return omzetRepository.save(omzet);
    }
    public Omzet getById(Long id){
        return omzetRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }
    public List<Omzet> getAll(){
        return omzetRepository.findAll();
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            omzetRepository.deleteById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
        }
    }
    public List<Omzet> getByBulanTahun(int bulan , int tahun){
        return omzetRepository.findByBulanTahun(bulan,tahun);
    }
    public List<Omzet> getByBulanTahunSalesman(int bulan , int tahun , Long id){
        return omzetRepository.findByBulanTahunSalesman(bulan, tahun, id);
    }
}
