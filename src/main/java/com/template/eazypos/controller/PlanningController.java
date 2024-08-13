package com.template.eazypos.controller;

import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Kunjungan;
import com.template.eazypos.model.Planning;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.service.itc.PlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/planning")
@CrossOrigin(origins = "*")
public class PlanningController {
    @Autowired
    private PlanningService planningService;

    @GetMapping
    public CommonResponse<List<Planning>> getAll() {
        return ResponseHelper.ok(planningService.getAll());
    }

    @GetMapping("/date")
    public CommonResponse<List<Planning>> getByDate(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir) {
        return ResponseHelper.ok(planningService.getByTanggal(tanggalAwal, tanggalAkhir));
    }
    @GetMapping("/customer")
    public CommonResponse<List<Planning>> getByCustomer(@RequestParam(name = "id_customer") Long idCustomer){
        return ResponseHelper.ok(planningService.getByCustomer(idCustomer));
    }
}
