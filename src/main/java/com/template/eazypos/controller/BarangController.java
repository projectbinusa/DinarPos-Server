package com.template.eazypos.controller;

import com.template.eazypos.dto.CustomerDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Barang;
import com.template.eazypos.model.Customer;
import com.template.eazypos.service.eazypos.BarangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/barang")
@CrossOrigin(origins = "http://localhost:3000")
public class BarangController {
    @Autowired
    private BarangService barangService;

    @PostMapping("/add")
    public CommonResponse<Barang> add(@RequestBody Barang barang){
        return ResponseHelper.ok( barangService.add(barang));
    }
    @GetMapping("/{id}")
    public CommonResponse <Barang> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( barangService.get(id));
    }
    @GetMapping
    public CommonResponse<List<Barang>> getAll(){
        return ResponseHelper.ok( barangService.getAll());
    }
    @PutMapping("/{id}")
    public CommonResponse<Barang> put(@PathVariable("id") Long id , @RequestBody Barang barang){
        return ResponseHelper.ok( barangService.edit(barang , id));
    }
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( barangService.delete(id));
    }
}
