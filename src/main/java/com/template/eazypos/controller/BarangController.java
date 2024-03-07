package com.template.eazypos.controller;


import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.exception.ResponseMessage;
import com.template.eazypos.model.Barang;
import com.template.eazypos.service.eazypos.BarangService;
import com.template.eazypos.service.eazypos.excel.ExcelBarang;
import com.template.eazypos.service.eazypos.excel.ExcelBarangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/barang")
@CrossOrigin(origins = "http://localhost:3000")
public class BarangController {
    @Autowired
    private BarangService barangService;

    @Autowired
    private ExcelBarangService excelBarang;

    @PostMapping("/add")
    public CommonResponse<Barang> add(@RequestBody Barang barang){
        return ResponseHelper.ok( barangService.add(barang));
    }
    @GetMapping("/{id}")
    public CommonResponse <Barang> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( barangService.get(id));
    }
    @GetMapping("/{barcode}")
    public CommonResponse <Barang> getByBarcode(@PathVariable("barcode") String  barcode){
        return ResponseHelper.ok( barangService.getByBarcode(barcode));
    }
    @GetMapping
    public CommonResponse<List<Barang>> getAll(){
        return ResponseHelper.ok( barangService.getAll());
    }
    @PutMapping("/{id}")
    public CommonResponse<Barang> put(@PathVariable("id") Long id , @RequestBody Barang barang){
        return ResponseHelper.ok( barangService.edit(barang , id));
    }
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( barangService.delete(id));
    }
    @PostMapping(path = "/import")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestPart("file") MultipartFile file) {
        String message = "";
        if (ExcelBarang.hasExcelFormat(file)) {
            try {
                excelBarang.saveBarang(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));

            } catch (Exception e) {
                System.out.println(e);
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> exportBarangsToExcel() throws IOException {
        String filename = "Data Barang.xlsx";
        InputStreamResource file = new InputStreamResource(excelBarang.loadBarang());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);

    }
    @GetMapping("/template")
    public ResponseEntity<Resource> templateBarangsToExcel() throws IOException {
        String filename = "Template Data Barang.xlsx";
        InputStreamResource file = new InputStreamResource(excelBarang.templateBarang());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);

    }
}
