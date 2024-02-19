package com.template.eazypos.controller;

import com.template.eazypos.dto.CustomerDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Customer;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.service.eazypos.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/add")
    public CommonResponse<Customer> add(@RequestBody CustomerDTO customerDTO){
        return ResponseHelper.ok( customerService.add(customerDTO));
    }
    @GetMapping("/{id}")
    public CommonResponse <Customer> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( customerService.get(id));
    }
    @GetMapping
    public CommonResponse<List<Customer>> getAll(){
        return ResponseHelper.ok( customerService.getAll());
    }
    @PutMapping("/{id}")
    public CommonResponse<Customer> put(@PathVariable("id") Long id , @RequestBody CustomerDTO customerDTO){
        return ResponseHelper.ok( customerService.edit(customerDTO , id));
    }
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( customerService.delete(id));
    }
}
