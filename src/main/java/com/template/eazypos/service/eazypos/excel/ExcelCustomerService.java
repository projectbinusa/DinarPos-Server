package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Customer;
import com.template.eazypos.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ExcelCustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public ByteArrayInputStream loadCustomer(Date tglAwal , Date tglAkhir) throws IOException {
        List<Customer> customers = customerRepository.findCustomersByTimestampBetween(tglAwal , tglAkhir);
        return ExcelCustomer.customerToExcel(customers);
    }
    public ByteArrayInputStream loadCustomerGoogle() throws IOException {
        List<Customer> customers = customerRepository.findAllCustomer();
        return ExcelCustomer.customerToExcel(customers);
    }

    public void excelLaporanCustomer(Date tglAwal, Date tglAkhir, HttpServletResponse response) throws IOException {
        ByteArrayInputStream bais = loadCustomer(tglAwal ,tglAkhir);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=REPORT CUSTOMER.xlsx");
        response.getOutputStream().write(bais.readAllBytes());
    }
    public void excelLaporanCustomerGoogle( HttpServletResponse response) throws IOException {
        ByteArrayInputStream bais = loadCustomerGoogle();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=REPORT GOOGLE CONTACT CUSTOMER.xlsx");
        response.getOutputStream().write(bais.readAllBytes());
    }
}
