package com.template.eazypos.controller;

import com.template.eazypos.dto.PlanningDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Planning;
import com.template.eazypos.service.eazypos.excel.ExcelPlanningAllService;
import com.template.eazypos.service.itc.PlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/planning")
@CrossOrigin(origins = "*")
public class PlanningController {
    @Autowired
    private PlanningService planningService;

    @Autowired
    private ExcelPlanningAllService excelPlanningAllService;

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
    @PostMapping("/add")
    public CommonResponse<Planning> add(@RequestBody PlanningDTO planningDTO){
        return ResponseHelper.ok( planningService.add(planningDTO));
    }

    // Mendapatkan Bon Barang Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse <Planning> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( planningService.getById(id));
    }



    // Mengedit Bon Barang Berdasarkan ID
    @PutMapping("/{id}")
    public CommonResponse<Planning> put(@PathVariable("id") Long id , @RequestBody PlanningDTO planningDTO){
        return ResponseHelper.ok( planningService.edit( id , planningDTO));
    }

    // Menghapus Bon Barang Berdasarkan ID
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( planningService.delete(id));
    }
    @GetMapping("/marketting/date")
    public CommonResponse<List<Planning>> getByDate(
            @RequestParam(name = "tanggal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggal) {
        return ResponseHelper.ok(planningService.getByTgl(tanggal));
    }
    @GetMapping("/salesman/date")
    public CommonResponse<List<Planning>> getByDateAndSalesman(
            @RequestParam(name = "tanggal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggal , @RequestParam(name = "id_salesman") Long id) {
        return ResponseHelper.ok(planningService.getByTglAndSalesman(tanggal , id));
    }
    @GetMapping("/notinkunjungan")
    public CommonResponse<List<Planning>> getPlaning(
            @RequestParam(name = "tanggal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggal , @RequestParam(name = "id_salesman") Long id) {
        return ResponseHelper.ok(planningService.getPlanning(tanggal , id));
    }

    @GetMapping("/export/excel/planningAll")
    public void exportExcelPlanningAll(
            @RequestParam("tglAwal") @DateTimeFormat(pattern = "yyy-MM-dd") Date tglAwal,
            @RequestParam("tglAkhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAkhir,
            HttpServletResponse response) throws IOException {

        excelPlanningAllService.excelLaporanPlanning(tglAwal, tglAkhir, response);
    }
    @GetMapping("/export/excel/salesman")
    public void exportExcelPlanningPerSalesman(
            @RequestParam("tglAwal") @DateTimeFormat(pattern = "yyy-MM-dd") Date tglAwal,
            @RequestParam("tglAkhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAkhir,
            @RequestParam("id_salesman") Long id,
            HttpServletResponse response) throws IOException {

        excelPlanningAllService.excelLaporanPlanningPerSalesman(tglAwal, tglAkhir , id, response);
    }
}
