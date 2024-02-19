package com.template.eazypos.controller;

import com.template.eazypos.dto.ExcelcomRequest;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.service.eazypos.excelcom.TransaksiPenjualan;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/transakasi")
@CrossOrigin(origins = "http://localhost:3000")
public class TransaksiController {
    @Autowired
    private TransaksiPenjualan transaksiPenjualanExcelcom;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/excelcom")
    public Transaksi addExcelcom(@RequestBody ExcelcomRequest request) {
        return transaksiPenjualanExcelcom.addExcelcom(modelMapper.map(request , Transaksi.class));
    }
}
