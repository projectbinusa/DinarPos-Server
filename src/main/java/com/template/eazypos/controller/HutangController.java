package com.template.eazypos.controller;

import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Hutang;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.service.eazypos.HutangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/hutang")
@CrossOrigin(origins = "*")
public class HutangController {
    @Autowired
    private HutangService hutangService;

    @GetMapping("/{id}")
    public CommonResponse<Hutang> getById(@PathVariable("id") Long id) {
        return ResponseHelper.ok(hutangService.getById(id));
    }
    @PutMapping("/{id}")
    public CommonResponse<Hutang> pelunasan(@RequestBody Hutang hutang , @PathVariable("id") Long id) {
        return ResponseHelper.ok(hutangService.pelunasan(hutang ,id));
    }
    @GetMapping()
    public CommonResponse<List<Hutang>> getAll() {
        return ResponseHelper.ok(hutangService.getAll());
    }
}
