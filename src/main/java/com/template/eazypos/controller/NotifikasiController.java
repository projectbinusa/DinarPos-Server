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

    @GetMapping("/7_hari/excelcom")
    public CommonResponse<List<Transaksi>> getAll7HariExcelcom() {
        return ResponseHelper.ok(notifikasiService.getAll7HariExelcom());
    }

    @GetMapping("/30_hari/excelcom")
    public CommonResponse<List<Transaksi>> getAl30HariExcelcom() {
        return ResponseHelper.ok(notifikasiService.getAll30HariExelcom());
    }

    @GetMapping("/90_hari/excelcom")
    public CommonResponse<List<Transaksi>> getAll90HariExcelcom() {
        return ResponseHelper.ok(notifikasiService.getAll90HariExelcom());
    }

    @GetMapping("/120_hari/excelcom")
    public CommonResponse<List<Transaksi>> getAll120HariExcelcom() {
        return ResponseHelper.ok(notifikasiService.getAll120HariExelcom());
    }

    @GetMapping("/365_hari/excelcom")
    public CommonResponse<List<Transaksi>> getAll365HariExcelcom() {
        return ResponseHelper.ok(notifikasiService.getAll365HariExelcom());
    }

    @GetMapping("/7_hari/dinarpos")
    public CommonResponse<List<Transaksi>> getAll7HariDinarpos() {
        return ResponseHelper.ok(notifikasiService.getAll7HariDinarpos());
    }

    @GetMapping("/30_hari/dinarpos")
    public CommonResponse<List<Transaksi>> getAl30HariDinarpos() {
        return ResponseHelper.ok(notifikasiService.getAll30HariDinarpos());
    }

    @GetMapping("/90_hari/dinarpos")
    public CommonResponse<List<Transaksi>> getAll90HariDinarpos() {
        return ResponseHelper.ok(notifikasiService.getAll90HariDinarpos());
    }

    @GetMapping("/120_hari/dinarpos")
    public CommonResponse<List<Transaksi>> getAll120HariDinarpos() {
        return ResponseHelper.ok(notifikasiService.getAll120HariDinarpos());
    }

    @GetMapping("/365_hari/dinarpos")
    public CommonResponse<List<Transaksi>> getAll365HariDinarpos() {
        return ResponseHelper.ok(notifikasiService.getAll365HariDinarpos());
    }

    @PutMapping("/konfirmasi/7_hari/{id}")
    public CommonResponse<Transaksi> konfirmasi7HariExcelcom(@RequestBody KonfirmasiDTO konfirmasiDTO, @PathVariable("id") Long id) {
        return ResponseHelper.ok(notifikasiService.konfirmasi7Hari(konfirmasiDTO, id));
    }

    @PutMapping("/konfirmasi/30_hari/{id}")
    public CommonResponse<Transaksi> konfirmasi30HariExcelcom(@RequestBody KonfirmasiDTO konfirmasiDTO, @PathVariable("id") Long id) {
        return ResponseHelper.ok(notifikasiService.konfirmasi30Hari(konfirmasiDTO, id));
    }

    @PutMapping("/konfirmasi/90_hari/{id}")
    public CommonResponse<Transaksi> konfirmasi90HariExcelcom(@RequestBody KonfirmasiDTO konfirmasiDTO, @PathVariable("id") Long id) {
        return ResponseHelper.ok(notifikasiService.konfirmasi90Hari(konfirmasiDTO, id));
    }

    @PutMapping("/konfirmasi/120_hari/{id}")
    public CommonResponse<Transaksi> konfirmasi120HariExcelcom(@RequestBody KonfirmasiDTO konfirmasiDTO, @PathVariable("id") Long id) {
        return ResponseHelper.ok(notifikasiService.konfirmasi120Hari(konfirmasiDTO, id));
    }

    @PutMapping("/konfirmasi/365_hari/{id}")
    public CommonResponse<Transaksi> konfirmasi365HariExcelcom(@RequestBody KonfirmasiDTO konfirmasiDTO, @PathVariable("id") Long id) {
        return ResponseHelper.ok(notifikasiService.konfirmasi365Hari(konfirmasiDTO, id));
    }
}
