package com.template.eazypos.controller;

import com.template.eazypos.dto.OmzetDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Kunjungan;
import com.template.eazypos.model.Omzet;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.service.eazypos.excel.ExcelOmzet;
import com.template.eazypos.service.itc.OmzetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/omzet")
@CrossOrigin(origins = "*")
public class OmzetController {
    @Autowired
    private OmzetService omzetService;

    @Autowired
    ExcelOmzet excelOmzet;

    @PostMapping("/add")
    public CommonResponse<Omzet> add(@RequestBody OmzetDTO omzetDTO){
        return ResponseHelper.ok( omzetService.add(omzetDTO));
    }

    // Mendapatkan Bon Barang Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse <Omzet> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( omzetService.getById(id));
    }

    // Mendapatkan Semua Bon Barang
    @GetMapping
    public CommonResponse<List<Omzet>> getAll(){
        return ResponseHelper.ok( omzetService.getAll());
    }

    @GetMapping("/export/pimpinan")
    public void exportExcelOmzet(
            @RequestParam("tglAwal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAwal,
            @RequestParam("tglAkhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAkhir,
            @RequestParam("id_salesman") Long id,
            HttpServletResponse response) throws IOException {

        excelOmzet.excelLaporanOmzet(tglAwal, tglAkhir , id, response);
    }

    // Menghapus Bon Barang Berdasarkan ID
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( omzetService.delete(id));
    }
    @GetMapping("/bulan_tahun")
    public CommonResponse<List<Double>> getByBulanTahun(@RequestParam(name = "bulan") int bulan ,@RequestParam(name = "tahun") int tahun){
        return ResponseHelper.ok(omzetService.getByBulanTahun(bulan, tahun));
    }

    @GetMapping("/bulan_tahun/salesman")
    public CommonResponse<List<Double>> getByBulanTahunSalesman(@RequestParam(name = "bulan") int bulan, @RequestParam(name = "tahun") int tahun, @RequestParam(name = "id_salesman") Long id_salesman) {
        return ResponseHelper.ok(omzetService.getByBulanTahunSalesman(bulan, tahun, id_salesman));
    }
}
