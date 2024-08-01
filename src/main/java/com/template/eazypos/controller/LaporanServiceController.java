package com.template.eazypos.controller;

import com.template.eazypos.service.eazypos.excel.*;
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

    @Autowired
    private ExcelLaporanServiceProses excelLaporanServiceProses;

    @Autowired
    private ExcelLaporanServiceCancel excelLaporanServiceCancel;

    @Autowired
    private ExcelLaporanServiceReady excelLaporanServiceReady;

    @Autowired
    private ExcelLaporanServiceTaken excelLaporanServiceTaken;

    @GetMapping("/export/laporanServiceAll")
    public void exportLaporanServiceAll(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir,
            HttpServletResponse response) throws IOException {
        excelLaporanService.excelLaporanService(tanggalAwal, tanggalAkhir, response);
    }

    @GetMapping("/export/laporanServiceProses")
    public void exportLaporanServiceProses(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir,
            HttpServletResponse response) throws IOException {
        excelLaporanServiceProses.excelLaporanServiceInProses(tanggalAwal, tanggalAkhir, response);
    }

    @GetMapping("/export/laporanServiceCancel")
    public void exportLaporanServiceCancel(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir,
            HttpServletResponse response) throws IOException {
        excelLaporanServiceCancel.excelLaporanServiceWithCancel(tanggalAwal, tanggalAkhir, response);
    }

    @GetMapping("/export/laporanServiceReady")
    public void exportLaporanServiceReady(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir,
            HttpServletResponse response) throws IOException {
        excelLaporanServiceReady.excelLaporanServiceWithReady(tanggalAwal, tanggalAkhir, response);
    }

    @GetMapping("/export/laporanServiceTaken")
    public void exportLaporanServiceTaken(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir,
            HttpServletResponse response) throws IOException {
        excelLaporanServiceTaken.excelLaporanServiceWithTaken(tanggalAwal, tanggalAkhir, response);
    }
}
