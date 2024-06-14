package com.template.eazypos.controller;

import com.template.eazypos.dto.LoginRequest;
import com.template.eazypos.dto.PenggunaDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Pengguna;
import com.template.eazypos.service.auth.PenggunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pengguna")
@CrossOrigin(origins = "*")
public class PenggunaController {

    @Autowired
    private PenggunaService penggunaService;

    // Endpoint Untuk Autentikasi Pengguna
    @PostMapping("/login")
    public CommonResponse<?> authenticatePengguna(@RequestBody LoginRequest loginRequest) {
        return ResponseHelper.ok(penggunaService.login(loginRequest));
    }

    // Endpoint Untuk menambahkan Pengguna Baru
    @PostMapping("/add")
    public CommonResponse<Pengguna> addPengguna(@RequestBody PenggunaDTO user) {
        return ResponseHelper.ok(penggunaService.addPengguna(user));
    }

    // Endpoint Untuk Mendapatkan Detail Pengguna Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse<Pengguna> get(@PathVariable("id") Long id) {
        return ResponseHelper.ok(penggunaService.get(id));
    }

    // Endpoint Untuk Mencari Pengguna Berdasarkan Nama
    @GetMapping("/nama")
    public CommonResponse<Pengguna> getByNama(@RequestParam("nama") String nama) {
        return ResponseHelper.ok(penggunaService.getByNama(nama));
    }

    // Endpoint Untuk Mendapatkan Semua Pengguna
    @GetMapping
    public CommonResponse<List<Pengguna>> getAll() {
        return ResponseHelper.ok(penggunaService.getAll());
    }

    // Endpoint Untuk Mengedit Pengguna Berdasarkan ID
    @PutMapping("/{id}")
    public CommonResponse<Pengguna> put(@PathVariable("id") Long id, @RequestBody Pengguna user) {
        return ResponseHelper.ok(penggunaService.edit(id, user));
    }

    // Endpoint Untuk Menghapus Pengguna Berdasarkan ID
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id") Long id) {
        return ResponseHelper.ok(penggunaService.delete(id));
    }
}
