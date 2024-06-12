package com.template.eazypos.controller;

import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.PoinHistory;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.service.itc.admin.PoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/poin")
@CrossOrigin(origins = "*")
public class PoinController {
    @Autowired
    private PoinService poinService;

    @GetMapping("/month/{month}")
    public List<PoinHistory> getPoinByMonth(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate month) {
        return poinService.getPoinByMonth(month);
    }
    @GetMapping("/tanggal/")
    public CommonResponse<List<PoinHistory>> getAllByTanggal(@RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal, @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir ) {
        return ResponseHelper.ok(poinService.getByTanggal(tanggalAwal,tanggalAkhir));
    }
    @GetMapping("/{id_teknisi}/{month}")
    public CommonResponse<?> getPoinForMonth(@PathVariable("id_teknisi") Long idTeknisi, @PathVariable("month") int month) {
        return ResponseHelper.ok(poinService.getPoinForMonth(idTeknisi, month));
    }
    @GetMapping("/filter")
    public List<PoinHistory> filterPoinHistory(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date awal,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date akhir,
            @RequestParam Long idTeknisi) {
        return poinService.getPoinHistory(awal, akhir, idTeknisi);
    }
    @GetMapping("/teknisi")
    public CommonResponse<List<PoinHistory>> getPoinByIdTeknisi(@RequestParam(name = "id_teknisi") String idTeknisi) {
        return ResponseHelper.ok(poinService.getPoinHistoryByIdTeknisi(idTeknisi));
    }

    @GetMapping("/ket/{keterangan}")
    public CommonResponse<List<PoinHistory>> getAllPoinByKeterangan(@PathVariable("keterangan") String keterangan) {
        return ResponseHelper.ok(poinService.getAllByKeterangan(keterangan));
    }
}
