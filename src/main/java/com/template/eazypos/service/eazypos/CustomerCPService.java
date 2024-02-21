package com.template.eazypos.service.eazypos;

import com.template.eazypos.dto.CustomerCPDTO;
import com.template.eazypos.dto.CustomerDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Customer;
import com.template.eazypos.model.CustomerCP;
import com.template.eazypos.repository.CustomerCPRepository;
import com.template.eazypos.repository.CustomerRepository;
import com.template.eazypos.repository.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerCPService {
    @Autowired
    private CustomerCPRepository customerCPRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SalesmanRepository salesmanRepository;

    public CustomerCP add(CustomerCPDTO customerCPDTO){
        CustomerCP add = new CustomerCP();
        add.setSalesman(salesmanRepository.findById(customerCPDTO.getId_salesman()).orElseThrow(() -> new NotFoundException("Id Salesman tidak dinemukan")));
        add.setCustomer(customerRepository.findById(customerCPDTO.getId_customer()).orElseThrow(() -> new NotFoundException("Id Customer tidak dinemukan")));
        add.setEmail(customerCPDTO.getEmail());
        add.setJabatan(customerCPDTO.getJabatan());
        add.setNama_cp(customerCPDTO.getNama_cp());
        add.setNo_hp(customerCPDTO.getNo_telp());
        return customerCPRepository.save(add);
    }
    public CustomerCP get(Long id) {
        return customerCPRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }
    public List<CustomerCP> getAll(){
        return customerCPRepository.findAll();
    }
    public CustomerCP edit(CustomerCPDTO customerCPDTO , Long id){
        CustomerCP update = customerCPRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setSalesman(salesmanRepository.findById(customerCPDTO.getId_salesman()).orElseThrow(() -> new NotFoundException("Id Salesman tidak dinemukan")));
        update.setCustomer(customerRepository.findById(customerCPDTO.getId_customer()).orElseThrow(() -> new NotFoundException("Id Customer tidak dinemukan")));
        update.setEmail(customerCPDTO.getEmail());
        update.setJabatan(customerCPDTO.getJabatan());
        update.setNama_cp(customerCPDTO.getNama_cp());
        update.setNo_hp(customerCPDTO.getNo_telp());
        return customerCPRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id ) {
        try {
            customerCPRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
