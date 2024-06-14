package com.template.eazypos.controller;

import com.template.eazypos.dto.GaransiDTO;
import com.template.eazypos.dto.TglJadiGaransiDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Garansi;
import com.template.eazypos.service.itc.GaransiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/garansi")
@CrossOrigin(origins = "*")
public class GaransiController {

    @Autowired
    private GaransiService garansiService;

    // Menambahkan Garansi Baru
    @PostMapping("/add")
    public CommonResponse<Garansi> add(@RequestBody GaransiDTO garansiDTO) {
        return ResponseHelper.ok(garansiService.add(garansiDTO));
    }

    // Mendapatkan Garansi Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse<Garansi> get(@PathVariable("id") Long id) {
        return ResponseHelper.ok(garansiService.getById(id));
    }

    // Mendapatkan Semua Garansi
    @GetMapping
    public CommonResponse<List<Garansi>> getAll() {
        return ResponseHelper.ok(garansiService.getAll());
    }

    // Mengedit Garansi Berdasarkan ID
    @PutMapping("/update/{id}")
    public CommonResponse<Garansi> put(@PathVariable("id") Long id, @RequestBody GaransiDTO garansiDTO) {
        return ResponseHelper.ok(garansiService.edit(garansiDTO, id));
    }

    // Mengupdate Tanggal Jadi Garansi Berdasarkan ID
    @PutMapping("/update/tgl_jadi/{id}")
    public CommonResponse<Garansi> updateTglJadi(@PathVariable("id") Long id, @RequestBody TglJadiGaransiDTO garansiDTO) {
        return ResponseHelper.ok(garansiService.updateTglJadi(garansiDTO, id));
    }

    // Menghapus Garansi Berdasarkan ID
    @DeleteMapping("delete/{id}")
    public CommonResponse<?> delete(@PathVariable("id") Long id) {
        return ResponseHelper.ok(garansiService.delete(id));
    }
}
