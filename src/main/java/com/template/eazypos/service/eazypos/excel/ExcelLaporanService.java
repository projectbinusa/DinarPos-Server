package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.repository.LaporanServiceRepository;
import com.template.eazypos.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.template.eazypos.model.ServiceBarang;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ExcelLaporanService {

    @Autowired
    private LaporanServiceRepository laporanServiceRepository;

    @Autowired
    private ExcelLaporanServiceAll excelLaporanServiceAll;

    public ByteArrayInputStream loadBarang() throws IOException {
        List<ServiceBarang> serviceBarangs = laporanServiceRepository.findAllService();
        return excelLaporanServiceAll.laporanServiceToExcel(serviceBarangs);
    }

    public ByteArrayInputStream templateLaporanService() throws IOException {
        List<ServiceBarang> serviceBarangs = laporanServiceRepository.findAllService();
        return excelLaporanServiceAll.laporanServiceToExcel(serviceBarangs);
    }

    public void saveLaporanService(MultipartFile file) {
        try {
            List<ServiceBarang> serviceBarangList = ExcelLaporanServiceAll.excelToLaporanService(file.getInputStream());
            laporanServiceRepository.saveAll(serviceBarangList);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public void excelLaporanService(Date tanggalAwal, Date tanggalAkhir, HttpServletResponse response) throws IOException {
        ByteArrayInputStream bais = loadBarang();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=LAPORAN SERVICE ALL.xlsx");
        response.getOutputStream().write(bais.readAllBytes());
    }
}