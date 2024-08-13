package com.template.eazypos.controller;

import com.template.eazypos.dto.SalesmanDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.service.itc.ITCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/itc")
@CrossOrigin(origins = "*")
public class ITCController {
    @Autowired
    private ITCService itcService;

    @PostMapping("/add")
    public CommonResponse<Salesman> add(@RequestBody SalesmanDTO salesmanDTO){
        return ResponseHelper.ok( itcService.add(salesmanDTO));
    }

    // Mendapatkan Bon Barang Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse <Salesman> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( itcService.getById(id));
    }

    // Mendapatkan Semua Bon Barang
    @GetMapping
    public CommonResponse<List<Salesman>> getAll(){
        return ResponseHelper.ok( itcService.getAll());
    }

    // Mengedit Bon Barang Berdasarkan ID
    @PutMapping("/{id}")
    public CommonResponse<Salesman> put(@PathVariable("id") Long id , @RequestBody Salesman salesman){
        return ResponseHelper.ok( itcService.put( id , salesman));
    }

    // Menghapus Bon Barang Berdasarkan ID
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( itcService.delete(id));
    }
}
