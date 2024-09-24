package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Customer;
import com.template.eazypos.model.Kunjungan;
import com.template.eazypos.repository.KunjunganRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
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

    public ByteArrayInputStream loadKunjunganBySelesman(Date tglAwal , Date tglAkhir , Long id) throws IOException {
        List<Kunjungan> kunjungans = kunjunganRepository.findByDateAndSalesman(tglAwal, tglAkhir, id);
        return excelKunjunganAll.laporanKunjunganToExcel(kunjungans);
    }
    public void excelLaporanKunjungan(Date tglAwal, Date tglAkhir, HttpServletResponse response) throws IOException {
        ByteArrayInputStream bais = loadKunjungan();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=LAPORAN KUNJUNGAN ALL.xlsx");
        response.getOutputStream().write(bais.readAllBytes());
    }

    public void excelLaporanKunjunganBySalesman(Date tglAwal, Date tglAkhir , Long id, HttpServletResponse response) throws IOException {
        ByteArrayInputStream bais = loadKunjunganBySelesman(tglAwal, tglAkhir, id);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=LAPORAN KUNJUNGAN.xlsx");
        response.getOutputStream().write(bais.readAllBytes());
    }

    public void exportExcel(List<Object[]> data, HttpServletResponse response, String salesmanName, String startDate, String endDate) throws IOException {
        // Set content type and attachment header for Excel download
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Kunjungan_Report.xlsx");

        // Create a workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Kunjungan Report");

        // Set the structure of the Excel sheet
        setExcelHeader(sheet, salesmanName, startDate, endDate);

        // Add data rows
        setExcelData(sheet, data);

        // Write workbook to output stream
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void setExcelHeader(Sheet sheet, String salesmanName, String startDate, String endDate) {
        // Create Header Rows (General Info)
        Row row1 = sheet.createRow(0);
        Cell cell1 = row1.createCell(0);
        cell1.setCellValue("EVALUASI PLAN & REPORT");

        Row row2 = sheet.createRow(1);
        Cell cell2 = row2.createCell(0);
        cell2.setCellValue("Nama ITC : " + salesmanName);

        Row row3 = sheet.createRow(2);
        Cell cell3 = row3.createCell(0);
        cell3.setCellValue("Periode Tanggal : " + startDate + " s/d " + endDate);

        // Skip a row
        sheet.createRow(3);

        // Create Planning and Report Header
        Row row5 = sheet.createRow(4);
        Cell cell5_1 = row5.createCell(0);
        cell5_1.setCellValue("Planning");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 3));

        Cell cell5_2 = row5.createCell(4);
        cell5_2.setCellValue("Report");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 4, 14));

        // Create table headers
        Row row6 = sheet.createRow(5);
        String[] headers = {
                "No", "Tgl", "Nama Customer", "Tujuan",
                "No", "Timestamp", "Tgl", "Nama Customer", "Tujuan", "Action",
                "Info didapat", "Peluang", "Deal", "Wkt-p", "Tgl Deal"
        };

        for (int i = 0; i < headers.length; i++) {
            Cell cell = row6.createCell(i);
            cell.setCellValue(headers[i]);
        }
    }

    private void setExcelData(Sheet sheet, List<Object[]> data) {
        int rowNum = 6;
        int no = 1;

        // Loop through each row of data
        for (Object[] record : data) {
            Row row = sheet.createRow(rowNum++);
            Kunjungan kunjungan = (Kunjungan) record[0];  // Assume first object is Kunjungan
            Customer customer = (Customer) record[1];    // Assume second object is Customer

            // Add Planning section (left part)
            row.createCell(0).setCellValue(no++);
            row.createCell(1).setCellValue(kunjungan.getPlanning().getTgl());  // Planning Tgl (optional)
            row.createCell(2).setCellValue(customer.getNama_customer());  // Customer Name
            row.createCell(3).setCellValue(kunjungan.getTujuan());  // Tujuan

            // Add Report section (right part)
            row.createCell(4).setCellValue(no);
            row.createCell(5).setCellValue(kunjungan.getTimestamp().toString());  // Timestamp
            row.createCell(6).setCellValue(kunjungan.getTanggalKunjungan().toString());  // Tgl
            row.createCell(7).setCellValue(customer.getNama_customer());  // Customer Name
            row.createCell(8).setCellValue(kunjungan.getTujuan());  // Tujuan
            row.createCell(9).setCellValue(kunjungan.getAction());  // Action
            row.createCell(10).setCellValue(kunjungan.getInfoDpt());  // Info didapat
            row.createCell(11).setCellValue(kunjungan.getPeluang());  // Peluang
            row.createCell(12).setCellValue(kunjungan.getDeal() != null ? kunjungan.getDeal().toString() : "0");  // Deal
            row.createCell(13).setCellValue(kunjungan.getWaktuPengadaan());  // Wkt-p
            row.createCell(14).setCellValue(kunjungan.getTanggalDeal() != null ? kunjungan.getTanggalDeal().toString() : "0000-00-00");  // Tgl Deal
        }
    }
}
