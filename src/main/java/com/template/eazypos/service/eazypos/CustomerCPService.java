package com.template.eazypos.service.eazypos;

import com.template.eazypos.dto.CustomerCPDTO;
import com.template.eazypos.dto.CustomerDTO;
import com.template.eazypos.exception.BadRequestException;
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

    // Menambahkan contact person baru untuk customer
    public CustomerCP add(CustomerCPDTO customerCPDTO){
        if (customerCPRepository.findByEmailOrTelp(customerCPDTO.getEmail(), customerCPDTO.getNo_telp()).isPresent()){
            throw new BadRequestException("Email atau no hp sudah digunakan");
        }
        CustomerCP add = new CustomerCP();
        add.setSalesman(salesmanRepository.findById(customerCPDTO.getId_salesman()).orElseThrow(() -> new NotFoundException("Id Salesman tidak dinemukan")));
        add.setCustomer(customerRepository.findById(customerCPDTO.getId_customer()).orElseThrow(() -> new NotFoundException("Id Customer tidak dinemukan")));
        add.setEmail(customerCPDTO.getEmail());
        add.setJabatan(customerCPDTO.getJabatan());
        add.setNama_cp(customerCPDTO.getNama_cp());
        add.setNo_hp(customerCPDTO.getNo_telp());
        return customerCPRepository.save(add);
    }

    // Mendapatkan contact person berdasarkan ID
    public CustomerCP get(Long id) {
        return customerCPRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }

    // Mendapatkan semua contact person
    public List<CustomerCP> getAll(){
        return customerCPRepository.findAll();
    }

    // Mengedit contact person berdasarkan ID
    public CustomerCP edit(CustomerCPDTO customerCPDTO , Long id){
        CustomerCP update = customerCPRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setEmail(customerCPDTO.getEmail());
        update.setJabatan(customerCPDTO.getJabatan());
        update.setNama_cp(customerCPDTO.getNama_cp());
        update.setNo_hp(customerCPDTO.getNo_telp());
        return customerCPRepository.save(update);
    }

    // Menghapus contact person berdasarkan ID
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
