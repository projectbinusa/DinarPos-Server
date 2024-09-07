package com.template.eazypos.controller;


import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Prov;
import com.template.eazypos.service.itc.ProvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/prov")
@CrossOrigin(origins = "*")
public class ProvController {
    @Autowired
    private ProvService provService;

    @PostMapping("/add")
    public CommonResponse<Prov> addProv(@RequestBody Prov user) {
        return ResponseHelper.ok(provService.add(user));
    }

    // Endpoint Untuk Mendapatkan Detail Prov Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse<Prov> get(@PathVariable("id") Long id) {
        return ResponseHelper.ok(provService.getById(id));
    }

    // Endpoint Untuk Mendapatkan Semua Prov
    @GetMapping
    public CommonResponse<List<Prov>> getAll() {
        return ResponseHelper.ok(provService.getAll());
    }

    // Endpoint Untuk Mengedit Prov Berdasarkan ID
    @PutMapping("/{id}")
    public CommonResponse<Prov> put(@PathVariable("id") Long id, @RequestBody Prov user) {
        return ResponseHelper.ok(provService.edit(user, id));
    }

    // Endpoint Untuk Menghapus Prov Berdasarkan ID
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id") Long id) {
        return ResponseHelper.ok(provService.delete(id));
    }
}
