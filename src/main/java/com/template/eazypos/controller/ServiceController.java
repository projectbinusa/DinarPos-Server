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

    // Endpoint untuk menambah layanan barang
    @PostMapping("/add")
    public CommonResponse<ServiceBarang> add(@RequestBody AddServiceDTO addServiceDTO) {
        return ResponseHelper.ok(dataService.addService(addServiceDTO));
    }

    // Endpoint untuk mendapatkan data "take" berdasarkan ID TT
    @GetMapping("take/{id}")
    public CommonResponse<List<Take>> getTakeByIdTT(@PathVariable("id") Long id) {
        return ResponseHelper.ok(dataService.getTakeByIdTT(id));
    }

    // Endpoint untuk mendapatkan data "status" berdasarkan ID TT
    @GetMapping("status/{id}")
    public CommonResponse<List<Status>> getStatusByIdTT(@PathVariable("id") Long id) {
        return ResponseHelper.ok(dataService.getStatusByIdTT(id));
    }

    // Endpoint untuk proses layanan berdasarkan ID
    @PostMapping("/proses/{id}")
    public CommonResponse<Status> proses(@RequestBody TakenServiceDTO takenServiceDTO, @PathVariable("id") Long id) {
        return ResponseHelper.ok(dataService.prosesService(takenServiceDTO, id));
    }

    // Endpoint untuk tambah status layanan
    @PostMapping("/tambah_status")
    public CommonResponse<Status> tambahStatus(@RequestParam(name = "id") Long id, @RequestBody TakenServiceDTO takenServiceDTO) {
        return ResponseHelper.ok(dataService.prosesServiceTeknisi(takenServiceDTO, id));
    }

    // Endpoint untuk mengunggah foto sebelum layanan
    @PutMapping(path = "/foto_before/{id}", consumes = "multipart/form-data")
    public CommonResponse<ServiceBarang> fotoBefore(@RequestPart("file") MultipartFile multipartFile, @PathVariable Long id) {
        return ResponseHelper.ok(dataService.fotoBefore(multipartFile, id));
    }

    // Endpoint untuk mengunggah foto setelah layanan
    @PutMapping(path = "/foto_after/{id}", consumes = "multipart/form-data")
    public CommonResponse<ServiceBarang> fotoAfter(@RequestPart("file") MultipartFile multipartFile, @PathVariable Long id) {
        return ResponseHelper.ok(dataService.fotoAfter(multipartFile, id));
    }

    // Endpoint untuk konfirmasi tanggal
    @PostMapping("/konfirm")
    public CommonResponse<TglKonf> konfirm(@RequestBody KonfirmDTO konfirmDTO) {
        return ResponseHelper.ok(dataService.konfirm(konfirmDTO));
    }

    // Endpoint untuk admin layanan
    @PutMapping("/service_admin/{id}")
    public CommonResponse<ServiceBarang> serviceAdmin(@RequestBody ServiceAdminDTO serviceAdminDTO, @PathVariable Long id) {
        return ResponseHelper.ok(dataService.serviceAdmin(serviceAdminDTO, id));
    }

    // Endpoint untuk update catatan
    @PutMapping("/update_note/{id}")
    public CommonResponse<ServiceBarang> updateNote(@RequestBody NoteDTO noteDTO, @PathVariable Long id) {
        return ResponseHelper.ok(dataService.updateNote(noteDTO, id));
    }

    // Endpoint untuk update pelanggan
    @PutMapping("/update_customer/{id}")
    public CommonResponse<ServiceBarang> updateCustomer(@RequestBody UpdateCustomerDTO updateCustomerDTO, @PathVariable Long id) {
        return ResponseHelper.ok(dataService.updateCustomer(updateCustomerDTO, id));
    }

    // Endpoint untuk layanan yang diambil oleh pelanggan
    @PutMapping("/taken_service/{id}")
    public CommonResponse<Transaksi> takenService(@RequestBody TransaksiPenjualanDTO transaksiPenjualanDTO, @PathVariable Long id) {
        return ResponseHelper.ok(dataService.takenServiceCustomer(transaksiPenjualanDTO, id));
    }

    // Endpoint untuk mendapatkan data layanan berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse<ServiceBarang> getById(@PathVariable Long id) {
        return ResponseHelper.ok(dataService.getById(id));
    }

    // Endpoint untuk take over layanan
    @PostMapping("/take_over")
    public CommonResponse<ServiceBarang> add(@RequestBody TakeOverDTO takeOverDTO) {
        return ResponseHelper.ok(dataService.takeOver(takeOverDTO));
    }

    // Endpoint untuk mendapatkan semua layanan barang
    @GetMapping
    public CommonResponse<List<ServiceBarang>> getAll() {
        return ResponseHelper.ok(dataService.getAll());
    }

    // Endpoint untuk mendapatkan semua layanan yang telah diambil
    @GetMapping("/taken")
    public CommonResponse<List<ServiceBarang>> getAllByTaken() {
        return ResponseHelper.ok(dataService.getByTaken());
    }

    // Endpoint untuk mendapatkan semua layanan yang belum diambil
    @GetMapping("/taken/N")
    public CommonResponse<List<ServiceBarang>> getAllByTakenN() {
        return ResponseHelper.ok(dataService.getTakenN());
    }

    // Endpoint untuk mendapatkan semua tanggal konfirmasi
    @GetMapping("/tgl_konfirm")
    public CommonResponse<List<TglKonf>> getAllByTglKonfirm(@RequestParam Long id) {
        return ResponseHelper.ok(dataService.getAllKonfirm(id));
    }

    // Endpoint untuk menghapus layanan berdasarkan ID
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable Long id) {
        return ResponseHelper.ok(dataService.delete(id));
    }

    // Endpoint untuk menghapus tanggal konfirmasi berdasarkan ID
    @DeleteMapping("/tgl_konfirm/{id}")
    public CommonResponse<?> deleteTglKonf(@PathVariable Long id) {
        return ResponseHelper.ok(dataService.deleteTglKonf(id));
    }

    // Endpoint untuk mendapatkan layanan berdasarkan tanggal dan status
    @GetMapping("/tanggal")
    public CommonResponse<List<ServiceBarang>> getAllByTanggalAndStatus(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir,
            @RequestParam String status) {
        return ResponseHelper.ok(dataService.getByTanggalAndStatus(tanggalAwal, tanggalAkhir, status));
    }

    // Endpoint untuk mendapatkan data layanan berdasarkan bulan
    @GetMapping("/data-service")
    public CommonResponse<List<ServiceDataDTO>> getDataService(@RequestParam @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return ResponseHelper.ok(dataService.findDataService(months));
    }

    // Endpoint Untuk Mendapatkan Total Layanan Elektronik
    @GetMapping("/total-service-elektro")
    public CommonResponse<Integer> getTotalServiceElektro(@RequestParam @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return ResponseHelper.ok(dataService.getTotalServiceElektro(months));
    }

    // Endpoint Untuk Mendapatkan Total Layanan CPU
    @GetMapping("/total-service-cpu")
    public CommonResponse<Integer> getTotalServiceCPU(@RequestParam @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return ResponseHelper.ok(dataService.getTotalServiceCPU(months));
    }

    // Endpoint Untuk Mendapatkan Total Layanan Sukses Elektronik
    @GetMapping("/total-service-success-elektro")
    public CommonResponse<Integer> getTotalServiceSuccessElektro(@RequestParam @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return ResponseHelper.ok(dataService.getTotalServiceSuccessElektro(months));
    }

    // Endpoint Untuk Mendapatkan Total Layanan Tidak Elektronik
    @GetMapping("/total-service-not-elektro")
    public CommonResponse<Integer> getTotalServiceNotElektro(@RequestParam @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return ResponseHelper.ok(dataService.getTotalServiceNotElektro(months));
    }

    // Endpoint Untuk Mendapatkan Total Layanan Tidak CPU
    @GetMapping("/total-service-not-cpu")
    public CommonResponse<Integer> getTotalServiceNotCPU(@RequestParam @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return ResponseHelper.ok(dataService.getTotalServiceNotCPU(months));
    }

    // Endpoint Untuk Mendapatkan Total Layanan Sukses CPU
    @GetMapping("/total-service-success-cpu")
    public CommonResponse<Integer> getTotalServiceSuccessCPU(@RequestParam @DateTimeFormat(pattern = "yyyy-MM") Date months) {
        return ResponseHelper.ok(dataService.getTotalServiceSuccessCPU(months));
    }

    // Endpoint Untuk Mendapatkan Layanan Oleh Teknisi
    @GetMapping("/dashboard/teknisi")
    public CommonResponse<List<ServiceBarang>> getService() {
        return ResponseHelper.ok(dataService.getService());
    }

    // Endpoint untuk filter layanan berdasarkan tanggal awal, tanggal akhir, dan status
    @GetMapping("/dashboard/teknisi/filter")
    public CommonResponse<List<ServiceBarang>> filterService(
            @RequestParam("awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date awal,
            @RequestParam("akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date akhir,
            @RequestParam(value = "status", required = false) String status) {
        return ResponseHelper.ok(dataService.filterServiceByDateAndStatus(awal, akhir, status));
    }

    // Endpoint untuk filter layanan berdasarkan status
    @GetMapping("/dashboard/teknisi/filter/status")
    public CommonResponse<List<ServiceBarang>> filterServiceByStatus(@RequestParam("status") String status) {
        return ResponseHelper.ok(dataService.filterServiceByStatus(status));
    }

    // Endpoint untuk filter layanan berdasarkan tanggal awal dan tanggal akhir
    @GetMapping("/dashboard/teknisi/filter/tanggal")
    public CommonResponse<List<ServiceBarang>> filterServiceByDateRange(
            @RequestParam("awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date awal,
            @RequestParam("akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date akhir) {
        return ResponseHelper.ok(dataService.filterServiceByDateRange(awal, akhir));
    }

    // Endpoint untuk mendapatkan layanan yang dibatalkan
    @GetMapping("/cancel")
    public CommonResponse<List<ServiceBarang>> getServiceCancel() {
        return ResponseHelper.ok(dataService.getServiceCancel());
    }

    // Endpoint untuk filter layanan yang dibatalkan berdasarkan tanggal awal dan tanggal akhir
    @GetMapping("/cancel/filter")
    public CommonResponse<List<ServiceBarang>> getTglFilterServiceCancel(
            @RequestParam("awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date awal,
            @RequestParam("akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date akhir) {
        return ResponseHelper.ok(dataService.getTglFilterServiceCancel(awal, akhir));
    }

    // Endpoint untuk mendapatkan layanan berdasarkan ID teknisi
    @GetMapping("/my-service")
    public CommonResponse<List<ServiceBarang>> getMyServices(@RequestParam Long teknisiId) {
        return ResponseHelper.ok(dataService.getMyServices(teknisiId));
    }

    // Endpoint untuk mendapatkan layanan retur berdasarkan ID teknisi
    @GetMapping("/my-service-retur")
    public CommonResponse<List<ServiceBarang>> getMyServicesRetur(@RequestParam Long teknisiId) {
        return ResponseHelper.ok(dataService.getMyServicesRetur(teknisiId));
    }

    // Endpoint untuk mendapatkan layanan yang telah diambil
    @GetMapping("/service-taken")
    public CommonResponse<List<ServiceBarang>> getServiceTaken() {
        return ResponseHelper.ok(dataService.getServiceTaken());
    }

    // Endpoint untuk menghitung jumlah total layanan
    @GetMapping("/count")
    public CommonResponse<Long> countAllServices() {
        return ResponseHelper.ok(dataService.countAllServices());
    }

    // Endpoint untuk mengubah status layanan menjadi baru
    @PutMapping("/status-new")
    public CommonResponse<Status> aksiStatusNew(@RequestParam Long idTT, @RequestParam Long teknisiId,
                                                @RequestParam String status, @RequestParam String solusi,
                                                @RequestParam String ket, @RequestParam String validasi) {
        return ResponseHelper.ok(dataService.aksiStatusNew(idTT, teknisiId, status, solusi, ket, validasi));
    }

    // Endpoint untuk menambahkan status layanan
    @PutMapping("/aksi-status-plus")
    public CommonResponse<Status> aksiStatusPlus(@RequestParam Long idTT, @RequestParam Long teknisiId,
                                                 @RequestParam String status, @RequestParam String solusi,
                                                 @RequestParam String ket, @RequestParam String validasi) {
        return ResponseHelper.ok(dataService.aksiStatusPlus(idTT, teknisiId, status, solusi, ket, validasi));
    }

    // Endpoint untuk melakukan take over layanan
    @PutMapping("/aksi-take-over")
    public CommonResponse<Take> aksiTakeOver(@RequestParam Long idTT, @RequestParam Long teknisiId,
                                             @RequestParam Long takeTeknisiId) {
        return ResponseHelper.ok(dataService.aksiTakeOver(idTT, teknisiId, takeTeknisiId));
    }

    // Endpoint untuk mendapatkan data layanan dengan pagination
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

    // Endpoint untuk mendapatkan layanan yang diambil oleh pimpinan
    @GetMapping("/service-taken-pimpinan")
    public List<ServiceBarang> getServiceTakenPimpinan() {
        return dataService.getServiceTakenPimpinan();
    }

    // Endpoint untuk mendapatkan layanan berdasarkan tanggal awal dan tanggal akhir yang diambil
    @GetMapping("/service-taken-by-date")
    public List<ServiceBarang> getServiceTakenByDateRange(
            @RequestParam("awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date awal,
            @RequestParam("akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date akhir) {
        return dataService.getServiceTakenByDateRange(awal, akhir);
    }
    @GetMapping("/get-service-barang")
    public List<Object[]> getServiceBarang() {
        return
        dataService.getServiceBarang();
    }

    @GetMapping("/get-service-barang-taken")
    public List<Object[]> getServiceBarangTaken() {
        return
        dataService.getServiceBarangTaken();
    }

    @GetMapping("/filter-service-barang")
    public List<ServiceBarang> filterServiceBarang(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date awal,
                                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date akhir,
                                                   @RequestParam(required = false) String status) {
        if (status != null) {
            return
            dataService.getServiceBarangByDateRangeAndStatus(awal, akhir, status);
        } else {
            return
            dataService.getServiceBarangByDateRange(awal, akhir);
        }
    }

    @GetMapping("/filter-service-barang-status")
    public List<ServiceBarang> filterServiceBarangStatus(@RequestParam String status) {
        return
        dataService.getServiceBarangByStatus(status);
    }

    @GetMapping("/filter-service-barang-date")
    public List<ServiceBarang> filterServiceBarangDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date awal,
                                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date akhir) {
        return
        dataService.getServiceBarangByDateRange(awal, akhir);
    }

    @GetMapping("/get-status-by-id-tt/{idTt}")
    public List<Status> getStatusByIdTt(@PathVariable Long idTt) {
        return
        dataService.getStatusByIdTt(idTt);
    }

    @GetMapping("/get-tgl-konfimasi-by-id-tt/{idTt}")
    public List<TglKonf> getTglKonfimasiByIdTt(@PathVariable Long idTt) {
        return
        dataService.getTglKonfimasiByIdTt(idTt);
    }
    @GetMapping("/finish/pimpinan/{month}")
    public List<ServiceReportDTO> getServiceReportByMonth(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date month) {
        return dataService.getServiceReportByMonth(month);
    }


    @GetMapping("/total-elektro")
    public int getTotalServiceElektro(@RequestParam String months) {
        return dataService.totalServiceElektro(months);
    }

    @GetMapping("/total-cpu")
    public int getTotalServiceCpu(@RequestParam String months) {
        return dataService.totalServiceCpu(months);
    }

    @GetMapping("/total-success-elektro")
    public int getTotalServiceSuccessElektro(@RequestParam String months) {
        return dataService.totalServiceSuccessElektro(months);
    }

    @GetMapping("/total-not-elektro")
    public int getTotalServiceNotElektro(@RequestParam String months) {
        return dataService.totalServiceNotElektro(months);
    }

    @GetMapping("/total-success-cpu")
    public int getTotalServiceSuccessCpu(@RequestParam String months) {
        return dataService.totalServiceSuccessCpu(months);
    }

    @GetMapping("/total-not-cpu")
    public int getTotalServiceNotCpu(@RequestParam String months) {
        return dataService.totalServiceNotCpu(months);
    }

}
