package com.template.eazypos.controller;

import com.template.eazypos.auditing.Pagination;
import com.template.eazypos.exception.*;
import com.template.eazypos.model.Barang;
import com.template.eazypos.model.Suplier;
import com.template.eazypos.service.eazypos.SupplierService;
import com.template.eazypos.service.eazypos.excel.ExcelBarang;
import com.template.eazypos.service.eazypos.excel.ExcelSuplier;
import com.template.eazypos.service.eazypos.excel.ExcelSuplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/supplier")
@CrossOrigin(origins = "*")
public class SuplierController {
    @Autowired
    private SupplierService service;

    @Autowired
    private ExcelSuplierService excelSuplier;

    // Endpoint untuk menambahkan data supplier baru
    @PostMapping("/add")
    public CommonResponse<Suplier> add(@RequestBody Suplier suplier){
        return ResponseHelper.ok( service.add(suplier));
    }

    // Endpoint untuk mendapatkan data supplier berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse <Suplier> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( service.get(id));
    }

    // Endpoint untuk mendapatkan semua data supplier
    @GetMapping
    public CommonResponse<List<Suplier>> getAll(){
        return ResponseHelper.ok( service.getAll());
    }

    // Endpoint untuk mendapatkan data supplier dengan pagination
    @GetMapping(path = "/pagination")
    public PaginationResponse<List<Suplier>> getAll(
            @RequestParam(defaultValue = Pagination.page, required = false) Long page,
            @RequestParam(defaultValue = Pagination.limit, required = false) Long limit,
            @RequestParam(defaultValue = Pagination.sort, required = false) String sort,
            @RequestParam(required = false) String search
    ) {

        Page<Suplier> suplierPage;

        if (search != null && !search.isEmpty()) {
            suplierPage = service.getAllWithPagination(page, limit, sort, search);
        } else {
            suplierPage = service.getAllWithPagination( page, limit, sort, null);
        }

        List<Suplier> supliers = suplierPage.getContent();
        long totalItems = suplierPage.getTotalElements();
        int totalPages = suplierPage.getTotalPages();

        Map<String, Integer> pagination = new HashMap<>();
        pagination.put("total", (int) totalItems);
        pagination.put("page", Math.toIntExact(page));
        pagination.put("total_page", totalPages);

        return ResponseHelper.okWithPagination(supliers, pagination);
    }

    // Endpoint untuk mengedit data supplier berdasarkan ID
    @PutMapping("/{id}")
    public CommonResponse<Suplier> put(@PathVariable("id") Long id , @RequestBody Suplier suplier){
        return ResponseHelper.ok( service.edit(suplier , id));
    }

    // Endpoint untuk menghapus data supplier berdasarkan ID
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id") Long id) {
        return ResponseHelper.ok(service.deleteSupplier(id));
    }

    // Endpoint untuk mengunggah file excel data supplier
    @PostMapping(path = "/import")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestPart("file") MultipartFile file) {
        String message = "";
        if (ExcelSuplier.hasExcelFormat(file)) {
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

    // Endpoint untuk mengekspor data supplier ke file excel
    @GetMapping("/export")
    public ResponseEntity<Resource> exportSupliersToExcel() throws IOException {
        String filename = "Data_Suplier.xlsx";
        InputStreamResource file = new InputStreamResource(excelSuplier.loadSuplier());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    // Endpoint untuk mendownload template file excel data supplier
    @GetMapping("/template")
    public ResponseEntity<Resource> templateSupliersToExcel() throws IOException {
        String filename = "Template_Data_Suplier.xlsx";
        InputStreamResource file = new InputStreamResource(excelSuplier.templateSuplier());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
