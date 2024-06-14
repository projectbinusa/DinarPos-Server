package com.template.eazypos.controller;

import com.template.eazypos.dto.PelunasanDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Piutang;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.service.eazypos.PiutangService;
import com.template.eazypos.service.eazypos.excel.ExcelPiutangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/piutang")
@CrossOrigin(origins = "*")
public class PiutangController {

    @Autowired
    private PiutangService piutangService;

    @Autowired
    private ExcelPiutangService excelPiutangService;

    // Endpoint Untuk Mendapatkan Data Piutang Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse<Piutang> getById(@PathVariable("id") Long id) {
        return ResponseHelper.ok(piutangService.getById(id));
    }

    // Endpoint Untuk Melakukan Pelunasan Piutang
    @PostMapping()
    public CommonResponse<Piutang> pelunasan(@RequestBody PelunasanDTO pelunasanDTO) {
        return ResponseHelper.ok(piutangService.pelunasan(pelunasanDTO));
    }

    // Endpoint Untuk Mendapatkan Semua Data Piutang
    @GetMapping()
    public CommonResponse<List<Transaksi>> getAll() {
        return ResponseHelper.ok(piutangService.getAll());
    }

    // Endpoint Untuk Export Data Piutang Ke Excel Dengan Rentang Tanggal Tertentu
    @GetMapping("/export/excel/piutang")
    public void exportExcelPiutang(
            @RequestParam("tglAwal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAwal,
            @RequestParam("tglAkhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAkhir,
            HttpServletResponse response) throws IOException {

        excelPiutangService.excelBukuPiutang(tglAwal, tglAkhir, response);
    }

    // Endpoint Untuk Export Rekap Data Piutang Ke Excel
    @GetMapping("/export/excel/rekap-piutang")
    public void exportExcelRekapPiutang(HttpServletResponse response) throws IOException {
        excelPiutangService.excelRekapPiutang(response);
    }
}
