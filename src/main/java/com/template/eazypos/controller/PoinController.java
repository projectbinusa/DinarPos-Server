package com.template.eazypos.controller;

import com.template.eazypos.dto.PoinHistoryDTO;
import com.template.eazypos.dto.PoinHistoryMonthDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Poin;
import com.template.eazypos.model.PoinHistory;
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

    // Mendapatkan Riwayat Poin Berdasarkan Bulan
    @GetMapping("/month")
    public CommonResponse<List<PoinHistoryMonthDTO>> getPoinByMonth(@RequestParam(name = "month") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate month) {
        return ResponseHelper.ok(poinService.getPoinByMonthAdmin(month));
    }

    // Mendapatkan Semua Riwayat Poin Berdasarkan Rentang Tanggal
    @GetMapping("/tanggal/")
    public CommonResponse<List<PoinHistory>> getAllByTanggal(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir) {
        return ResponseHelper.ok(poinService.getByTanggal(tanggalAwal, tanggalAkhir));
    }

    // Mendapatkan Poin Untuk Seorang Teknisi Pada Bulan Tertentu
    @GetMapping("/{id_teknisi}/{month}")
    public CommonResponse<?> getPoinForMonth(@PathVariable("id_teknisi") Long idTeknisi, @PathVariable("month") int month) {
        return ResponseHelper.ok(poinService.getPoinForMonth(idTeknisi, month));
    }

    // Mencari Riwayat Poin Berdasarkan Filter Tanggal Dan ID Teknisi
    @GetMapping("/filter")
    public CommonResponse<List<PoinHistory>> filterPoinHistory(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date awal,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date akhir,
            @RequestParam Long idTeknisi) {
        return ResponseHelper.ok(poinService.getPoinHistory(awal, akhir, idTeknisi));
    }

    // Mendapatkan Riwayat Poin Berdasarkan ID Teknisi
    @GetMapping("/teknisi")
    public CommonResponse<List<PoinHistory>> getPoinByIdTeknisi(@RequestParam(name = "id_teknisi") String idTeknisi) {
        return ResponseHelper.ok(poinService.getPoinHistoryByIdTeknisi(idTeknisi));
    }

    // Mendapatkan Semua Riwayat Poin Berdasarkan Keterangan
    @GetMapping("/keterangan")
    public CommonResponse<List<PoinHistory>> getAllPoinByKeterangan(@RequestParam("keterangan") String keterangan) {
        return ResponseHelper.ok(poinService.getAllByKeterangan(keterangan));
    }

    // Mendapatkan Semua Data Poin Untuk Pimpinan
    @GetMapping("/pimpinan")
    public CommonResponse<List<Poin>> getPoin() {
        return ResponseHelper.ok(poinService.getPoin());
    }

    // Mendapatkan Total Poin Untuk Pimpinan
    @GetMapping("/pimpinan/total")
    public CommonResponse<?> getTotalPoin() {
        return ResponseHelper.ok(poinService.getTotalPoin());
    }

    // Mendapatkan Total Poin Berdasarkan Bulan Untuk Pimpinan
    @GetMapping("/pimpinan/total-by-month")
    public CommonResponse<?> getTotalPoinByMonth(@RequestParam("month") int month) {
        return ResponseHelper.ok( poinService.getTotalPoinByMonth(month));
    }

    // Mendapatkan Riwayat Poin Berdasarkan Bulan Untuk Pimpinan
    @GetMapping("/pimpinan/by-month")
    public List<PoinHistory> getPoinByMonthPimpinan(@RequestParam("month") String month) {
        return poinService.getPoinByMonth(month);
    }

    // Mendapatkan Total Poin Berdasarkan Bulan Dan Tahun Untuk Pimpinan
    @GetMapping("/pimpinan/total-by-month-year")
    public CommonResponse<?> getTotalPoinByMonthAndYear(@RequestParam("idTeknisi") Long idTeknisi,
                                             @RequestParam("year") int year,
                                             @RequestParam("month") int month) {
        return ResponseHelper.ok( poinService.getTotalPoinByMonthAndYear(idTeknisi, year, month));
    }

    // Menambahkan Riwayat Poin Baru
    @PostMapping("/add")
    public CommonResponse<PoinHistory> add(@RequestBody PoinHistoryDTO poinHistoryDTO) {
        return ResponseHelper.ok(poinService.add(poinHistoryDTO));
    }

    // Mendapatkan Semua Riwayat Poin
    @GetMapping
    public List<PoinHistory> getAllPoinHistory() {
        return poinService.getAllPoinHistory();
    }
}
