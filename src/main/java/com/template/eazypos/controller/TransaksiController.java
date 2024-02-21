package com.template.eazypos.controller;

import com.template.eazypos.dto.TransaksiPenjualanDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.service.eazypos.excelcom.TransaksiPenjualanExcelcomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/transaksi")
@CrossOrigin(origins = "http://localhost:3000")
public class TransaksiController {
    @Autowired
    private TransaksiPenjualanExcelcomService transaksiPenjualanService;

    @PostMapping("/excelcom")
    public CommonResponse<Transaksi> add(@RequestBody TransaksiPenjualanDTO transaksiPenjualanDTO){
        return ResponseHelper.ok( transaksiPenjualanService.addTransaksi(transaksiPenjualanDTO));
    }
}
