package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Kunjungan;
import com.template.eazypos.repository.KunjunganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ExcelKunjunganAllService {

    @Autowired
    private KunjunganRepository kunjunganRepository;

    @Autowired
    private ExcelKunjunganAll excelKunjunganAll;

    public ByteArrayInputStream loadKunjungan() throws IOException {
        List<Kunjungan> kunjungans = kunjunganRepository.findAll();
        return excelKunjunganAll.laporanKunjunganToExcel(kunjungans);
    }

    public void excelLaporanKunjungan(Date tglAwal, Date tglAkhir, HttpServletResponse response) throws IOException {
        ByteArrayInputStream bais = loadKunjungan();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=LAPORAN KUNJUNGAN ALL.xlsx");
        response.getOutputStream().write(bais.readAllBytes());
    }
}
