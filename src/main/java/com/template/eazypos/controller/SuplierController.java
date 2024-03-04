package com.template.eazypos.controller;

import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.InternalErrorException;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.exception.ResponseMessage;
import com.template.eazypos.model.Suplier;
import com.template.eazypos.service.eazypos.SupplierService;
import com.template.eazypos.service.eazypos.excel.ExcelBarang;
import com.template.eazypos.service.eazypos.excel.ExcelSuplier;
import com.template.eazypos.service.eazypos.excel.ExcelSuplierService;
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
@RequestMapping("api/supplier")
@CrossOrigin(origins = "http://localhost:3000")
public class SuplierController {
    @Autowired
    private SupplierService service;

    @Autowired
    private ExcelSuplierService excelSuplier;

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

    @PostMapping(path = "/import")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestPart("file") MultipartFile file) {
        String message = "";
        if (ExcelBarang.hasExcelFormat(file)) {
            try {
                excelSuplier.saveSuplier(file);
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
        String filename = "Data Suplier.xlsx";
        InputStreamResource file = new InputStreamResource(excelSuplier.loadSuplier());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);

    }
    @GetMapping("/template")
    public ResponseEntity<Resource> templateBarangsToExcel() throws IOException {
        String filename = "Template Data Suplier.xlsx";
        InputStreamResource file = new InputStreamResource(excelSuplier.templateSuplier());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);

    }
}
