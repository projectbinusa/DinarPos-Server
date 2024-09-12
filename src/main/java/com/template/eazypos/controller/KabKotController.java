package com.template.eazypos.controller;

import com.template.eazypos.auditing.Pagination;
import com.template.eazypos.dto.KabKotDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.PaginationResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.exception.ResponseMessage;
import com.template.eazypos.model.KabKot;
import com.template.eazypos.service.eazypos.excel.ExcelKabKot;
import com.template.eazypos.service.eazypos.excel.ExcelProv;
import com.template.eazypos.service.itc.KabKotService;
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
@RequestMapping("api/kab_kot")
@CrossOrigin(origins = "*")
public class KabKotController {
    @Autowired
    private KabKotService kabKotService;

    @Autowired
    private ExcelKabKot excelKabKot;

    @PostMapping("/add")
    public CommonResponse<KabKot> addKabKot(@RequestBody KabKotDTO user) {
        return ResponseHelper.ok(kabKotService.add(user));
    }

    // Endpoint Untuk Mendapatkan Detail KabKot Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse<KabKot> get(@PathVariable("id") Long id) {
        return ResponseHelper.ok(kabKotService.getById(id));
    }

    // Endpoint Untuk Mendapatkan Semua KabKot
    @GetMapping
    public CommonResponse<List<KabKot>> getAll() {
        return ResponseHelper.ok(kabKotService.getAll());
    }
    @GetMapping(path = "/pagination")
    public PaginationResponse<List<KabKot>> getAll(
            @RequestParam(defaultValue = Pagination.page, required = false) Long page,
            @RequestParam(defaultValue = Pagination.limit, required = false) Long limit,
            @RequestParam(defaultValue = Pagination.sort, required = false) String sort,
            @RequestParam(name = "id_prov") Long id
    ) {

        Page<KabKot> customerPage;
            customerPage = kabKotService.getAllWithPagination(page, limit, sort, id);

        List<KabKot> customers = customerPage.getContent();
        long totalItems = customerPage.getTotalElements();
        int totalPages = customerPage.getTotalPages();

        Map<String, Integer> pagination = new HashMap<>();
        pagination.put("total", (int) totalItems);
        pagination.put("page", Math.toIntExact(page));
        pagination.put("total_page", totalPages);

        return ResponseHelper.okWithPagination(customers, pagination);
    }

    // Endpoint Untuk Mengedit KabKot Berdasarkan ID
    @PutMapping("/{id}")
    public CommonResponse<KabKot> put(@PathVariable("id") Long id, @RequestBody KabKotDTO user) {
        return ResponseHelper.ok(kabKotService.edit(user, id));
    }

    // Endpoint Untuk Menghapus KabKot Berdasarkan ID
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id") Long id) {
        return ResponseHelper.ok(kabKotService.delete(id));
    }

    @PostMapping(path = "/import")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestPart("file") MultipartFile file) {
        String message = "";
        if (ExcelKabKot.hasExcelFormat(file)) {
            try {
                excelKabKot.saveBarang(file);
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
        String filename = "Template Data Kabupaten.xlsx";
        InputStreamResource file = new InputStreamResource(excelKabKot.templateKabKot());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);

    }
}
