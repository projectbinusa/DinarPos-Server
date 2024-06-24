package com.template.eazypos.controller;

import com.template.eazypos.auditing.Pagination;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.PaginationResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.service.eazypos.SalesmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/salesman")
@CrossOrigin(origins = "*")
public class SalesmanController {

    @Autowired
    private SalesmanService salesmanService;

    // Endpoint Untuk Menambah Salesman
    @PostMapping("/add")
    public CommonResponse<Salesman> add(@RequestBody Salesman salesman) {
        return ResponseHelper.ok(salesmanService.add(salesman));
    }

    // Endpoint Untuk Mendapatkan Detail Salesman Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse<Salesman> get(@PathVariable("id") Long id) {
        return ResponseHelper.ok(salesmanService.get(id));
    }

    // Endpoint Untuk Mendapatkan Semua Salesman
    @GetMapping
    public CommonResponse<List<Salesman>> getAll() {
        return ResponseHelper.ok(salesmanService.getAll());
    }

    // Endpoint Mendapatkan Salesman Dengan Pagination Dan Pencarian Opsional
    @GetMapping("/pagination")
    public PaginationResponse<List<Salesman>> getAllWithPagination(
            @RequestParam(defaultValue = Pagination.page, required = false) Long page,
            @RequestParam(defaultValue = Pagination.limit, required = false) Long limit,
            @RequestParam(defaultValue = Pagination.sort, required = false) String sort,
            @RequestParam(required = false) String search
    ) {
        Page<Salesman> salesmanPage;
        if (search != null && !search.isEmpty()) {
            salesmanPage = salesmanService.getAllWithPagination(page, limit, sort, search);
        } else {
            salesmanPage = salesmanService.getAllWithPagination(page, limit, sort, null);
        }

        List<Salesman> salesmen = salesmanPage.getContent();
        long totalItems = salesmanPage.getTotalElements();
        int totalPages = salesmanPage.getTotalPages();

        Map<String, Integer> pagination = new HashMap<>();
        pagination.put("total", (int) totalItems);
        pagination.put("page", Math.toIntExact(page));
        pagination.put("total_page", totalPages);

        return ResponseHelper.okWithPagination(salesmen, pagination);
    }

    // Endpoint untuk Mengedit Salesman Berdasarkan ID
    @PutMapping("/{id}")
    public CommonResponse<Salesman> edit(@PathVariable("id") Long id, @RequestBody Salesman salesman) {
        return ResponseHelper.ok(salesmanService.edit(salesman, id));
    }

    // Endpoint Untuk Menghapus Salesman Berdasarkan ID
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id") Long id) {
        return ResponseHelper.ok(salesmanService.delete(id));
    }
}
