package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.repository.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalesmanService {
    @Autowired
    private SalesmanRepository salesmanRepository;

    public Salesman add(Salesman salesman){
        salesman.setDelFlag(1);
        return salesmanRepository.save(salesman);
    }
    public Salesman get(Long id) {
        return salesmanRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }
    public List<Salesman> getAll(){
        return salesmanRepository.findAll();
    }
    public Salesman edit(Salesman salesman , Long id){
        Salesman update = salesmanRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setAlamatSalesman(salesman.getAlamatSalesman());
        update.setNamaSalesman(salesman.getNamaSalesman());
        update.setNoTelpSalesman(salesman.getNoTelpSalesman());
        return salesmanRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id ) {
        try {
            salesmanRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
