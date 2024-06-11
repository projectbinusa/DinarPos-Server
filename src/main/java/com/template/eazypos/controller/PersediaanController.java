package com.template.eazypos.controller;

import com.template.eazypos.service.eazypos.excel.ExcelPersediaanService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/persediaan")
@CrossOrigin(origins = "*")
public class PersediaanController {
    @Autowired
    private ExcelPersediaanService excelPersediaan;

    @GetMapping("/export")
    public ResponseEntity<Resource> exportPersediaanToExcel() throws IOException {
        String filename = "Data_Persediaan.xlsx";
        InputStreamResource file = new InputStreamResource(excelPersediaan.loadPersediaan());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }
}
