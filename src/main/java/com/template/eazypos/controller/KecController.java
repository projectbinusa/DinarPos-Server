package com.template.eazypos.controller;

import com.template.eazypos.auditing.Pagination;
import com.template.eazypos.dto.KecDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.PaginationResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.exception.ResponseMessage;
import com.template.eazypos.model.Kec;
import com.template.eazypos.service.eazypos.excel.ExcelKabKot;
import com.template.eazypos.service.eazypos.excel.ExcelKec;
import com.template.eazypos.service.itc.KecService;
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
@RequestMapping("api/kec")
@CrossOrigin(origins = "*")
public class KecController {
    @Autowired
    private KecService kecService;

    @Autowired
    private ExcelKec excelKec;

    @PostMapping("/add")
    public CommonResponse<Kec> addKec(@RequestBody KecDTO user) {
        return ResponseHelper.ok(kecService.add(user));
    }

    // Endpoint Untuk Mendapatkan Detail Kec Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse<Kec> get(@PathVariable("id") Long id) {
        return ResponseHelper.ok(kecService.getById(id));
    }

    // Endpoint Untuk Mendapatkan Semua Kec
    @GetMapping
    public CommonResponse<List<Kec>> getAll() {
        return ResponseHelper.ok(kecService.getAll());
    }

    @GetMapping(path = "/pagination")
    public PaginationResponse<List<Kec>> getAll(
            @RequestParam(defaultValue = Pagination.page, required = false) Long page,
            @RequestParam(defaultValue = Pagination.limit, required = false) Long limit,
            @RequestParam(defaultValue = Pagination.sort, required = false) String sort,
            @RequestParam(name = "id_kabkot") Long id
    ) {

        Page<Kec> customerPage;
            customerPage = kecService.getAllWithPagination(page, limit, sort, id);


        List<Kec> customers = customerPage.getContent();
        long totalItems = customerPage.getTotalElements();
        int totalPages = customerPage.getTotalPages();

        Map<String, Integer> pagination = new HashMap<>();
        pagination.put("total", (int) totalItems);
        pagination.put("page", Math.toIntExact(page));
        pagination.put("total_page", totalPages);

        return ResponseHelper.okWithPagination(customers, pagination);
    }

    // Endpoint Untuk Mengedit Kec Berdasarkan ID
    @PutMapping("/{id}")
    public CommonResponse<Kec> put(@PathVariable("id") Long id, @RequestBody KecDTO user) {
        return ResponseHelper.ok(kecService.edit(user, id));
    }

    // Endpoint Untuk Menghapus Kec Berdasarkan ID
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id") Long id) {
        return ResponseHelper.ok(kecService.delete(id));
    }

    @PostMapping(path = "/import")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestPart("file") MultipartFile file) {
        String message = "";
        if (ExcelKec.hasExcelFormat(file)) {
            try {
                excelKec.saveBarang(file);
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
    public ResponseEntity<Resource> templateKecToExcel() throws IOException {
        String filename = "Template Data Kecamatan.xlsx";
        InputStreamResource file = new InputStreamResource(excelKec.templateKec());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);

    }
}
