package com.template.eazypos.controller;

import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.InternalErrorException;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Suplier;
import com.template.eazypos.service.eazypos.SupplierService;
import com.template.eazypos.service.eazypos.excel.ExcelSuplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/supplier")
@CrossOrigin(origins = "http://localhost:3000")
public class SuplierController {
    @Autowired
    private SupplierService service;

    @Autowired
    private ExcelSuplier  excelSuplier;

    @PostMapping("/add")
    public CommonResponse<Suplier> add(@RequestBody Suplier suplier){
        return ResponseHelper.ok( service.add(suplier));
    }
    @GetMapping("/{id}")
    public CommonResponse <Suplier> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( service.get(id));
    }
    @GetMapping
    public CommonResponse<List<Suplier>> getAll(){
        return ResponseHelper.ok( service.getAll());
    }
    @PutMapping("/{id}")
    public CommonResponse<Suplier> put(@PathVariable("id") Long id , @RequestBody Suplier suplier){
        return ResponseHelper.ok( service.edit(suplier , id));
    }
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( service.delete(id));
    }

    @PostMapping("/import")
    public CommonResponse<String> importSupliersFromExcel(@RequestParam("file") MultipartFile file) {
        try {
            excelSuplier.importSuplierFromExcel(file);
            return ResponseHelper.ok("Supliers imported successfully");
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalErrorException("Error" + e.getMessage());
        }
    }
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportSupliersToExcel() {
        try {
            byte[] excelData = excelSuplier.exportSuplierToExcel();
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=Data Suplier.xlsx")
                    .body(excelData);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalErrorException("Error " + e.getMessage());
        }
    }
    @GetMapping("/template")
    public ResponseEntity<byte[]> templateSupliersToExcel() {
        try {
            byte[] excelData = excelSuplier.templateSuplierToExcel();
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=Template Data Suplier.xlsx")
                    .body(excelData);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalErrorException("Error " + e.getMessage());
        }
    }
}
