package com.template.eazypos.controller;

import com.template.eazypos.service.eazypos.excel.ExcelLaporanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/laporan_service")
public class LaporanServiceController {
    @Autowired
    private ExcelLaporanService excelLaporanService;

    @GetMapping("/export/laporanServiceAll")
    public void exportToExcel(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir,
            HttpServletResponse response) throws IOException {
        excelLaporanService.excelLaporanService(tanggalAwal, tanggalAkhir, response);
    }
}
