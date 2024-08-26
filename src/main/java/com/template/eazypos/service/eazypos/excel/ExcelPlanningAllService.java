package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Planning;
import com.template.eazypos.repository.PlanningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ExcelPlanningAllService {

    @Autowired
    PlanningRepository planningRepository;

    @Autowired
    private ExcelPlanningAll excelPlanningAll;

    public ByteArrayInputStream loadPlanning() throws IOException {
        List<Planning> plannings = planningRepository.findAll();
        return excelPlanningAll.laporanPlanningToExcel(plannings);
    }

    public void excelLaporanPlanning(Date tglAwal, Date tglAkhir, HttpServletResponse response) throws IOException {
        ByteArrayInputStream bais = loadPlanning();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=LAPORAN PLANNING ALL.xlsx");
        response.getOutputStream().write(bais.readAllBytes());
    }
}
