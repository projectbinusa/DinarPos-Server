package com.template.eazypos.controller;

import com.template.eazypos.dto.KonfirmasiDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.service.eazypos.NotifikasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notifikasi")
@CrossOrigin(origins = "*")
public class NotifikasiController {

    @Autowired
    private NotifikasiService notifikasiService;

    // Endpoint Untuk Mendapatkan Laporan Transaksi 7 Hari Untuk Excelcom
    @GetMapping("/7_hari/excelcom")
    public CommonResponse<List<Transaksi>> getAll7HariExcelcom() {
        return ResponseHelper.ok(notifikasiService.getAll7HariExelcom());
    }

    // Endpoint Untuk Mendapatkan Laporan Transaksi 30 Hari Untuk Excelcom
    @GetMapping("/30_hari/excelcom")
    public CommonResponse<List<Transaksi>> getAl30HariExcelcom() {
        return ResponseHelper.ok(notifikasiService.getAll30HariExelcom());
    }

    // Endpoint Untuk Mendapatkan Laporan Transaksi 90 Hari Untuk Excelcom
    @GetMapping("/90_hari/excelcom")
    public CommonResponse<List<Transaksi>> getAll90HariExcelcom() {
        return ResponseHelper.ok(notifikasiService.getAll90HariExelcom());
    }

    // Endpoint Untuk Mendapatkan Laporan Transaksi 120 Hari Untuk Excelcom
    @GetMapping("/120_hari/excelcom")
    public CommonResponse<List<Transaksi>> getAll120HariExcelcom() {
        return ResponseHelper.ok(notifikasiService.getAll120HariExelcom());
    }

    // Endpoint Untuk Mendapatkan Laporan Transaksi 365 Hari Untuk Excelcom
    @GetMapping("/365_hari/excelcom")
    public CommonResponse<List<Transaksi>> getAll365HariExcelcom() {
        return ResponseHelper.ok(notifikasiService.getAll365HariExelcom());
    }

    // Endpoint Untuk Mendapatkan Laporan Transaksi 7 Hari Untuk Dinarpos
    @GetMapping("/7_hari/dinarpos")
    public CommonResponse<List<Transaksi>> getAll7HariDinarpos() {
        return ResponseHelper.ok(notifikasiService.getAll7HariDinarpos());
    }

    // Endpoint Untuk Mendapatkan Laporan Transaksi 30 Hari Untuk Dinarpos
    @GetMapping("/30_hari/dinarpos")
    public CommonResponse<List<Transaksi>> getAl30HariDinarpos() {
        return ResponseHelper.ok(notifikasiService.getAll30HariDinarpos());
    }

    // Endpoint Untuk Mendapatkan Laporan Transaksi 90 Hari Untuk Dinarpos
    @GetMapping("/90_hari/dinarpos")
    public CommonResponse<List<Transaksi>> getAll90HariDinarpos() {
        return ResponseHelper.ok(notifikasiService.getAll90HariDinarpos());
    }

    // Endpoint Untuk Mendapatkan Laporan Transaksi 120 Hari Untuk Dinarpos
    @GetMapping("/120_hari/dinarpos")
    public CommonResponse<List<Transaksi>> getAll120HariDinarpos() {
        return ResponseHelper.ok(notifikasiService.getAll120HariDinarpos());
    }

    // Endpoint Untuk Mendapatkan Laporan Transaksi 365 Hari Untuk Dinarpos
    @GetMapping("/365_hari/dinarpos")
    public CommonResponse<List<Transaksi>> getAll365HariDinarpos() {
        return ResponseHelper.ok(notifikasiService.getAll365HariDinarpos());
    }

    // Endpoint Untuk Mendapatkan notifikasi Konfirmasi Transaksi 7 Hari Untuk Dinarpos
    @GetMapping("/konfirmasi/7_hari/dinarpos")
    public CommonResponse<List<Transaksi>> getAllKonfirmasi7HariDinarpos() {
        return ResponseHelper.ok(notifikasiService.getNotifikasi7HariDinarpos());
    }

    // Endpoint Untuk Mendapatkan Notifikasi Konfirmasi Transaksi 30 Hari Untuk Dinarpos
    @GetMapping("/konfirmasi/30_hari/dinarpos")
    public CommonResponse<List<Transaksi>> getAllKonfirmasi30HariDinarpos() {
        return ResponseHelper.ok(notifikasiService.getNotifikasi30HariDinarpos());
    }

    // Endpoint Untuk Mendapatkan Notifikasi Konfirmasi Transaksi 90 Hari Untuk Dinarpos
    @GetMapping("/konfirmasi/90_hari/dinarpos")
    public CommonResponse<List<Transaksi>> getAllKonfirmasi90HariDinarpos() {
        return ResponseHelper.ok(notifikasiService.getNotifikasi90HariDinarpos());
    }

    // Endpoint Untuk Mendapatkan Notifikasi Konfirmasi Transaksi 120 Hari Untuk Dinarpos
    @GetMapping("/konfirmasi/120_hari/dinarpos")
    public CommonResponse<List<Transaksi>> getAllKonfirmasi120HariDinarpos() {
        return ResponseHelper.ok(notifikasiService.getNotifikasi120HariDinarpos());
    }

    // Endpoint Untuk Mendapatkan Notifikasi Konfirmasi Transaksi 365 Hari Untuk Dinarpos
    @GetMapping("/konfirmasi/365_hari/dinarpos")
    public CommonResponse<List<Transaksi>> getAllKonfirmasi365HariDinarpos() {
        return ResponseHelper.ok(notifikasiService.getNotifikasi365HariDinarpos());
    }

    // Endpoint Untuk Konfirmasi Transaksi 7 Hari Untuk Excelcom
    @PutMapping("/konfirmasi/7_hari/{id}")
    public CommonResponse<Transaksi> konfirmasi7HariExcelcom(
            @RequestBody KonfirmasiDTO konfirmasiDTO,
            @PathVariable("id") Long id) {
        return ResponseHelper.ok(notifikasiService.konfirmasi7Hari(konfirmasiDTO, id));
    }

    // Endpoint Untuk Konfirmasi Transaksi 30 Hari Untuk Excelcom
    @PutMapping("/konfirmasi/30_hari/{id}")
    public CommonResponse<Transaksi> konfirmasi30HariExcelcom(
            @RequestBody KonfirmasiDTO konfirmasiDTO,
            @PathVariable("id") Long id) {
        return ResponseHelper.ok(notifikasiService.konfirmasi30Hari(konfirmasiDTO, id));
    }

    // Endpoint Untuk Konfirmasi Transaksi 90 Hari Untuk Excelcom
    @PutMapping("/konfirmasi/90_hari/{id}")
    public CommonResponse<Transaksi> konfirmasi90HariExcelcom(
            @RequestBody KonfirmasiDTO konfirmasiDTO,
            @PathVariable("id") Long id) {
        return ResponseHelper.ok(notifikasiService.konfirmasi90Hari(konfirmasiDTO, id));
    }

    // Endpoint Untuk Konfirmasi Transaksi 120 Hari Untuk Excelcom
    @PutMapping("/konfirmasi/120_hari/{id}")
    public CommonResponse<Transaksi> konfirmasi120HariExcelcom(
            @RequestBody KonfirmasiDTO konfirmasiDTO,
            @PathVariable("id") Long id) {
        return ResponseHelper.ok(notifikasiService.konfirmasi120Hari(konfirmasiDTO, id));
    }

    // Endpoint Untuk Konfirmasi Transaksi 365 Hari Untuk Excelcom
    @PutMapping("/konfirmasi/365_hari/{id}")
    public CommonResponse<Transaksi> konfirmasi365HariExcelcom(
            @RequestBody KonfirmasiDTO konfirmasiDTO,
            @PathVariable("id") Long id) {
        return ResponseHelper.ok(notifikasiService.konfirmasi365Hari(konfirmasiDTO, id));
    }
}
