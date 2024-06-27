package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.KasHarian;
import com.template.eazypos.model.SaldoAwalShift;
import com.template.eazypos.repository.BarangTransaksiRepository;
import com.template.eazypos.repository.KasHarianRepository;
import com.template.eazypos.repository.SaldoAwalShiftRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ExcelKasHarian {
    @Autowired
    private KasHarianRepository kasHarianRepository;

    @Autowired
    private BarangTransaksiRepository barangTransaksiRepository;

    @Autowired
    private SaldoAwalShiftRepository saldoAwalShiftRepository;

    public void generateKasHarianExcel(HttpServletResponse response, Date startDate, Date endDate) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Kas Harian");

        // Create cell styles
        CellStyle styleHeader = createHeaderStyle(workbook);
        CellStyle styleData = createDataStyle(workbook);

        // Generate header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {
                "Date", "Invoice Number", "Customers", "Product / Service", "Quantity",
                "Sales", "Payment", "Receivable", "Sales Return", "Bank"
        };
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(styleHeader);
        }

        // Fetch data from repositories
        List<KasHarian> kasHarianList = kasHarianRepository.findByTimestampBetween(startDate, endDate);
        List<SaldoAwalShift> saldoAwalShiftList = saldoAwalShiftRepository.findByDateBetween(startDate, endDate);

        // Populate data rows
        int rowNum = 1;
        for (KasHarian kasHarian : kasHarianList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(kasHarian.getTimestamp().toString()); // Date
            row.createCell(1).setCellValue(kasHarian.getTransaksi().getNoFaktur()); // Invoice Number
            row.createCell(2).setCellValue(kasHarian.getTransaksi().getNamaCustomer()); // Customers
            row.createCell(3).setCellValue(barangTransaksiRepository.findById(kasHarian.getTransaksi().getIdTransaksi()).get().getNamaBarang()); // Product / Service
            row.createCell(4).setCellValue(barangTransaksiRepository.findById(kasHarian.getTransaksi().getIdTransaksi()).get().getQty()); // Quantity
            row.createCell(5).setCellValue(kasHarian.getPenjualan()); // Sales
            row.createCell(6).setCellValue(kasHarian.getPelunasan()); // Payment
            row.createCell(7).setCellValue(kasHarian.getPiutang()); // Receivable
            row.createCell(8).setCellValue(kasHarian.getReturn_penjualan()); // Sales Return
            row.createCell(9).setCellValue(kasHarian.getBank()); // Bank
            for (SaldoAwalShift saldoAwalShift : saldoAwalShiftList) {
                Row row1 = sheet.createRow(rowNum++);
                row1.createCell(1).setCellValue(saldoAwalShift.getShift());
                row1.createCell(2).setCellValue(saldoAwalShift.getSaldoAwal());
                row1.createCell(3).setCellValue(saldoAwalShift.getSetorKas());
                row1.createCell(4).setCellValue(saldoAwalShift.getDate().toString());
                row1.createCell(5).setCellValue(saldoAwalShift.getId().toString());
            }
        }

        // Set column widths (adjust as needed)
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Set response headers
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = "KasHarian.xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        // Write workbook to response output stream
        workbook.write(response.getOutputStream());

        // Close workbook
        workbook.close();
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return style;
    }
}
