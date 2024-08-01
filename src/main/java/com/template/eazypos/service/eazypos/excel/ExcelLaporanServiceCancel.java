package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.ServiceBarang;
import com.template.eazypos.repository.LaporanServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ExcelLaporanServiceCancel {

    @Autowired
    private LaporanServiceRepository laporanServiceRepository;

    @Autowired
    private ExcelLaporanServiceWithCancel excelLaporanServiceWithCancel;

    public ByteArrayInputStream loadBarang() throws IOException {
        List<ServiceBarang> serviceBarangs = laporanServiceRepository.findAllServiceWithStatusCancel();
        return excelLaporanServiceWithCancel.laporanServiceWithCancelToExcel(serviceBarangs);
    }

    public void excelLaporanServiceWithCancel(Date tanggalAwal, Date tanggalAkhir, HttpServletResponse response) throws IOException {
        ByteArrayInputStream bais = loadBarang();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=LAPORAN SERVICE CANCEL.xlsx");
        response.getOutputStream().write(bais.readAllBytes());
    }
}
