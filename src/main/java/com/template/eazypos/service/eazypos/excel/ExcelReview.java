package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Kunjungan;
import com.template.eazypos.model.Omzet;
import com.template.eazypos.model.Planning;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.repository.*;
import com.template.eazypos.service.itc.KunjunganService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ExcelReview {
    @Autowired
    private SalesmanRepository salesmanRepository;

    @Autowired
    private KunjunganRepository kunjunganRepository;

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerCPRepository customerCPRepository;

    @Autowired
    private OmzetRepository omzetRepository;

    @Autowired
    private KunjunganService kunjunganService;

    public void generateExcelReport(Integer bulan , Integer tahun, HttpServletResponse response) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Laporan Review");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("No");
        headerRow.createCell(1).setCellValue("Nama");
        headerRow.createCell(2).setCellValue("Plan");
        headerRow.createCell(3).setCellValue("AVG Plan");
        headerRow.createCell(4).setCellValue("Report");
        headerRow.createCell(5).setCellValue("AVG Report");
        headerRow.createCell(6).setCellValue("Workday");
        headerRow.createCell(7).setCellValue("Report Avg");
        headerRow.createCell(8).setCellValue("Presensi");
        headerRow.createCell(9).setCellValue("Omzet");
        headerRow.createCell(10).setCellValue("Presensi Omzet");
        headerRow.createCell(11).setCellValue("Penambahan Customer");
        headerRow.createCell(12).setCellValue("Penambahan Customer CP");

        // Fetch all salesmen
        List<Salesman> salesmen = salesmanRepository.findAll();

        // Fill data rows
        int rowCount = 1;
        for (Salesman salesman : salesmen) {
            // Menghitung Plan
            List<Planning> plannings = planningRepository.findBySalesmanId(salesman.getId());
            int planCount = plannings.size();
            double avgPlan = plannings.stream().mapToInt(p -> p.getKet().length()).average().orElse(0);

            // Menghitung Report
            List<Kunjungan> reports = kunjunganRepository.findBySalesmanId(salesman.getId());
            int reportCount = reports.size();
            double avgReport = reports.stream().mapToInt(r -> r.getPeluang().length()).average().orElse(0);

            // Workday
            int workday = kunjunganService.calculateWorkdays(salesman);

            // Presensi
            int presensi = kunjunganService.calculatePresensi(salesman, bulan , tahun);

            // Omzet
            double totalOmzet = omzetRepository.findBySalesmanId(salesman.getId())
                    .stream().mapToDouble(Omzet::getOmzet).sum();
            double presensiOmzet = totalOmzet / presensi;

            // Penambahan customer
            int customerAdded = customerRepository.countNewCustomersBySalesmanId(salesman.getId());
            int customerCPAdded = customerCPRepository.countNewCustomerCPBySalesmanId(salesman.getId());

            // Isi data ke dalam row
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(rowCount - 1); // No
            row.createCell(1).setCellValue(salesman.getNamaSalesman()); // Nama
            row.createCell(2).setCellValue(planCount); // Plan
            row.createCell(3).setCellValue(Math.round(avgPlan)); // AVG Plan
            row.createCell(4).setCellValue(reportCount); // Report
            row.createCell(5).setCellValue(Math.round(avgReport)); // AVG Report
            row.createCell(6).setCellValue(workday); // Workday
            row.createCell(7).setCellValue(reportCount / (double) workday); // Report Avg
            row.createCell(8).setCellValue(presensi); // Presensi
            row.createCell(9).setCellValue(totalOmzet); // Omzet
            row.createCell(10).setCellValue(presensiOmzet); // Presensi Omzet
            row.createCell(11).setCellValue(customerAdded); // Penambahan Customer
            row.createCell(12).setCellValue(customerCPAdded); // Penambahan Customer CP
        }

        // Set content type and headers for file download
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=review_report.xlsx");

        // Write workbook to response
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
    }


}
