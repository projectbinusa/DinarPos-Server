package com.template.eazypos.controller;

import com.template.eazypos.auditing.Pagination;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.PaginationResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Barang;
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

    @PostMapping("/add")
    public CommonResponse<Salesman> add(@RequestBody Salesman salesman){
        return ResponseHelper.ok( salesmanService.add(salesman));
    }
    @GetMapping("/{id}")
    public CommonResponse <Salesman> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( salesmanService.get(id));
    }
    @GetMapping
    public CommonResponse<List<Salesman>> getAll(){
        return ResponseHelper.ok( salesmanService.getAll());
    }
    @GetMapping(path = "/pagination")
    public PaginationResponse<List<Salesman>> getAll(
            @RequestParam(defaultValue = Pagination.page, required = false) Long page,
            @RequestParam(defaultValue = Pagination.limit, required = false) Long limit,
            @RequestParam(defaultValue = Pagination.sort, required = false) String sort,
            @RequestParam(required = false) String search
    ) {

        Page<Salesman> salesmanPage;

        if (search != null && !search.isEmpty()) {
            salesmanPage = salesmanService.getAllWithPagination(page, limit, sort, search);
        } else {
            salesmanPage = salesmanService.getAllWithPagination( page, limit, sort, null);
        }

        List<Salesman> salesmans = salesmanPage.getContent();
        long totalItems = salesmanPage.getTotalElements();
        int totalPages = salesmanPage.getTotalPages();

        Map<String, Integer> pagination = new HashMap<>();
        pagination.put("total", (int) totalItems);
        pagination.put("page", Math.toIntExact(page));
        pagination.put("total_page", totalPages);

        return ResponseHelper.okWithPagination(salesmans, pagination);
    }
    @PutMapping("/{id}")
    public CommonResponse<Salesman> put(@PathVariable("id") Long id , @RequestBody Salesman salesman){
        return ResponseHelper.ok( salesmanService.edit(salesman , id));
    }
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( salesmanService.delete(id));
    }
}
