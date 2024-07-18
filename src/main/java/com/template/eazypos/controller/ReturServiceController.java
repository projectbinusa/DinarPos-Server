package com.template.eazypos.controller;

import com.template.eazypos.dto.AddServiceDTO;
import com.template.eazypos.dto.GetServiceReturDTO;
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

    // Buat Pengembalian Baru Untuk Layanan Tertentu Yang Diidentifikasi ID
    @PostMapping("/{id}")
    public CommonResponse<ServiceBarang> returService(@PathVariable("id") Long id , @RequestBody AddServiceDTO addServiceDTO) {
        return ResponseHelper.ok(returService.retur(id , addServiceDTO));
    }

    // Mendapatkan Semua Data Layanan Pengembalian
    @GetMapping
    public CommonResponse<List<GetServiceReturDTO>> getAll(){
        return ResponseHelper.ok( returService.getAll());
    }

    // Mendapatkan Layanan Yang Siap Dalam Rentang Tanggal
    @GetMapping("/ready")
    public List<ServiceBarang> getServiceReady(
            @RequestParam("tglawal") String tglAwal,
            @RequestParam("tglakhir") String tglAkhir
    ) {
        return returService.getServiceReady(tglAwal, tglAkhir);
    }

    // Mendapatkan Layanan Yang Tidak Tersedia Dalam Rentang Tanggal
    @GetMapping("/na")
    public List<ServiceBarang> getServiceNA(
            @RequestParam("tglawal") String tglAwal,
            @RequestParam("tglakhir") String tglAkhir
    ) {
        return returService.getServiceNA(tglAwal, tglAkhir);
    }

    // Mendapatkan Layanan Berdasarkan Status Proses
    @GetMapping("/proses")
    public List<ServiceBarang> getServiceByStatusAndTanggalMasuk(
            @RequestParam("status") String status,
            @RequestParam("tglawal") String tglAwal,
            @RequestParam("tglakhir") String tglAkhir
    ) {
        return returService.getServiceByStatusAndTanggalMasuk(status, tglAwal, tglAkhir);
    }

    // Dapatkan Layanan Yang Dikembalikan Dalam Rentang Tanggal Berdasarkan Retur
    @GetMapping("/retur")
    public List<ServiceBarang> getServiceRetur(
            @RequestParam("tglawal") String tglAwal,
            @RequestParam("tglakhir") String tglAkhir
    ) {
        return returService.getServiceRetur(tglAwal, tglAkhir);
    }

    // Dapatkan Layanan Yang Dikembalikan Dalam Rentang Tanggal Berdasarkan Cancel
    @GetMapping("/cancel")
    public List<ServiceBarang> getServiceCancel(
            @RequestParam("tglawal") String tglAwal,
            @RequestParam("tglakhir") String tglAkhir
    ) {
        return returService.getServiceCancel(tglAwal, tglAkhir);
    }

    // Mendapatakn Layanan Dengan Notifikasi Dalam Rentang Tanggal
    @GetMapping("/notifications")
    public List<ServiceBarang> getServiceNotifications(
            @RequestParam("tglawal") String tglAwal,
            @RequestParam("tglakhir") String tglAkhir
    ) {
        return returService.getServiceNotif(tglAwal, tglAkhir);
    }

    // Dapatkan Layanan Yang Diambil Dalam Rentang Tanggal Sesuai Taken
    @GetMapping("taken")
    public List<ServiceBarang> getTakenServices(
            @RequestParam("tglawal") String tglAwal,
            @RequestParam("tglakhir") String tglAkhir
    ) {
        return returService.getTakenServices(tglAwal, tglAkhir);
    }
}
