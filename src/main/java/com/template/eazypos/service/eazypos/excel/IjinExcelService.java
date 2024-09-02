package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Ijin;
import com.template.eazypos.repository.IjinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class IjinExcelService {
    @Autowired
    private IjinRepository ijinRepository;



    public ByteArrayInputStream loadIjin(Date tglAwal , Date tglAkhir) throws IOException {
        List<Ijin> ijins = ijinRepository.getByTanggal(tglAwal , tglAkhir);
        return ExcelIjin.ijinToExcel(ijins);
    }

    public void excelLaporanIjin(Date tglAwal, Date tglAkhir, HttpServletResponse response) throws IOException {
        ByteArrayInputStream bais = loadIjin(tglAwal ,tglAkhir);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=REPORT IJIN.xlsx");
        response.getOutputStream().write(bais.readAllBytes());
    }
}
