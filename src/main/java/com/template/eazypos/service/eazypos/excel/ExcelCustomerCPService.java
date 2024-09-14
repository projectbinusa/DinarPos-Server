package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Customer;
import com.template.eazypos.model.CustomerCP;
import com.template.eazypos.repository.CustomerCPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ExcelCustomerCPService {
    @Autowired
    private CustomerCPRepository customerRepository;

    public ByteArrayInputStream loadCustomer(Date tglAwal , Date tglAkhir) throws IOException {
        List<CustomerCP> customers = customerRepository.findCustomerCPBetweenDates(tglAwal , tglAkhir);
        return ExcelCustomerCP.customerToExcel(customers);
    }

    public void excelLaporanCustomer(Date tglAwal, Date tglAkhir, HttpServletResponse response) throws IOException {
        ByteArrayInputStream bais = loadCustomer(tglAwal ,tglAkhir);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=REPORT CUSTOMER CP.xlsx");
        response.getOutputStream().write(bais.readAllBytes());
    }
}
