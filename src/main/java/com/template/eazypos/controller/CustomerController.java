package com.template.eazypos.controller;

import com.template.eazypos.auditing.Pagination;
import com.template.eazypos.dto.CustomerDTO;
import com.template.eazypos.dto.CustomerITCDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.PaginationResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Customer;
import com.template.eazypos.service.eazypos.CustomerService;
import com.template.eazypos.service.eazypos.excel.ExcelCustomerService;
import com.template.eazypos.service.itc.CustomerITCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/customer")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerITCService customerITCService;

    @Autowired
    private ExcelCustomerService excelCustomerService;

    // Menambahkan Customer Baru
    @PostMapping("/add")
    public CommonResponse<Customer> add(@RequestBody CustomerDTO customerDTO) {
        return ResponseHelper.ok(customerService.add(customerDTO));
    }
    @PostMapping("/itc")
    public CommonResponse<Customer> addITC(@RequestBody CustomerITCDTO customerDTO) {
        return ResponseHelper.ok(customerITCService.add(customerDTO));
    }

    // Mendapatkan Customer Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse<Customer> get(@PathVariable("id") Long id) {
        return ResponseHelper.ok(customerService.get(id));
    }

    // Mendapatkan Semua Customer
    @GetMapping
    public CommonResponse<List<Customer>> getAll() {
        return ResponseHelper.ok(customerService.getAll());
    }

    // Mendapatkan Customer Dengan Pagination
    @GetMapping(path = "/pagination")
    public PaginationResponse<List<Customer>> getAll(
            @RequestParam(defaultValue = Pagination.page, required = false) Long page,
            @RequestParam(defaultValue = Pagination.limit, required = false) Long limit,
            @RequestParam(defaultValue = Pagination.sort, required = false) String sort,
            @RequestParam(required = false) String search
    ) {

        Page<Customer> customerPage;
        if (search != null && !search.isEmpty()) {
            customerPage = customerService.getAllWithPagination(page, limit, sort, search);
        } else {
            customerPage = customerService.getAllWithPagination(page, limit, sort, null);
        }

        List<Customer> customers = customerPage.getContent();
        long totalItems = customerPage.getTotalElements();
        int totalPages = customerPage.getTotalPages();

        Map<String, Integer> pagination = new HashMap<>();
        pagination.put("total", (int) totalItems);
        pagination.put("page", Math.toIntExact(page));
        pagination.put("total_page", totalPages);

        return ResponseHelper.okWithPagination(customers, pagination);
    }

    // Mengedit Customer Berdasarkan ID
    @PutMapping("/{id}")
    public CommonResponse<Customer> put(@PathVariable("id") Long id, @RequestBody CustomerDTO customerDTO) {
        return ResponseHelper.ok(customerService.edit(customerDTO, id));
    }

    // Menghapus Customer Berdasarkan ID
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id") Long id) {
        return ResponseHelper.ok(customerService.delete(id));
    }
    @GetMapping("/export/excel")
    public void exportExcelPlanningPerCustomer(
            @RequestParam("tglAwal") @DateTimeFormat(pattern = "yyy-MM-dd") Date tglAwal,
            @RequestParam("tglAkhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAkhir,
            HttpServletResponse response) throws IOException {

        excelCustomerService.excelLaporanCustomer(tglAwal, tglAkhir , response);
    }
    @GetMapping("/export/customer/google")
    public void exportExcelCustomerGoogle(
            HttpServletResponse response) throws IOException {

        excelCustomerService.excelLaporanCustomerGoogle(response);
    }
}
