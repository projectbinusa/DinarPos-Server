package com.template.eazypos.controller;

import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.SaldoAwalShift;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.service.eazypos.KasHarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/kas_harian")
@CrossOrigin(origins = "*")
public class KasHarianController {
    @Autowired
    private KasHarianService kasHarianService;

    @PostMapping("/add")
    public CommonResponse<SaldoAwalShift> add(@RequestBody SaldoAwalShift shift) {
        return ResponseHelper.ok(kasHarianService.add(shift));
    }

    // Endpoint Untuk Mendapatkan Detail SaldoAwalShift Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse<SaldoAwalShift> get(@PathVariable("id") Long id) {
        return ResponseHelper.ok(kasHarianService.getById(id));
    }

    // Endpoint Untuk Mendapatkan Semua SaldoAwalShift
    @GetMapping
    public CommonResponse<List<SaldoAwalShift>> getAll() {
        return ResponseHelper.ok(kasHarianService.getAll());
    }

    @DeleteMapping("delete/{id}")
    public CommonResponse<?> delete(@PathVariable("id") Long id) {
        return ResponseHelper.ok(kasHarianService.delete(id));
    }
    @PutMapping("/update/{id}")
    public CommonResponse<SaldoAwalShift> put(@PathVariable("id") Long id, @RequestBody SaldoAwalShift shift) {
        return ResponseHelper.ok(kasHarianService.edit(shift, id));
    }

}
