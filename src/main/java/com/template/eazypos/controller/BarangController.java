package com.template.eazypos.controller;


import com.template.eazypos.auditing.Pagination;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.PaginationResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.exception.ResponseMessage;
import com.template.eazypos.model.Barang;
import com.template.eazypos.service.eazypos.BarangService;
import com.template.eazypos.service.eazypos.excel.ExcelBarang;
import com.template.eazypos.service.eazypos.excel.ExcelBarangService;
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
@RequestMapping("api/barang")
@CrossOrigin(origins = "*")
public class BarangController {

    @Autowired
    private BarangService barangService;

    @Autowired
    private ExcelBarangService excelBarang;

    @Autowired
    private ExcelBarang excelBarangService;

    // Menambahkan Barang Baru
    @PostMapping("/add")
    public CommonResponse<Barang> add(@RequestBody Barang barang){
        return ResponseHelper.ok( barangService.add(barang));
    }

    // Mendapatkan Barang Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse <Barang> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( barangService.get(id));
    }

    // Mendapatkan Barang Berdasarkan Barcode
    @GetMapping("/barcode")
    public CommonResponse <Barang> getByBarcode(@RequestParam("barcode") String  barcode){
        return ResponseHelper.ok( barangService.getByBarcode(barcode));
    }

    // Mendapatkan Semua Barang
    @GetMapping
    public CommonResponse<List<Barang>> getAll(){
        return ResponseHelper.ok( barangService.getAll());
    }

    // Mendapatkan Barang Dengan Pagination
    @GetMapping(path = "/pagination")
    public PaginationResponse<List<Barang>> getAll(
            @RequestParam(defaultValue = Pagination.page, required = false) Long page,
            @RequestParam(defaultValue = Pagination.limit, required = false) Long limit,
            @RequestParam(defaultValue = Pagination.sort, required = false) String sort,
            @RequestParam(required = false) String search
    ) {

        Page<Barang> barangPage;

        if (search != null && !search.isEmpty()) {
            barangPage = barangService.getAllWithPagination(page, limit, sort, search);
        } else {
            barangPage = barangService.getAllWithPagination(page, limit, sort, null);
        }

        List<Barang> barangs = barangPage.getContent();
        long totalItems = barangPage.getTotalElements();
        int totalPages = barangPage.getTotalPages();

        Map<String, Integer> pagination = new HashMap<>();
        pagination.put("total", (int) totalItems);
        pagination.put("page", Math.toIntExact(page));
        pagination.put("total_page", totalPages);

        return ResponseHelper.okWithPagination(barangs, pagination);
    }

    // Mengedit Barang Berdasarkan ID
    @PutMapping("/{id}")
    public CommonResponse<Barang> put(@PathVariable("id") Long id , @RequestBody Barang barang){
        return ResponseHelper.ok( barangService.edit(barang , id));
    }

    // Menghapus Barang Berdasarkan ID
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( barangService.delete(id));
    }

    // Mengimpor Data Barang Dari File Excel
    @PostMapping(path = "/import")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestPart("file") MultipartFile file) {
        String message = "";
        if (ExcelBarang.hasExcelFormat(file)) {
            try {
                excelBarangService.saveBarang(file);
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

    // Mengekspor Data Barang Ke File Excel
    @GetMapping("/export")
    public ResponseEntity<Resource> exportBarangsToExcel() throws IOException {
        String filename = "Data Barang.xlsx";
        InputStreamResource file = new InputStreamResource(excelBarang.loadBarang());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);

    }

    // Mendapatkan Template File Excel Untuk Data Barang
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
