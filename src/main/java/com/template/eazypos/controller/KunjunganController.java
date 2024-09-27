package com.template.eazypos.controller;

import com.template.eazypos.dto.KunjunganDTO;
import com.template.eazypos.dto.OmzetDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.*;
import com.template.eazypos.service.eazypos.SalesmanService;
import com.template.eazypos.service.eazypos.excel.ExcelKunjunganAllService;
import com.template.eazypos.service.eazypos.excel.ExcelReview;
import com.template.eazypos.service.itc.KunjunganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/kunjungan")
@CrossOrigin(origins = "*")
public class KunjunganController {
    @Autowired
    private KunjunganService kunjunganService;

    @Autowired
    private ExcelKunjunganAllService excelKunjunganAllService;

    @Autowired
    private ExcelReview excelReview;




    @GetMapping
    public CommonResponse<List<Kunjungan>> getAll() {
        return ResponseHelper.ok(kunjunganService.getAll());
    }

    @PostMapping("/add-n_visit")
    public CommonResponse<?> calculateVisit(@RequestParam Long idSalesman, @RequestParam Long idCustomer, @RequestParam String visitType) {
        return ResponseHelper.ok(kunjunganService.calculateVisit(idSalesman, idCustomer, visitType));
    }
    @GetMapping("/{id}")
    public CommonResponse<Kunjungan> get(@PathVariable("id") Long id) {
        return ResponseHelper.ok(kunjunganService.getById(id));
    }

    @GetMapping("/deal_po/salesman")
    public CommonResponse<List<Kunjungan>> getDealPoAndSalesman(@RequestParam("nama_salesman") String nama) {
        return ResponseHelper.ok(kunjunganService.getDealPoAndSalesman(nama));
    }

    @GetMapping("/deal_finish/salesman")
    public CommonResponse<List<Kunjungan>> getDealFinishAndSalesman(@RequestParam("nama_salesman") String nama) {
        return ResponseHelper.ok(kunjunganService.getDealFinishAndSalesman(nama));
    }

    @GetMapping("/deal_finish")
    public CommonResponse<List<Kunjungan>> getDealFinish() {
        return ResponseHelper.ok(kunjunganService.getDealFinish());
    }

    @GetMapping("/deal_po")
    public CommonResponse<List<Kunjungan>> getDealPO() {
        return ResponseHelper.ok(kunjunganService.getDealPO());
    }

    @GetMapping("/date")
    public CommonResponse<List<Kunjungan>> getByDate(@RequestParam(name = "tanggal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggal) {
        return ResponseHelper.ok(kunjunganService.getByDate(tanggal));
    }

    @GetMapping("/date/salesman")
    public CommonResponse<List<Kunjungan>> getByDateSalesman(@RequestParam(name = "tanggal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggal, @RequestParam(name = "id_salesman") Long id) {
        return ResponseHelper.ok(kunjunganService.getByDateSalesman(tanggal, id));
    }

    @GetMapping("/date/between")
    public CommonResponse<List<Kunjungan>> getByDateBetween(@RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal, @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir) {
        return ResponseHelper.ok(kunjunganService.getByDateBetween(tanggalAwal, tanggalAkhir));
    }

    @GetMapping("/date/between/salesman")
    public CommonResponse<List<Kunjungan>> getByDateBetweenSalesman(@RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal, @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir, @RequestParam(name = "id_salesman") Long id) {
        return ResponseHelper.ok(kunjunganService.getByDateBetweenAndSalesman(tanggalAwal, tanggalAkhir, id));
    }

    @GetMapping("/customer")
    public CommonResponse<List<Kunjungan>> getByCustomer(@RequestParam(name = "id_customer") Long id_customer) {
        return ResponseHelper.ok(kunjunganService.getByCustomer(id_customer));
    }

    @GetMapping("/bulan")
    public CommonResponse<List<Kunjungan>> getByBulan(@RequestParam(name = "bulan") int bulan) {
        return ResponseHelper.ok(kunjunganService.getByBulan(bulan));
    }

    @GetMapping("/sync")
    public CommonResponse<List<Kunjungan>> getKunjunganSync() {
        return ResponseHelper.ok(kunjunganService.getKunjunganSync());
    }

    @GetMapping("/sync/tanggal")
    public CommonResponse<List<Kunjungan>> getKunjunganSyncTanggal(@RequestParam("tglAwal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAwal, @RequestParam("tglAkhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAkhir) {
        return ResponseHelper.ok(kunjunganService.getKunjunganSyncBetweenDate(tglAwal ,tglAkhir));
    }
    @GetMapping("/foto/not_null")
    public CommonResponse<List<Kunjungan>> getFoto(
            @RequestParam("tgl_awal") Date tgl1,
            @RequestParam("tgl_akhir") Date tgl2,
            @RequestParam("id_salesman") Long idm) {
        return ResponseHelper.ok( kunjunganService.getFotoNotNull(idm , tgl1 , tgl2));
    }
    @GetMapping("/sync/tanggal_beetwen/salesman")
    public CommonResponse<List<Kunjungan>> getKunjunganByDateBetweenAndSalesman(
            @RequestParam("tgl_awal") Date tgl1,
            @RequestParam("tgl_akhir") Date tgl2,
            @RequestParam("id_salesman") Long idm) {
        return ResponseHelper.ok( kunjunganService.findKunjunganByDateRangeAndSalesman(tgl1, tgl2, idm));
    }
    @GetMapping("/sync/tanggal/salesman")
    public CommonResponse<List<Kunjungan>> getKunjunganByDateAndSalesman(
            @RequestParam("tgl") Date tgl1,
            @RequestParam("id_salesman") Long idm) {
        return ResponseHelper.ok( kunjunganService.getSyncKunjunganBytgl(tgl1, idm));
    }

    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id") Long id) {
        return ResponseHelper.ok(kunjunganService.delete(id));
    }

    @PostMapping(path = "/add", consumes = "multipart/form-data")
    public CommonResponse<Kunjungan> add(KunjunganDTO kunjunganDTO, @RequestPart("foto") MultipartFile multipartFile) throws IOException {
        return ResponseHelper.ok(kunjunganService.add(kunjunganDTO, multipartFile));
    }

    @PutMapping(path = "/{id}", consumes = "multipart/form-data")
    public CommonResponse<Kunjungan> edit(KunjunganDTO kunjunganDTO, @PathVariable("id") Long id, @RequestPart("foto") MultipartFile multipartFile) throws IOException {
        return ResponseHelper.ok(kunjunganService.edit(id, kunjunganDTO, multipartFile));
    }

    @GetMapping("/export/kunjungan")
    public void exportExcelKunjungan(@RequestParam("tglAwal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAwal, @RequestParam("tglAkhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAkhir, HttpServletResponse response) throws IOException {

        excelKunjunganAllService.excelLaporanKunjungan(tglAwal, tglAkhir, response);
    }

    @GetMapping("/export/kunjungan/salesman")
    public void exportExcelKunjunganBySelsman(@RequestParam("tglAwal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAwal, @RequestParam("tglAkhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAkhir, @RequestParam("id_selesman") Long id, HttpServletResponse response) throws IOException {

        excelKunjunganAllService.excelLaporanKunjunganBySalesman(tglAwal, tglAkhir, id, response);
    }


    @GetMapping("/by_date/salesman")
    public CommonResponse<List<Kunjungan>> getBySalesmanGroupedByDate(
            @RequestParam(name = "id_salesman") Long idSalesman) {
        return ResponseHelper.ok(kunjunganService.getBySalesmanGroupedByDate(idSalesman));
    }
    @GetMapping("/group/salesman")
    public CommonResponse<List<Object[]>> getBySalesmanGrouped() {
        return ResponseHelper.ok(kunjunganService.getKunjunganGroupedBySalesman());
    }
    @GetMapping("/export/laporan/sync")
    public void exportKunjunganToExcel(
            @RequestParam("tglAwal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAwal,
            @RequestParam("tglAkhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAkhir,
            @RequestParam("id_selesman") Long id,
            HttpServletResponse response) throws IOException {

        // Fetch salesman details from the database
        Salesman salesman = kunjunganService.getSalesmanById(id);
        String salesmanName = salesman.getNamaSalesman();  // Assuming Salesman entity has a `getNama()` method.

        // Fetch kunjungan data from the database
        List<Object[]> kunjunganData = kunjunganService.getKunjunganReport(tglAwal, tglAkhir, id);

        // Call service to export data to Excel
        excelKunjunganAllService.exportExcel(kunjunganData, response, salesmanName, tglAwal.toString(), tglAkhir.toString());
    }

    @GetMapping("/between51and80")
    public List<Kunjungan> getAllKunjunganBetween51And80() {
        return kunjunganService.getKunjunganBetween51And80();
    }

    @GetMapping("/between0and50")
    public List<Kunjungan> getAllKunjunganBetween0And50() {
        return kunjunganService.getKunjunganBetween0And50();
    }

    @GetMapping("/max-visit")
    public CommonResponse<List<Kunjungan>> getMaxVisitBySalesmanAndCustomer(@RequestParam Long idSalesman, @RequestParam Long idCustomer) {
        return ResponseHelper.ok(kunjunganService.getMaxVisitBySalesmanAndCustomer(idSalesman, idCustomer));
    }
    @GetMapping("/export/review")
    public void exportExcelReview(@RequestParam("tglAwal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAwal,
                                  @RequestParam("tglAkhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAkhir, HttpServletResponse response) throws IOException {

        excelReview.generateExcelReport(tglAwal, tglAkhir, response);
    }

    @PostMapping(path = "/add/non_plan", consumes = "multipart/form-data")
    public CommonResponse<Kunjungan> addNonPlan(KunjunganDTO kunjunganDTO, @RequestPart("foto") MultipartFile multipartFile) throws IOException {
        return ResponseHelper.ok(kunjunganService.addNonPlan(kunjunganDTO, multipartFile));
    }
}
