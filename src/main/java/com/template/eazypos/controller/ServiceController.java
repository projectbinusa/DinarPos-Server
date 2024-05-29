package com.template.eazypos.controller;

import com.template.eazypos.dto.*;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.ServiceBarang;
import com.template.eazypos.model.Status;
import com.template.eazypos.model.TglKonf;
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
    @PostMapping("/taken")
    public CommonResponse<Status> add(@RequestBody TakenServiceDTO takenServiceDTO) {
        return ResponseHelper.ok(dataService.takenService(takenServiceDTO));
    }
    @PostMapping("/konfirm")
    public CommonResponse<TglKonf> konfirm(@RequestBody KonfirmDTO konfirmDTO) {
        return ResponseHelper.ok(dataService.konfirm(konfirmDTO));
    }
    @PutMapping("/service_admin/{id}")
    public CommonResponse<ServiceBarang> serviceAdmin(@RequestBody ServiceAdminDTO serviceAdminDTO , @PathVariable("id") Long id) {
        return ResponseHelper.ok(dataService.serviceAdmin(serviceAdminDTO , id));
    }
    @PutMapping("/update_note/{id}")
    public CommonResponse<ServiceBarang> updateNote(@RequestBody NoteDTO noteDTO , @PathVariable("id") Long id) {
        return ResponseHelper.ok(dataService.updateNote(noteDTO , id));
    }
    @PutMapping("/update_customer/{id}")
    public CommonResponse<ServiceBarang> updateCustomer(@RequestBody UpdateCustomerDTO updateCustomerDTO , @PathVariable("id") Long id) {
        return ResponseHelper.ok(dataService.updateCustomer(updateCustomerDTO , id));
    }
    @PutMapping("/taken_service/{id}")
    public CommonResponse<ServiceBarang> takenService(@RequestBody TransaksiPenjualanDTO transaksiPenjualanDTO , @PathVariable("id") Long id) {
        return ResponseHelper.ok(dataService.takenServiceCustomer(transaksiPenjualanDTO , id));
    }
    @GetMapping("/{id}")
    public CommonResponse<ServiceBarang> getById(@PathVariable("id") Long id) {
        return ResponseHelper.ok(dataService.getById(id));
    }
    @PostMapping("/take_over")
    public CommonResponse<ServiceBarang> add(@RequestBody TakeOverDTO takeOverDTO) {
        return ResponseHelper.ok(dataService.takeOver(takeOverDTO));
    }
    @GetMapping
    public CommonResponse<List<ServiceBarang>> getAll() {
        return ResponseHelper.ok(dataService.getAll());
    }
    @GetMapping("/taken")
    public CommonResponse<List<ServiceBarang>> getAllByTaken() {
        return ResponseHelper.ok(dataService.getByTaken());
    }
    @GetMapping("/tgl_konfirm")
    public CommonResponse<List<TglKonf>> getAllByTglKonfirm(Long id) {
        return ResponseHelper.ok(dataService.getAllKonfirm(id));
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
