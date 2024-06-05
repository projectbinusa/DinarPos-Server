package com.template.eazypos.controller;

import com.template.eazypos.dto.*;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.*;
import com.template.eazypos.service.itc.admin.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @GetMapping("take/{id}")
    public CommonResponse<List<Take>> getTakeByIdTT(@PathVariable("id") Long id){
        return ResponseHelper.ok(dataService.getTakeByIdTT(id));
    }
    @GetMapping("status/{id}")
    public CommonResponse<List<Status>> getStatusByIdTT(@PathVariable("id") Long id){
        return ResponseHelper.ok(dataService.getStatusByIdTT(id));
    }
    @PostMapping("/proses")
    public CommonResponse<Status> proses(@RequestBody TakenServiceDTO takenServiceDTO) {
        return ResponseHelper.ok(dataService.prosesService(takenServiceDTO));
    }
    @PostMapping("/tambah_status")
    public CommonResponse<Status> tambahStatus(@RequestBody TakenServiceDTO takenServiceDTO) {
        return ResponseHelper.ok(dataService.prosesServiceTeknisi(takenServiceDTO));
    }
    @PutMapping(path = "/foto_before/{id}" , consumes = "multipart/form-data")
    public CommonResponse<ServiceBarang> fotoBefore(@RequestPart("file") MultipartFile multipartFile , Long id) {
        return ResponseHelper.ok(dataService.fotoBefore(multipartFile ,id));
    }
    @PutMapping(path = "/foto_after/{id}" , consumes = "multipart/form-data")
    public CommonResponse<ServiceBarang> fotoAfter(@RequestPart("file") MultipartFile multipartFile , Long id) {
        return ResponseHelper.ok(dataService.fotoAfter(multipartFile , id));
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
    public CommonResponse<Transaksi> takenService(@RequestBody TransaksiPenjualanDTO transaksiPenjualanDTO , @PathVariable("id") Long id) {
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
    @DeleteMapping("/tgl_konfirm/{id}")
    public CommonResponse<?> deleteTglKonf(@PathVariable("id") Long id) {
        return ResponseHelper.ok(dataService.deleteTglKonf(id));
    }
    @GetMapping("/tanggal")
    public CommonResponse<List<ServiceBarang>> getAllByTanggalAndStatus(@RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal, @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir , @RequestParam("status") String status) {
        return ResponseHelper.ok(dataService.getByTanggalAndStatus(tanggalAwal,tanggalAkhir,status));
    }

    @GetMapping("/data-service")
    public List<Object[]> getDataService(@RequestParam("months") @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return dataService.getDataService(months);
    }

    @GetMapping("/total-service-elektro")
    public int getTotalServiceElektro(@RequestParam("months") @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return dataService.getTotalServiceElektro(months);
    }

    @GetMapping("/total-service-cpu")
    public int getTotalServiceCPU(@RequestParam("months") @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return dataService.getTotalServiceCPU(months);
    }

    @GetMapping("/total-service-success-elektro")
    public int getTotalServiceSuccessElektro(@RequestParam("months") @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return dataService.getTotalServiceSuccessElektro(months);
    }

    @GetMapping("/total-service-not-elektro")
    public int getTotalServiceNotElektro(@RequestParam("months") @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return dataService.getTotalServiceNotElektro(months);
    }

    @GetMapping("/total-service-not-cpu")
    public int getTotalServiceNotCPU(@RequestParam("months") @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return dataService.getTotalServiceNotCPU(months);
    }

    @GetMapping("/total-service-success-cpu")
    public int getTotalServiceSuccessCPU(@RequestParam("months") @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return dataService.getTotalServiceSuccessCPU(months);
    }

}
