package com.template.eazypos.controller;

import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Status;
import com.template.eazypos.service.eazypos.excel.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private ExcelLaporanStatusService excelLaporanStatusService;

    @Autowired
    private ExcelLaporanStatusAllService excelLaporanStatusAllService;

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

    @GetMapping("/export/laporanStatus")
    public void exportLaporanStatus(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir,
            HttpServletResponse response) throws IOException {
        excelLaporanStatusService.excelLaporanStatus(tanggalAwal, tanggalAkhir, response);
    }

    @GetMapping("/export/statusAll")
    public ResponseEntity<Resource> exportStatusAll() throws IOException {
        String filename = "Status.xlsx";
        InputStreamResource file = new InputStreamResource(excelLaporanStatusAllService.loadStatus());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spareadsheetml.sheet"))
                .body(file);
    }

    @GetMapping
    public CommonResponse<List<Status>> getAll() {
        return ResponseHelper.ok(excelLaporanStatusAllService.getAllStatus());
    }
}
