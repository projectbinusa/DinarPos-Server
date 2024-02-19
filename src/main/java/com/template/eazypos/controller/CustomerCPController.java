package com.template.eazypos.controller;

import com.template.eazypos.dto.CustomerCPDTO;
import com.template.eazypos.dto.CustomerDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Customer;
import com.template.eazypos.model.CustomerCP;
import com.template.eazypos.service.eazypos.CustomerCPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer/cp")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerCPController {
    @Autowired
    private CustomerCPService customerCPService;

    @PostMapping("/add")
    public CommonResponse<CustomerCP> add(@RequestBody CustomerCPDTO customerDTO){
        return ResponseHelper.ok( customerCPService.add(customerDTO));
    }
    @GetMapping("/{id}")
    public CommonResponse <CustomerCP> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( customerCPService.get(id));
    }
    @GetMapping
    public CommonResponse<List<CustomerCP>> getAll(){
        return ResponseHelper.ok( customerCPService.getAll());
    }
    @PutMapping("/{id}")
    public CommonResponse<CustomerCP> put(@PathVariable("id") Long id , @RequestBody CustomerCPDTO customerDTO){
        return ResponseHelper.ok( customerCPService.edit(customerDTO , id));
    }
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( customerCPService.delete(id));
    }
}
