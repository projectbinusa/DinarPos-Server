package com.template.eazypos.controller;

import com.template.eazypos.dto.TransaksiPenjualanDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.BarangTransaksiIndent;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.model.TransaksiBeli;
import com.template.eazypos.model.TransaksiIndent;
import com.template.eazypos.service.eazypos.dinarpos.TransaksiIndentDinarposService;
import com.template.eazypos.service.eazypos.excelcom.TransaksiIndentExcelcomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/transaksi_indent")
@CrossOrigin(origins = "*")
public class TransaksiIndentController {
    @Autowired
    private TransaksiIndentExcelcomService transaksiIndentExcelcomService;
    @Autowired
    private TransaksiIndentDinarposService transaksiIndentDinarposService;

    @PostMapping("/excelcom")
    public CommonResponse<TransaksiIndent> addExcelcom(@RequestBody TransaksiPenjualanDTO transaksiPenjualanDTO){
        return ResponseHelper.ok( transaksiIndentExcelcomService.addTransaksi(transaksiPenjualanDTO));
    }
    @PostMapping("/dinarpos")
    public CommonResponse<TransaksiIndent> addDinarpos(@RequestBody TransaksiPenjualanDTO transaksiPenjualanDTO){
        return ResponseHelper.ok( transaksiIndentDinarposService.addTransaksi(transaksiPenjualanDTO));
    }
    @PostMapping("/checklist/{id}")
    public CommonResponse<Transaksi> checklist(@PathVariable("id") Long id){
        return ResponseHelper.ok( transaksiIndentExcelcomService.checklist(id));
    }
    @GetMapping("/excelcom")
    public CommonResponse <List<TransaksiIndent>> getExcelcom(){
        return ResponseHelper.ok( transaksiIndentExcelcomService.getTransaksiIndentExcelcom());
    }
    @GetMapping("/{id}")
    public CommonResponse <TransaksiIndent> getById(Long id){
        return ResponseHelper.ok( transaksiIndentExcelcomService.getById(id));
    }
    @GetMapping("/dinarpos")
    public CommonResponse <List<TransaksiIndent>> getDinarpos(){
        return ResponseHelper.ok( transaksiIndentDinarposService.getTransaksiIndentDinarpos());
    }
    @GetMapping("/barang")
    public CommonResponse <List<BarangTransaksiIndent>> getBarangById(@RequestParam Long id){
        return ResponseHelper.ok( transaksiIndentExcelcomService.getBarangTransaksiIndent(id));
    }
}
