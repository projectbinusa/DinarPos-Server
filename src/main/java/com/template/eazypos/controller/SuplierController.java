package com.template.eazypos.controller;

import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Suplier;
import com.template.eazypos.service.eazypos.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/supplier")
@CrossOrigin(origins = "http://localhost:3000")
public class SuplierController {
    @Autowired
    private SupplierService service;

    @PostMapping("/add")
    public CommonResponse<Suplier> add(@RequestBody Suplier suplier){
        return ResponseHelper.ok( service.add(suplier));
    }
    @GetMapping("/{id}")
    public CommonResponse <Suplier> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( service.get(id));
    }
    @GetMapping
    public CommonResponse<List<Suplier>> getAll(){
        return ResponseHelper.ok( service.getAll());
    }
    @PutMapping("/{id}")
    public CommonResponse<Suplier> put(@PathVariable("id") Long id , @RequestBody Suplier suplier){
        return ResponseHelper.ok( service.edit(suplier , id));
    }
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( service.delete(id));
    }
}
