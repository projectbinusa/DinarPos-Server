package com.template.eazypos.controller;

import com.template.eazypos.dto.StokMasukDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.StokMasuk;
import com.template.eazypos.service.eazypos.StokMasukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/stok_masuk")
@CrossOrigin(origins = "*")
public class StokMasukController {

    @Autowired
    private StokMasukService stokMasukService;

    // Endpoint untuk menambahkan data stok masuk
    @PostMapping("/add")
    public CommonResponse<StokMasuk> add(@RequestBody StokMasukDTO stokMasukDTO) {
        return ResponseHelper.ok(stokMasukService.add(stokMasukDTO));
    }

    // Endpoint untuk mendapatkan data stok masuk berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse<StokMasuk> get(@PathVariable("id") Long id) {
        return ResponseHelper.ok(stokMasukService.get(id));
    }

    // Endpoint untuk mendapatkan semua data stok masuk
    @GetMapping
    public CommonResponse<List<StokMasuk>> getAll() {
        return ResponseHelper.ok(stokMasukService.getAll());
    }

    // Endpoint untuk mengedit data stok masuk berdasarkan ID
    @PutMapping("/{id}")
    public CommonResponse<StokMasuk> put(@PathVariable("id") Long id, @RequestBody StokMasukDTO stokMasukDTO) {
        return ResponseHelper.ok(stokMasukService.edit(stokMasukDTO, id));
    }

    // Endpoint untuk menghapus data stok masuk berdasarkan ID
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id") Long id) {
        return ResponseHelper.ok(stokMasukService.delete(id));
    }
}
