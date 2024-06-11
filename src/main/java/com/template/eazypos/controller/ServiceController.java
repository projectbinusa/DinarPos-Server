package com.template.eazypos.controller;

import com.template.eazypos.auditing.Pagination;
import com.template.eazypos.dto.*;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.PaginationResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.*;
import com.template.eazypos.service.itc.admin.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @PostMapping("/proses/{id}")
    public CommonResponse<Status> proses(@RequestBody TakenServiceDTO takenServiceDTO , @PathVariable("id") Long id) {
        return ResponseHelper.ok(dataService.prosesService(takenServiceDTO , id));
    }
    @PostMapping("/tambah_status")
    public CommonResponse<Status> tambahStatus( @RequestParam(name = "id") Long id,@RequestBody TakenServiceDTO takenServiceDTO) {
        return ResponseHelper.ok(dataService.prosesServiceTeknisi(takenServiceDTO , id));
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
    @GetMapping("/taken/N")
    public CommonResponse<List<ServiceBarang>> getAllByTakenN() {
        return ResponseHelper.ok(dataService.getTakenN());
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
    public CommonResponse<List<ServiceDataDTO>> getDataService(@RequestParam("months") @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return ResponseHelper.ok(dataService.findDataService(months));
    }

    @GetMapping("/total-service-elektro")
    public CommonResponse<Integer> getTotalServiceElektro(@RequestParam("months") @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return ResponseHelper.ok(dataService.getTotalServiceElektro(months));
    }

    @GetMapping("/total-service-cpu")
    public CommonResponse<Integer> getTotalServiceCPU(@RequestParam("months") @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return ResponseHelper.ok( dataService.getTotalServiceCPU(months));
    }

    @GetMapping("/total-service-success-elektro")
    public CommonResponse<Integer> getTotalServiceSuccessElektro(@RequestParam("months") @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return ResponseHelper.ok( dataService.getTotalServiceSuccessElektro(months));
    }

    @GetMapping("/total-service-not-elektro")
    public CommonResponse<Integer> getTotalServiceNotElektro(@RequestParam("months") @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return ResponseHelper.ok( dataService.getTotalServiceNotElektro(months));
    }

    @GetMapping("/total-service-not-cpu")
    public CommonResponse<Integer> getTotalServiceNotCPU(@RequestParam("months") @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return ResponseHelper.ok( dataService.getTotalServiceNotCPU(months));
    }

    @GetMapping("/total-service-success-cpu")
    public CommonResponse<Integer> getTotalServiceSuccessCPU(@RequestParam("months") @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return ResponseHelper.ok( dataService.getTotalServiceSuccessCPU(months));
    }
    @GetMapping("/dashboard/teknisi")
    public CommonResponse<List<ServiceBarang>> getService() {
        return ResponseHelper.ok( dataService.getService());
    }
    @GetMapping("/dashboard/teknisi/filter")
    public CommonResponse<List<ServiceBarang>> filterService(
            @RequestParam("awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date awal,
            @RequestParam("akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date akhir,
            @RequestParam(value = "status", required = false) String status) {
        return ResponseHelper.ok( dataService.filterServiceByDateAndStatus(awal, akhir, status));
    }

    @GetMapping("/dashboard/teknisi/filter/status")
    public CommonResponse<List<ServiceBarang>> filterServiceByStatus(@RequestParam("status") String status) {
        return ResponseHelper.ok( dataService.filterServiceByStatus(status));
    }

    @GetMapping("/dashboard/teknisi/filter/tanggal")
    public CommonResponse<List<ServiceBarang>> filterServiceByDateRange(@RequestParam("awal") @DateTimeFormat(pattern="yyyy-MM-dd") Date awal,
                                                        @RequestParam("akhir") @DateTimeFormat(pattern="yyyy-MM-dd") Date akhir) {
        return ResponseHelper.ok( dataService.filterServiceByDateRange(awal, akhir));
    }

    @GetMapping("/cancel")
    public CommonResponse<List<ServiceBarang>> getServiceCancel() {
      return ResponseHelper.ok(dataService.getServiceCancel());
    }

    @GetMapping("/cancel/filter")
    public CommonResponse<List<ServiceBarang>> getTglFilterServiceCancel(
            @RequestParam("awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date awal,
            @RequestParam("akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date akhir) {
       return ResponseHelper.ok(dataService.getTglFilterServiceCancel(awal , akhir));
    }
    @GetMapping("/my-service")
    public CommonResponse<List<ServiceBarang>> getMyServices(@RequestParam Long teknisiId) {
        return ResponseHelper.ok(dataService.getMyServices(teknisiId));
    }

    @GetMapping("/my-service-retur")
    public CommonResponse<List<ServiceBarang>> getMyServicesRetur(@RequestParam Long teknisiId) {
        return ResponseHelper.ok(dataService.getMyServicesRetur(teknisiId));
    }

    @GetMapping("/service-taken")
    public CommonResponse<List<ServiceBarang>> getServiceTaken() {
        return ResponseHelper.ok( dataService.getServiceTaken());
    }

    @GetMapping("/count")
    public CommonResponse<Long> countAllServices() {
        return ResponseHelper.ok(dataService.countAllServices());
    }

    @PutMapping("/status-new")
    public CommonResponse<Status>aksiStatusNew(@RequestParam Long idTT, @RequestParam Long teknisiId,
                                 @RequestParam String status, @RequestParam String solusi,
                                 @RequestParam String ket, @RequestParam String validasi) {
        return ResponseHelper.ok( dataService.aksiStatusNew(idTT, teknisiId, status, solusi, ket, validasi));
    }
    @PutMapping("/aksi-status-plus")
    public CommonResponse<Status> aksiStatusPlus(@RequestParam Long idTT, @RequestParam Long teknisiId,
                                  @RequestParam String status, @RequestParam String solusi,
                                  @RequestParam String ket, @RequestParam String validasi) {
        return ResponseHelper.ok(dataService.aksiStatusPlus(idTT, teknisiId, status, solusi, ket, validasi));
    }
    @PutMapping("/aksi-take-over")
    public CommonResponse<Take> aksiTakeOver(@RequestParam Long idTT, @RequestParam Long teknisiId,
                                @RequestParam Long takeTeknisiId) {
        return ResponseHelper.ok(dataService.aksiTakeOver(idTT, teknisiId, takeTeknisiId));
    }

    @GetMapping(path = "/pagination/takenN")
    public PaginationResponse<List<ServiceBarang>> getAll(
            @RequestParam(defaultValue = Pagination.page, required = false) Long page,
            @RequestParam(defaultValue = Pagination.limit, required = false) Long limit,
            @RequestParam(defaultValue = Pagination.sort, required = false) String sort,
            @RequestParam(required = false) String search
    ) {
        Page<ServiceBarang> servicePage;

        if (search != null && !search.isEmpty()) {
            servicePage = dataService.getAllWithPagination(page, limit, sort, search);
        } else {
            servicePage = dataService.getAllWithPagination(page, limit, sort, null);
        }

        List<ServiceBarang> serviceBarangs = servicePage.getContent();
        long totalItems = servicePage.getTotalElements();
        int totalPages = servicePage.getTotalPages();

        Map<String, Long> pagination = new HashMap<>();
        pagination.put("total", totalItems);
        pagination.put("page", page);
        pagination.put("total_page", (long) totalPages);

        return ResponseHelper.okWithPagination(serviceBarangs, pagination);
    }

}
