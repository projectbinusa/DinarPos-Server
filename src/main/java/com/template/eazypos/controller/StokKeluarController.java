package com.template.eazypos.controller;

import com.template.eazypos.dto.StokKeluarDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.StokKeluar;
import com.template.eazypos.service.eazypos.StokKeluarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/stok_keluar")
@CrossOrigin(origins = "*")
public class StokKeluarController {
    @Autowired
    private StokKeluarService stokKeluarService;


    @PostMapping("/add")
    public CommonResponse<StokKeluar> add(@RequestBody StokKeluarDTO stokKeluarDTO) {
        return ResponseHelper.ok(stokKeluarService.add(stokKeluarDTO));
    }

    @GetMapping("/{id}")
    public CommonResponse<StokKeluar> get(@PathVariable("id") Long id) {
        return ResponseHelper.ok(stokKeluarService.get(id));
    }

    @GetMapping
    public CommonResponse<List<StokKeluar>> getAll() {
        return ResponseHelper.ok(stokKeluarService.getAll());
    }

    @PutMapping("/{id}")
    public CommonResponse<StokKeluar> put(@PathVariable("id") Long id, @RequestBody StokKeluarDTO stokKeluarDTO) {
        return ResponseHelper.ok(stokKeluarService.edit(stokKeluarDTO, id));
    }

    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id") Long id) {
        return ResponseHelper.ok(stokKeluarService.delete(id));
    }
}
