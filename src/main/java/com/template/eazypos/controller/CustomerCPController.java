package com.template.eazypos.controller;

import com.template.eazypos.auditing.Pagination;
import com.template.eazypos.dto.CustomerCPDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.PaginationResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Customer;
import com.template.eazypos.model.CustomerCP;
import com.template.eazypos.service.eazypos.CustomerCPService;
import com.template.eazypos.service.eazypos.excel.ExcelCustomerCPService;
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
@RequestMapping("api/customer/cp")
@CrossOrigin(origins = "*")
public class CustomerCPController {

    @Autowired
    private CustomerCPService customerCPService;

    @Autowired
    private ExcelCustomerCPService excelCustomerCPService;

    // Menambahkan Customer CP Baru
    @PostMapping("/add")
    public CommonResponse<CustomerCP> add(@RequestBody CustomerCPDTO customerDTO) {
        return ResponseHelper.ok(customerCPService.add(customerDTO));
    }

    // Mendapatkan Customer CP Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse<CustomerCP> get(@PathVariable("id") Long id) {
        return ResponseHelper.ok(customerCPService.get(id));
    }

    // Mendapatkan Semua Customer CP
    @GetMapping
    public CommonResponse<List<CustomerCP>> getAll() {
        return ResponseHelper.ok(customerCPService.getAll());
    }

    // Mengedit Customer CP Berdasarkan ID
    @PutMapping("/{id}")
    public CommonResponse<CustomerCP> put(@PathVariable("id") Long id, @RequestBody CustomerCPDTO customerDTO) {
        return ResponseHelper.ok(customerCPService.edit(customerDTO, id));
    }

    // Menghapus Customer CP Berdasarkan ID
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id") Long id) {
        return ResponseHelper.ok(customerCPService.delete(id));
    }
    @GetMapping(path = "/pagination")
    public PaginationResponse<List<CustomerCP>> getAllPagination(
            @RequestParam(defaultValue = Pagination.page, required = false) Long page,
            @RequestParam(defaultValue = Pagination.limit, required = false) Long limit,
            @RequestParam(defaultValue = Pagination.sort, required = false) String sort,
          @RequestParam("id_customer") Long id
    ) {

        Page<CustomerCP> customerPage;
            customerPage = customerCPService.getAllWithPagination(page, limit, sort, id);

        List<CustomerCP> customers = customerPage.getContent();
        long totalItems = customerPage.getTotalElements();
        int totalPages = customerPage.getTotalPages();

        Map<String, Integer> pagination = new HashMap<>();
        pagination.put("total", (int) totalItems);
        pagination.put("page", Math.toIntExact(page));
        pagination.put("total_page", totalPages);

        return ResponseHelper.okWithPagination(customers, pagination);
    }
    @GetMapping("/export/excel")
    public void exportExcelCustomer(
            @RequestParam("tglAwal") @DateTimeFormat(pattern = "yyy-MM-dd") Date tglAwal,
            @RequestParam("tglAkhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tglAkhir,
            HttpServletResponse response) throws IOException {

        excelCustomerCPService.excelLaporanCustomer(tglAwal, tglAkhir , response);
    }
    @GetMapping("/export/customer_cp/google")
    public void exportExcelCustomerGoogle(
            HttpServletResponse response) throws IOException {

        excelCustomerCPService.excelLaporanCustomerGoogle(response);
    }

    @GetMapping("/by-customer/{idCustomer}")
    public CommonResponse<List<CustomerCP>> getAllByCustomerId(@PathVariable("idCustomer") Long idCustomer) {
        return ResponseHelper.ok(customerCPService.getAllByCustomerId(idCustomer));
    }
}
