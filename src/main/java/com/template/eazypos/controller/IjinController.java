package com.template.eazypos.controller;

import com.template.eazypos.dto.IjinDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Ijin;
import com.template.eazypos.model.Kunjungan;
import com.template.eazypos.service.eazypos.excel.IjinExcelService;
import com.template.eazypos.service.itc.IjinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/ijin")
@CrossOrigin(origins = "*")
public class IjinController {
    @Autowired
    IjinService ijinService;

    @Autowired
    IjinExcelService ijinExcelService;

    @GetMapping
    public CommonResponse<List<Ijin>> getAll() {
        return ResponseHelper.ok(ijinService.getAll());
    }
    @GetMapping("/{id}")
    public CommonResponse <Ijin> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( ijinService.getById(id));
    }
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( ijinService.delete(id));
    }
    @PostMapping(path = "/add" , consumes = "multipart/form-data")
    public CommonResponse<Ijin> add(IjinDTO ijinDTO , @RequestPart("foto") MultipartFile multipartFile) throws IOException {
        return ResponseHelper.ok( ijinService.add(ijinDTO, multipartFile));
    }
    @PutMapping(path = "/{id}" , consumes = "multipart/form-data")
    public CommonResponse<Ijin> edit(IjinDTO ijinDTO , @PathVariable("id") Long id , @RequestPart("foto")MultipartFile multipartFile) throws IOException {
        return ResponseHelper.ok( ijinService.edit(ijinDTO, id, multipartFile));
    }
    @GetMapping("/export")
    public void exportExcelIjin(
            @RequestParam("tglAwal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAwal,
            @RequestParam("tglAkhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAkhir,
            HttpServletResponse response) throws IOException {

        ijinExcelService.excelLaporanIjin(tglAwal, tglAkhir, response);
    }
    @GetMapping("/tanggal_beetwen/salesman")
    public CommonResponse<List<Ijin>> getIjinBetweenTgl(
            @RequestParam("tgl_awal") Date tgl1,
            @RequestParam("tgl_akhir") Date tgl2,
            @RequestParam("id_salesman") Long idm) {
        return ResponseHelper.ok( ijinService.getIjinBetweenTanggal(idm,tgl1, tgl2));
    }
}
