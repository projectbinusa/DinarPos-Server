package com.template.eazypos.controller;

import com.template.eazypos.dto.AddServiceDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.ServiceBarang;
import com.template.eazypos.service.itc.admin.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/service")
@CrossOrigin(origins = "*")
public class ServiceController {
    @Autowired
    private DataService dataService;

    @PostMapping("/add")
    public CommonResponse<ServiceBarang> add(@RequestBody AddServiceDTO addServiceDTO) {
        return ResponseHelper.ok(dataService.addService(addServiceDTO));
    }
    @GetMapping("/{id}")
    public CommonResponse<ServiceBarang> getById(@PathVariable("id") Long id) {
        return ResponseHelper.ok(dataService.getById(id));
    }
    @GetMapping
    public CommonResponse<List<ServiceBarang>> getAll() {
        return ResponseHelper.ok(dataService.getAll());
    }
    @GetMapping("/taken")
    public CommonResponse<List<ServiceBarang>> getAllByTaken() {
        return ResponseHelper.ok(dataService.getByTaken());
    }
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id") Long id) {
        return ResponseHelper.ok(dataService.delete(id));
    }
    @GetMapping("/tanggal")
    public CommonResponse<List<ServiceBarang>> getAllByTanggalAndStatus(@RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal, @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir , @RequestParam("status") String status) {
        return ResponseHelper.ok(dataService.getByTanggalAndStatus(tanggalAwal,tanggalAkhir,status));
    }

}
