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

    public ByteArrayInputStream loadPlanning(Date tglAwal , Date tglAKhir) throws IOException {
        List<Planning> plannings = planningRepository.findByTgl(tglAwal , tglAKhir);
        return excelPlanningAll.laporanPlanningToExcel(plannings);
    }
    public ByteArrayInputStream loadPlanningBYSalesman(Date tglAwal , Date tglAKhir , Long id) throws IOException {
        List<Planning> plannings = planningRepository.findByTglAndSalesman(tglAwal , tglAKhir , id);
        return excelPlanningAll.laporanPlanningToExcel(plannings);
    }

    public void excelLaporanPlanning(Date tglAwal, Date tglAkhir, HttpServletResponse response) throws IOException {
        ByteArrayInputStream bais = loadPlanning(tglAwal ,tglAkhir);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=LAPORAN PLANNING ALL.xlsx");
        response.getOutputStream().write(bais.readAllBytes());
    }
    public void excelLaporanPlanningPerSalesman(Date tglAwal, Date tglAkhir , Long id, HttpServletResponse response) throws IOException {
        ByteArrayInputStream bais = loadPlanningBYSalesman(tglAwal ,tglAkhir , id);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=LAPORAN PLANNING ALL.xlsx");
        response.getOutputStream().write(bais.readAllBytes());
    }
}
