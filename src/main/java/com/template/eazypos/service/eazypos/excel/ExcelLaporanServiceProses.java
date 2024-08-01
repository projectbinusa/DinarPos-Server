package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.*;
import com.template.eazypos.repository.LaporanServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ExcelLaporanServiceProses {

    @Autowired
    private LaporanServiceRepository laporanServiceRepository;

    @Autowired
    private ExcelLaporanServiceInProses excelLaporanServiceInProses;

    public ByteArrayInputStream loadBarang() throws IOException {
        List<ServiceBarang> serviceBarangs = laporanServiceRepository.findAllServiceWithStatusProses();
        return excelLaporanServiceInProses.laporanServiceInProsesToExcel(serviceBarangs);
    }

    public void excelLaporanServiceInProses(Date tanggalAwal, Date tanggalAkhir, HttpServletResponse response) throws IOException {
        ByteArrayInputStream bais = loadBarang();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=LAPORAN SERVICE PROSES.xlsx");
        response.getOutputStream().write(bais.readAllBytes());
    }
}
