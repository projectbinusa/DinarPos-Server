package com.template.eazypos.controller;

import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.SaldoAwalShift;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.service.eazypos.KasHarianService;
import com.template.eazypos.service.eazypos.excel.ExcelKasHarian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/kas_harian")
@CrossOrigin(origins = "*")
public class KasHarianController {
    @Autowired
    private KasHarianService kasHarianService;

    @Autowired
    private ExcelKasHarian excelKasHarian;

    @PostMapping("/saldo_shift_awal/add")
    public CommonResponse<SaldoAwalShift> add(@RequestBody SaldoAwalShift shift) {
        return ResponseHelper.ok(kasHarianService.add(shift));
    }

    // Endpoint Untuk Mendapatkan Detail SaldoAwalShift Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse<SaldoAwalShift> get(@PathVariable("id") Long id) {
        return ResponseHelper.ok(kasHarianService.getById(id));
    }

    // Endpoint Untuk Mendapatkan Semua SaldoAwalShift
    @GetMapping
    public CommonResponse<List<SaldoAwalShift>> getAll() {
        return ResponseHelper.ok(kasHarianService.getAll());
    }

    @DeleteMapping("delete/{id}")
    public CommonResponse<?> delete(@PathVariable("id") Long id) {
        return ResponseHelper.ok(kasHarianService.delete(id));
    }
    @PutMapping("/update/{id}")
    public CommonResponse<SaldoAwalShift> put(@PathVariable("id") Long id, @RequestBody SaldoAwalShift shift) {
        return ResponseHelper.ok(kasHarianService.edit(shift, id));
    }
    @GetMapping("/export/excel")
    public void exportToExcel(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir,
            HttpServletResponse response) throws IOException {
        excelKasHarian.excelKasHarian(tanggalAwal, tanggalAkhir, response);
    }

}
