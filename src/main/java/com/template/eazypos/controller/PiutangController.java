package com.template.eazypos.controller;

import com.template.eazypos.dto.PelunasanDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Hutang;
import com.template.eazypos.model.Piutang;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.service.eazypos.HutangService;
import com.template.eazypos.service.eazypos.PiutangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/piutang")
@CrossOrigin(origins = "*")
public class PiutangController {
    @Autowired
    private PiutangService piutangService;

    @GetMapping("/{id}")
    public CommonResponse<Piutang> getById(@PathVariable("id") Long id) {
        return ResponseHelper.ok(piutangService.getById(id));
    }
    @PostMapping()
    public CommonResponse<Piutang> pelunasan(@RequestBody PelunasanDTO piutang) {
        return ResponseHelper.ok(piutangService.pelunasan(piutang));
    }
    @GetMapping()
    public CommonResponse<List<Transaksi>> getAll() {
        return ResponseHelper.ok(piutangService.getAll());
    }
}
