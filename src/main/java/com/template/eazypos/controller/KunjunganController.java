package com.template.eazypos.controller;

import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Kunjungan;
import com.template.eazypos.model.SaldoAwalShift;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.service.itc.KunjunganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/kunjungan")
@CrossOrigin(origins = "*")
public class KunjunganController {
    @Autowired
    private KunjunganService kunjunganService;

    @GetMapping
    public CommonResponse<List<Kunjungan>> getAll() {
        return ResponseHelper.ok(kunjunganService.getAll());
    }
    @GetMapping("/{id}")
    public CommonResponse <Kunjungan> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( kunjunganService.getById(id));
    }
}
