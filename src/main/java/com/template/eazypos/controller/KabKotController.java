package com.template.eazypos.controller;

import com.template.eazypos.dto.KabKotDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.KabKot;
import com.template.eazypos.service.itc.KabKotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/kab_kot")
@CrossOrigin(origins = "*")
public class KabKotController {
    @Autowired
    private KabKotService kabKotService;

    @PostMapping("/add")
    public CommonResponse<KabKot> addKabKot(@RequestBody KabKotDTO user) {
        return ResponseHelper.ok(kabKotService.add(user));
    }

    // Endpoint Untuk Mendapatkan Detail KabKot Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse<KabKot> get(@PathVariable("id") Long id) {
        return ResponseHelper.ok(kabKotService.getById(id));
    }

    // Endpoint Untuk Mendapatkan Semua KabKot
    @GetMapping
    public CommonResponse<List<KabKot>> getAll() {
        return ResponseHelper.ok(kabKotService.getAll());
    }

    // Endpoint Untuk Mengedit KabKot Berdasarkan ID
    @PutMapping("/{id}")
    public CommonResponse<KabKot> put(@PathVariable("id") Long id, @RequestBody KabKotDTO user) {
        return ResponseHelper.ok(kabKotService.edit(user, id));
    }

    // Endpoint Untuk Menghapus KabKot Berdasarkan ID
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id") Long id) {
        return ResponseHelper.ok(kabKotService.delete(id));
    }
}
