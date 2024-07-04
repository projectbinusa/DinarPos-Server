package com.template.eazypos.controller;

import com.template.eazypos.dto.PelunasanDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Hutang;
import com.template.eazypos.model.TransaksiBeli;
import com.template.eazypos.service.eazypos.HutangService;
import com.template.eazypos.service.eazypos.excel.ExcelHutangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/hutang")
@CrossOrigin(origins = "*")
public class HutangController {

    @Autowired
    private HutangService hutangService;

    @Autowired
    private ExcelHutangService excelHutangService;

    // Mendapatkan Hutang Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse<Hutang> getById(@PathVariable("id") Long id) {
        return ResponseHelper.ok(hutangService.getById(id));
    }

    // Melakukan Pelunasan Hutang
    @PostMapping("/pelunasan")
    public CommonResponse<Hutang> pelunasan(@RequestBody PelunasanDTO pelunasanDTO) {
        return ResponseHelper.ok(hutangService.pelunasan(pelunasanDTO));
    }

    // Mendapatkan Semua Transaksi Beli
    @GetMapping()
    public CommonResponse<List<TransaksiBeli>> getAll() {
        return ResponseHelper.ok(hutangService.getAll());
    }

    // Mengekspor Data Hutang Ke Dalam File Excel Berdasarkan Tanggal
    @GetMapping("/export/excel/hutang")
    public void exportExcelHutang(
            @RequestParam("tglAwal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAwal,
            @RequestParam("tglAkhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAkhir,
            HttpServletResponse response) throws IOException {
        excelHutangService.excelBukuHutang(tglAwal, tglAkhir, response);
    }

    // Mengekspor Rekap Hutang Ke Dalam File Excel
    @GetMapping("/export/excel/rekap-hutang")
    public void exportExcelRekapHutang(HttpServletResponse response) throws IOException {
        excelHutangService.excelRekapHutang(response);
    }
    @GetMapping("/export/excel/history-hutang")
    public void exportHistoryHutang(@RequestParam("tglAwal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAwal,
                                    @RequestParam("tglAkhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAkhir,
                                    HttpServletResponse response) throws IOException {
        excelHutangService.exportHistoryHutang(response, tglAwal, tglAkhir);
    }
}
