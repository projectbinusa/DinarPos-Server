package com.template.eazypos.service.eazypos;

import com.template.eazypos.dto.CustomerDTO;
import com.template.eazypos.exception.BadRequestException;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Customer;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.repository.CustomerRepository;
import com.template.eazypos.repository.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    // Menambahkan customer baru
    public Customer add(CustomerDTO customerDTO){
       if (customerRepository.findByEmailOrTelp(customerDTO.getEmail(), customerDTO.getNot_telp()).isPresent()){
           throw new BadRequestException("Customer sudah ada");
       }
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

    // Mendapatkan customer berdasarkan ID
    public Customer get(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }

    // Mendapatkan semua customer
    public List<Customer> getAll(){
        return customerRepository.findAll();
    }

    // Mengedit customer berdasarkan ID
    public Customer edit(CustomerDTO customerDTO , Long id){
        Customer update = customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setAlamat(customerDTO.getAlamat());
        update.setEmail(customerDTO.getEmail());
        update.setNama_customer(customerDTO.getNama_customer());
        update.setTelp(customerDTO.getNot_telp());
        update.setJenis(customerDTO.getJenis());
        return customerRepository.save(update);
    }

    // Menghapus customer berdasarkan ID
    public Map<String, Boolean> delete(Long id ) {
        try {
            Customer update = customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
            update.setDel_flag(0);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }

    // Mendapatkan semua customer dengan pagination
    public Page<Customer> getAllWithPagination(Long page, Long limit, String sort, String search) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (sort.startsWith("-")) {
            sort = sort.substring(1);
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(Math.toIntExact(page - 1), Math.toIntExact(limit), direction, sort);
            if (search != null && !search.isEmpty()) {
                return customerRepository.findAllByKeyword(search, pageable);
            } else {
                return customerRepository.findAll(pageable);
            }
    }
}
