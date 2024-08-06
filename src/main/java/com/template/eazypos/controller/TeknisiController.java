package com.template.eazypos.controller;

import com.template.eazypos.auditing.Pagination;
import com.template.eazypos.dto.TeknisiDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.PaginationResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Teknisi;
import com.template.eazypos.service.itc.TeknisiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/teknisi")
@CrossOrigin(origins = "*")
public class TeknisiController {
    @Autowired
    private TeknisiService teknisiService;

    // Endpoint untuk menambahkan data teknisi baru
    @PostMapping("/add")
    public CommonResponse<Teknisi> add(@RequestBody TeknisiDTO teknisiDTO){
        return ResponseHelper.ok( teknisiService.add(teknisiDTO));
    }

    // Endpoint untuk mendapatkan data teknisi berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse <Teknisi> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( teknisiService.getById(id));
    }

    // Endpoint untuk mendapatkan data teknisi berdasarkan username
    @GetMapping("/username")
    public CommonResponse <Teknisi> getByUsername(@RequestParam("username") String username){
        return ResponseHelper.ok( teknisiService.getByNama(username));
    }

    // Endpoint untuk mendapatkan semua data teknisi
    @GetMapping
    public CommonResponse<List<Teknisi>> getAll(){
        return ResponseHelper.ok( teknisiService.getAll());
    }

    // Endpoint untuk mengedit data teknisi berdasarkan ID
    @PutMapping("/{id}")
    public CommonResponse<Teknisi> put(@PathVariable("id") Long id , @RequestBody TeknisiDTO teknisiDTO){
        return ResponseHelper.ok( teknisiService.put(teknisiDTO , id));
    }

    // Endpoint untuk menghapus data teknisi berdasarkan ID
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id") Long id) {
        return ResponseHelper.ok(teknisiService.deleteTeknisi(id));
    }

    // Endpoint untuk mendapatkan data teknisi dengan pagination
    @GetMapping(path= "/pagination")
    public PaginationResponse<List<Teknisi>> getAll(
            @RequestParam(defaultValue = Pagination.page, required = false) Long page,
            @RequestParam(defaultValue = Pagination.limit, required = false) Long limit,
            @RequestParam(defaultValue = Pagination.sort, required = false) String sort,
            @RequestParam(required = false) String search
    ) {
        Page<Teknisi> teknisiPage;

        if (search != null && !search.isEmpty()) {
            teknisiPage = teknisiService.getAllWithPagination(page, limit, sort, search);
        } else {
            teknisiPage = teknisiService.getAllWithPagination( page, limit, sort, null);
        }

        List<Teknisi> teknisis = teknisiPage.getContent();
        long totalItems = teknisiPage.getTotalElements();
        int totalPages = teknisiPage.getTotalPages();

        Map<String, Integer> pagination = new HashMap<>();
        pagination.put("total", (int) totalItems);
        pagination.put("page", Math.toIntExact(page));
        pagination.put("total_page", totalPages);

        return ResponseHelper.okWithPagination(teknisis, pagination);
    }
}
