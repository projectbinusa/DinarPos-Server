package com.template.eazypos.controller;

import com.template.eazypos.dto.CustomerDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.InternalErrorException;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Barang;
import com.template.eazypos.model.Customer;
import com.template.eazypos.service.eazypos.BarangService;
import com.template.eazypos.service.eazypos.excel.ExcelBarang;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ExcelBarang excelBarang;

    @PostMapping("/add")
    public CommonResponse<Barang> add(@RequestBody Barang barang){
        return ResponseHelper.ok( barangService.add(barang));
    }
    @GetMapping("/{id}")
    public CommonResponse <Barang> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( barangService.get(id));
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
    @PostMapping("/import")
    public CommonResponse<String> importBarangsFromExcel(@RequestParam("file") MultipartFile file) {
        try {
            excelBarang.importBarangFromExcel(file);
            return ResponseHelper.ok("Barangs imported successfully");
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalErrorException("Error" + e.getMessage());
        }
    }
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportBarangsToExcel() {
        try {
            byte[] excelData = excelBarang.exportToExcel();
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=Data Barang.xlsx")
                    .body(excelData);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalErrorException("Error " + e.getMessage());
        }
    }
    @GetMapping("/template")
    public ResponseEntity<byte[]> templateBarangsToExcel() {
        try {
            byte[] excelData = excelBarang.templateToExcel();
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=Template Data Barang.xlsx")
                    .body(excelData);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalErrorException("Error " + e.getMessage());
        }
    }
}
