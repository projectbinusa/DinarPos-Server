package com.template.eazypos.controller;


import com.template.eazypos.auditing.Pagination;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.PaginationResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.exception.ResponseMessage;
import com.template.eazypos.model.Customer;
import com.template.eazypos.model.Prov;
import com.template.eazypos.service.eazypos.excel.ExcelBarang;
import com.template.eazypos.service.eazypos.excel.ExcelProv;
import com.template.eazypos.service.itc.ProvService;
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
@RequestMapping("api/prov")
@CrossOrigin(origins = "*")
public class ProvController {
    @Autowired
    private ProvService provService;

    @Autowired
    private ExcelProv excelProv;

    @PostMapping("/add")
    public CommonResponse<Prov> addProv(@RequestBody Prov user) {
        return ResponseHelper.ok(provService.add(user));
    }

    // Endpoint Untuk Mendapatkan Detail Prov Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse<Prov> get(@PathVariable("id") Long id) {
        return ResponseHelper.ok(provService.getById(id));
    }

    // Endpoint Untuk Mendapatkan Semua Prov
    @GetMapping("/all")
    public CommonResponse<List<Prov>> getAll() {
        return ResponseHelper.ok(provService.getAll());
    }

    @GetMapping(path = "/pagination")
    public PaginationResponse<List<Prov>> getAll(
            @RequestParam(defaultValue = Pagination.page, required = false) Long page,
            @RequestParam(defaultValue = Pagination.limit, required = false) Long limit,
            @RequestParam(defaultValue = Pagination.sort, required = false) String sort,
            @RequestParam(required = false) String search
    ) {

        Page<Prov> customerPage;
            customerPage = provService.getAllWithPagination(page, limit, sort);

        List<Prov> customers = customerPage.getContent();
        long totalItems = customerPage.getTotalElements();
        int totalPages = customerPage.getTotalPages();

        Map<String, Integer> pagination = new HashMap<>();
        pagination.put("total", (int) totalItems);
        pagination.put("page", Math.toIntExact(page));
        pagination.put("total_page", totalPages);

        return ResponseHelper.okWithPagination(customers, pagination);
    }

    // Endpoint Untuk Mengedit Prov Berdasarkan ID
    @PutMapping("/{id}")
    public CommonResponse<Prov> put(@PathVariable("id") Long id, @RequestBody Prov user) {
        return ResponseHelper.ok(provService.edit(user, id));
    }

    // Endpoint Untuk Menghapus Prov Berdasarkan ID
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id") Long id) {
        return ResponseHelper.ok(provService.delete(id));
    }

    @PostMapping(path = "/import")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestPart("file") MultipartFile file) {
        String message = "";
        if (ExcelProv.hasExcelFormat(file)) {
            try {
                excelProv.saveBarang(file);
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
    @GetMapping("/template")
    public ResponseEntity<Resource> templateProvToExcel() throws IOException {
        String filename = "Template Data Provinsi.xlsx";
        InputStreamResource file = new InputStreamResource(excelProv.templateProv());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);

    }
}
