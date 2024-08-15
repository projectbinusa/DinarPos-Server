package com.template.eazypos.controller;

import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.*;
import com.template.eazypos.service.itc.KunjunganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/kunjungan")
@CrossOrigin(origins = "*")
public class KunjunganController {
    @Autowired
    private KunjunganService kunjunganService;

    @GetMapping
    public CommonResponse<List<Kunjungan>> getAll() {
        return ResponseHelper.ok(kunjunganService.getAll());
    }
    @GetMapping("/{id}")
    public CommonResponse <Kunjungan> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( kunjunganService.getById(id));
    }
    @GetMapping("/deal_po/salesman")
    public CommonResponse<List<Kunjungan>> getDealPoAndSalesman(
            @RequestParam("nama_salesman") String  nama) {
        return ResponseHelper.ok(kunjunganService.getDealPoAndSalesman(nama));
    }
    @GetMapping("/deal_finish/salesman")
    public CommonResponse<List<Kunjungan>> getDealFinishAndSalesman(
            @RequestParam("nama_salesman") String  nama) {
        return ResponseHelper.ok(kunjunganService.getDealFinishAndSalesman(nama));
    }
    @GetMapping("/deal_finish")
    public CommonResponse<List<Kunjungan>> getDealFinish() {
        return ResponseHelper.ok(kunjunganService.getDealFinish());
    }
    @GetMapping("/deal_po")
    public CommonResponse<List<Kunjungan>> getDealPO() {
        return ResponseHelper.ok(kunjunganService.getDealPO());
    }
    @GetMapping("/date")
    public CommonResponse<List<Kunjungan>> getByDate(
            @RequestParam(name = "tanggal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggal) {
        return ResponseHelper.ok(kunjunganService.getByDate(tanggal));
    }
    @GetMapping("/date/between")
    public CommonResponse<List<Kunjungan>> getByDateBetween(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir) {
        return ResponseHelper.ok(kunjunganService.getByDateBetween(tanggalAwal, tanggalAkhir));
    }
    @GetMapping("/customer")
    public CommonResponse<List<Kunjungan>> getByCustomer(@RequestParam(name = "id_customer") Long id_customer){
        return ResponseHelper.ok(kunjunganService.getByCustomer(id_customer));
    }
    @GetMapping("/bulan")
    public CommonResponse<List<Kunjungan>> getByBulan(@RequestParam(name = "bulan") int bulan){
        return ResponseHelper.ok(kunjunganService.getByBulan(bulan));
    }
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( kunjunganService.delete(id));
    }

}
