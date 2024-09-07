package com.template.eazypos.controller;

import com.template.eazypos.dto.KecDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Kec;
import com.template.eazypos.service.itc.KecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/kec")
@CrossOrigin(origins = "*")
public class KecController {
    @Autowired
    private KecService kecService;

    @PostMapping("/add")
    public CommonResponse<Kec> addKec(@RequestBody KecDTO user) {
        return ResponseHelper.ok(kecService.add(user));
    }

    // Endpoint Untuk Mendapatkan Detail Kec Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse<Kec> get(@PathVariable("id") Long id) {
        return ResponseHelper.ok(kecService.getById(id));
    }

    // Endpoint Untuk Mendapatkan Semua Kec
    @GetMapping
    public CommonResponse<List<Kec>> getAll() {
        return ResponseHelper.ok(kecService.getAll());
    }

    // Endpoint Untuk Mengedit Kec Berdasarkan ID
    @PutMapping("/{id}")
    public CommonResponse<Kec> put(@PathVariable("id") Long id, @RequestBody KecDTO user) {
        return ResponseHelper.ok(kecService.edit(user, id));
    }

    // Endpoint Untuk Menghapus Kec Berdasarkan ID
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id") Long id) {
        return ResponseHelper.ok(kecService.delete(id));
    }
}
