package com.template.eazypos.controller;

import com.template.eazypos.dto.PembayaranDTO;
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

    // Endpoint untuk menambahkan transaksi indent dari Excelcom
    @PostMapping("/excelcom")
    public CommonResponse<TransaksiIndent> addExcelcom(@RequestBody TransaksiPenjualanDTO transaksiPenjualanDTO) {
        return ResponseHelper.ok(transaksiIndentExcelcomService.addTransaksi(transaksiPenjualanDTO));
    }

    // Endpoint untuk menambahkan transaksi indent dari Dinarpos
    @PostMapping("/dinarpos")
    public CommonResponse<TransaksiIndent> addDinarpos(@RequestBody TransaksiPenjualanDTO transaksiPenjualanDTO) {
        return ResponseHelper.ok(transaksiIndentDinarposService.addTransaksi(transaksiPenjualanDTO));
    }

    // Endpoint untuk checklist transaksi indent dari Excelcom berdasarkan ID
    @PostMapping("/checklist/{id}")
    public CommonResponse<Transaksi> checklist(@PathVariable("id") Long id, @RequestBody PembayaranDTO pembayaranDTO) {
        return ResponseHelper.ok(transaksiIndentExcelcomService.checklist(id, pembayaranDTO));
    }

    // Endpoint untuk mendapatkan semua transaksi indent dari Excelcom
    @GetMapping("/excelcom")
    public CommonResponse<List<TransaksiIndent>> getExcelcom() {
        return ResponseHelper.ok(transaksiIndentExcelcomService.getTransaksiIndentExcelcom());
    }

    // Endpoint untuk mendapatkan transaksi indent dari Excelcom berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse<TransaksiIndent> getById(@PathVariable("id") Long id) {
        return ResponseHelper.ok(transaksiIndentExcelcomService.getById(id));
    }

    // Endpoint untuk mendapatkan semua transaksi indent dari Dinarpos
    @GetMapping("/dinarpos")
    public CommonResponse<List<TransaksiIndent>> getDinarpos() {
        return ResponseHelper.ok(transaksiIndentDinarposService.getTransaksiIndentDinarpos());
    }

    // Endpoint untuk mendapatkan daftar barang transaksi indent dari Excelcom berdasarkan ID
    @GetMapping("/barang")
    public CommonResponse<List<BarangTransaksiIndent>> getBarangById(@RequestParam Long id) {
        return ResponseHelper.ok(transaksiIndentExcelcomService.getBarangTransaksiIndent(id));
    }
}
