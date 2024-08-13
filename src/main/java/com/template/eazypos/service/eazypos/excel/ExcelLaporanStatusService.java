package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.repository.StatusRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ExcelLaporanStatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private ExcelLaporanStatus excelLaporanStatus;

    public ByteArrayInputStream loadBarang(Date tanggalAwal, Date tanggalAkhir) throws IOException {
        List<Object[]> statuses = statusRepository.laporanStatusService(tanggalAwal, tanggalAkhir);
        return excelLaporanStatus.laporanStatusToExcel(statuses);
    }

    public void excelLaporanStatus(Date tanggalAwal, Date tanggalAkhir, HttpServletResponse response) throws IOException {
        ByteArrayInputStream bais = loadBarang(tanggalAwal, tanggalAkhir);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=LAPORAN_STATUS.xlsx");
        response.getOutputStream().write(bais.readAllBytes());
    }
}
