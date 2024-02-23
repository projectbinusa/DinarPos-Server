package com.template.eazypos.controller;

import com.template.eazypos.dto.TransaksiBeliDTO;
import com.template.eazypos.dto.TransaksiPenjualanDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.model.TransaksiBeli;
import com.template.eazypos.service.eazypos.dinarpos.TransaksiBeliDinarposService;
import com.template.eazypos.service.eazypos.dinarpos.TransaksiPenjualanDinarposService;
import com.template.eazypos.service.eazypos.excelcom.TransaksiBeliExcelcomService;
import com.template.eazypos.service.eazypos.excelcom.TransaksiPenjualanExcelcomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/transaksi")
@CrossOrigin(origins = "http://localhost:3000")
public class TransaksiController {
    @Autowired
    private TransaksiPenjualanExcelcomService transaksiPenjualanService;
    @Autowired
    private TransaksiPenjualanDinarposService transaksiPenjualanDinarposService;

    @Autowired
    private TransaksiBeliExcelcomService transaksiBeliExcelcomService;

    @Autowired
    private TransaksiBeliDinarposService transaksiBeliDinarposService;

    @PostMapping("/penjualan/excelcom")
    public CommonResponse<Transaksi> addExcelcom(@RequestBody TransaksiPenjualanDTO transaksiPenjualanDTO){
        return ResponseHelper.ok( transaksiPenjualanService.addTransaksi(transaksiPenjualanDTO));
    }
    @PostMapping("/penjualan/dinarpos")
    public CommonResponse<Transaksi> addDinarpos(@RequestBody TransaksiPenjualanDTO transaksiPenjualanDTO){
        return ResponseHelper.ok( transaksiPenjualanDinarposService.addTransaksi(transaksiPenjualanDTO));
    }
    @PostMapping("/pembelian/excelcom")
    public CommonResponse<TransaksiBeli> addExcelcom(@RequestBody TransaksiBeliDTO transaksiBeliDTO){
        return ResponseHelper.ok( transaksiBeliExcelcomService.addTransaksiBeli(transaksiBeliDTO));
    }
    @PostMapping("/pembelian/dinarpos")
    public CommonResponse<TransaksiBeli> addDinarpos(@RequestBody TransaksiBeliDTO transaksiBeliDTO){
        return ResponseHelper.ok( transaksiBeliDinarposService.addTransaksiBeli(transaksiBeliDTO));
    }
}
