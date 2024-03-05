package com.template.eazypos.service.eazypos;

import com.template.eazypos.dto.CustomerDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Customer;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.repository.CustomerRepository;
import com.template.eazypos.repository.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SalesmanRepository salesmanRepository;

    public Customer add(CustomerDTO customerDTO){
       Customer add = new Customer();
       add.setSalesman(salesmanRepository.findById(customerDTO.getId_salesman()).orElseThrow(() -> new NotFoundException("Id Salesman tidak dinemukan")));
       add.setAlamat(customerDTO.getAlamat());
       add.setEmail(customerDTO.getEmail());
       add.setNama_customer(customerDTO.getNama_customer());
       add.setTelp(customerDTO.getNot_telp());
       add.setDel_flag(1);
       add.setJenis(customerDTO.getJenis());
       return customerRepository.save(add);
    }
    public Customer get(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }
    public List<Customer> getAll(){
        return customerRepository.findAll();
    }
    public Customer edit(CustomerDTO customerDTO , Long id){
        Customer update = customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setAlamat(customerDTO.getAlamat());
        update.setEmail(customerDTO.getEmail());
        update.setNama_customer(customerDTO.getNama_customer());
        update.setTelp(customerDTO.getNot_telp());
        update.setJenis(customerDTO.getJenis());
        return customerRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id ) {
        try {
            customerRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
