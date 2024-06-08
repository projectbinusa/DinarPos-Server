package com.template.eazypos.controller;

import com.template.eazypos.dto.AddServiceDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Retur;
import com.template.eazypos.model.ServiceBarang;
import com.template.eazypos.service.itc.admin.ReturService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/return/service")
@CrossOrigin(origins = "*")
public class ReturServiceController {
    @Autowired
    ReturService returService;

    @PostMapping("/{id}")
    public CommonResponse<ServiceBarang> returService(@PathVariable("id") Long id , @RequestBody AddServiceDTO addServiceDTO) {
        return ResponseHelper.ok(returService.retur(id , addServiceDTO));
    }
    @GetMapping
    public CommonResponse<List<Retur>> getAll(){
        return ResponseHelper.ok( returService.getAll());
    }

    @GetMapping("/ready")
    public List<ServiceBarang> getServiceReady(
            @RequestParam("tglawal") String tglAwal,
            @RequestParam("tglakhir") String tglAkhir
    ) {
        return returService.getServiceReady(tglAwal, tglAkhir);
    }

    @GetMapping("/na")
    public List<ServiceBarang> getServiceNA(
            @RequestParam("tglawal") String tglAwal,
            @RequestParam("tglakhir") String tglAkhir
    ) {
        return returService.getServiceNA(tglAwal, tglAkhir);
    }

    @GetMapping("/proses")
    public List<ServiceBarang> getServiceByStatusAndTanggalMasuk(
            @RequestParam("status") String status,
            @RequestParam("tglawal") String tglAwal,
            @RequestParam("tglakhir") String tglAkhir
    ) {
        return returService.getServiceByStatusAndTanggalMasuk(status, tglAwal, tglAkhir);
    }

    @GetMapping("/retur")
    public List<ServiceBarang> getServiceRetur(
            @RequestParam("tglawal") String tglAwal,
            @RequestParam("tglakhir") String tglAkhir
    ) {
        return returService.getServiceRetur(tglAwal, tglAkhir);
    }

    @GetMapping("/cancel")
    public List<ServiceBarang> getServiceCancel(
            @RequestParam("tglawal") String tglAwal,
            @RequestParam("tglakhir") String tglAkhir
    ) {
        return returService.getServiceCancel(tglAwal, tglAkhir);
    }

    @GetMapping("/notifications")
    public List<ServiceBarang> getServiceNotifications(
            @RequestParam("tglawal") String tglAwal,
            @RequestParam("tglakhir") String tglAkhir
    ) {
        return returService.getServiceNotif(tglAwal, tglAkhir);
    }

    @GetMapping("taken")
    public List<ServiceBarang> getTakenServices(
            @RequestParam("tglawal") String tglAwal,
            @RequestParam("tglakhir") String tglAkhir
    ) {
        return returService.getTakenServices(tglAwal, tglAkhir);
    }
}
