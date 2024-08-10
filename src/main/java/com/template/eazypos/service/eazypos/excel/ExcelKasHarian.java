package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.KasHarian;
import com.template.eazypos.model.SaldoAwalShift;
import com.template.eazypos.repository.BarangTransaksiRepository;
import com.template.eazypos.repository.KasHarianRepository;
import com.template.eazypos.repository.SaldoAwalShiftRepository;
import com.template.eazypos.service.eazypos.KasHarianService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ExcelKasHarian {

    @Autowired
    private KasHarianService kasHarianService;

    private static final Logger logger = LoggerFactory.getLogger(ExcelKasHarian.class);

    public void excelKasHarian(Date tglAwal, Date tglAkhir, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Kas Harian");

        // Cell styles
        CellStyle styleHeader = createHeaderStyle(workbook);
        CellStyle styleColor1 = createColor1Style(workbook);
        CellStyle styleColor2 = createColor2Style(workbook);
        CellStyle styleColor3 = createColor3Style(workbook);
        CellStyle styleColumn = createColumnStyle(workbook);
        CellStyle styleColumnNumber = createColumnNumberStyle(workbook);
        CellStyle styleColumnNama = createColumnNamaStyle(workbook);

        // Header
        Row headerRow1 = sheet.createRow(1);
        Cell headerCell1 = headerRow1.createCell(0);
        headerCell1.setCellValue("KAS HARIAN");
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 12));
        headerCell1.setCellStyle(styleColor1);

        Row headerRow2 = sheet.createRow(2);
        createMergedCell(headerRow2, 0, "DATE", styleColor1, 0, 0);
        createMergedCell(headerRow2, 1, "INVOICE NUMB.", styleColor1, 1, 1);
        createMergedCell(headerRow2, 2, "CUSTOMERS", styleColor1, 2, 2);
        createMergedCell(headerRow2, 3, "NAMA BARANG / JASA", styleColor1, 3, 3);
        createMergedCell(headerRow2, 4, "JML BRG", styleColor1, 4, 4);
        createMergedCell(headerRow2, 5, "DEBET", styleColor1, 5, 7);
        createMergedCell(headerRow2, 8, "KREDIT", styleColor1, 8, 11);
        createMergedCell(headerRow2, 12, "SALDO", styleColor1, 12, 12);

        Row headerRow3 = sheet.createRow(3);
        createMergedCell(headerRow3, 5, "SALDO AWAL", styleColor1, 5, 5);
        createMergedCell(headerRow3, 6, "PENJUALAN", styleColor1, 6, 6);
        createMergedCell(headerRow3, 7, "PELUNASAN", styleColor1, 7, 7);
        createMergedCell(headerRow3, 8, "PIUTANG", styleColor1, 8, 8);
        createMergedCell(headerRow3, 9, "RETURN PENJUALAN", styleColor1, 9, 9);
        createMergedCell(headerRow3, 10, "BANK", styleColor1, 10, 10);
        createMergedCell(headerRow3, 11, "SETOR", styleColor1, 11, 11);

        // Set column widths
        sheet.setColumnWidth(0, 15 * 256);
        sheet.setColumnWidth(1, 25 * 256);
        sheet.setColumnWidth(2, 25 * 256);
        sheet.setColumnWidth(3, 25 * 256);
        sheet.setColumnWidth(4, 15 * 256);
        sheet.setColumnWidth(5, 25 * 256);
        sheet.setColumnWidth(6, 25 * 256);
        sheet.setColumnWidth(7, 25 * 256);
        sheet.setColumnWidth(8, 25 * 256);
        sheet.setColumnWidth(9, 25 * 256);
        sheet.setColumnWidth(10, 25 * 256);
        sheet.setColumnWidth(11, 25 * 256);
        sheet.setColumnWidth(12, 25 * 256);

        // Convert Date to LocalDate
        LocalDate startDate = tglAwal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = tglAkhir.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Dates
        int rowIndex = 5;

        while (!startDate.isAfter(endDate)) {
            // Logging the current date
            logger.info("Processing data for date: {}", startDate);

            Date currentDate = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            // Fetch data for Shift Pagi
            SaldoAwalShift saldoAwalShiftPagi = kasHarianService.findByDateAndShift(currentDate, "Pagi");
            List<KasHarian> kasHarianListPagi = kasHarianService.findByDate(currentDate);

            int saldoAwal = safeParseInt(saldoAwalShiftPagi != null ? saldoAwalShiftPagi.getSaldoAwal() : null, 0);
            int penjualan = 0;
            int pelunasan = 0;
            int piutang = 0;
            int returnPenjualan = 0;
            int bank = 0;

            for (KasHarian kasHarian : kasHarianListPagi) {
                penjualan += safeParseInt(kasHarian.getPenjualan(), 0);
                pelunasan += safeParseInt(kasHarian.getPelunasan(), 0);
                piutang += safeParseInt(kasHarian.getPiutang(), 0);
                returnPenjualan += safeParseInt(kasHarian.getReturn_penjualan(), 0);
                bank += safeParseInt(kasHarian.getBank(), 0);
            }

            // Create row for Shift Pagi
            createMergedCell(sheet.createRow(rowIndex), 0, "Shift Pagi", styleColumn, 0, 1);
            createMergedCell(sheet.createRow(rowIndex), 2, "Saldo Awal", styleColor2, 2, 3);
            createCell(sheet.createRow(rowIndex), 5, saldoAwal, styleColumnNumber);

            rowIndex++;

            // Populate cells for Shift Pagi
            Row pagiRow = sheet.createRow(rowIndex);
            createCell(pagiRow, 6, penjualan, styleColumnNumber);
            createCell(pagiRow, 7, pelunasan, styleColumnNumber);
            createCell(pagiRow, 8, piutang, styleColumnNumber);
            createCell(pagiRow, 9, returnPenjualan, styleColumnNumber);
            createCell(pagiRow, 10, bank, styleColumnNumber);

            rowIndex++;
            createCell(sheet.createRow(rowIndex), 5, saldoAwal, styleColor3);

            rowIndex++;

            // Fetch data for Shift Siang
            SaldoAwalShift saldoAwalShiftSiang = kasHarianService.findByDateAndShift(currentDate, "Siang");
            List<KasHarian> kasHarianListSiang = kasHarianService.findByDate(currentDate);

            int saldoAwalS = safeParseInt(saldoAwalShiftSiang != null ? saldoAwalShiftSiang.getSaldoAwal() : null, 0);
            penjualan = 0;
            pelunasan = 0;
            piutang = 0;
            returnPenjualan = 0;
            bank = 0;

            for (KasHarian kasHarian : kasHarianListSiang) {
                penjualan += safeParseInt(kasHarian.getPenjualan(), 0);
                pelunasan += safeParseInt(kasHarian.getPelunasan(), 0);
                piutang += safeParseInt(kasHarian.getPiutang(), 0);
                returnPenjualan += safeParseInt(kasHarian.getReturn_penjualan(), 0);
                bank += safeParseInt(kasHarian.getBank(), 0);
            }

            // Create row for Shift Siang
            createMergedCell(sheet.createRow(rowIndex), 0, "Shift Siang", styleColumn, 0, 1);
            createMergedCell(sheet.createRow(rowIndex), 2, "Saldo Awal", styleColor2, 2, 3);
            createCell(sheet.createRow(rowIndex), 5, saldoAwalS, styleColumnNumber);

            rowIndex++;

            // Populate cells for Shift Siang
            Row siangRow = sheet.createRow(rowIndex);
            createCell(siangRow, 6, penjualan, styleColumnNumber);
            createCell(siangRow, 7, pelunasan, styleColumnNumber);
            createCell(siangRow, 8, piutang, styleColumnNumber);
            createCell(siangRow, 9, returnPenjualan, styleColumnNumber);
            createCell(siangRow, 10, bank, styleColumnNumber);

            rowIndex++;
            createCell(sheet.createRow(rowIndex), 5, saldoAwalS, styleColor3);

            rowIndex++;
            startDate = startDate.plusDays(1);
        }

        // Set response properties
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=KasHarian.xlsx");

        // Write the output to the response output stream
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
        }
    }

    // Method to safely parse integer values
    private void createCell(Row row, int column, int value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
    private int safeParseInt(String value, int defaultValue) {
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void createMergedCell(Row row, int cellIndex, String value, CellStyle style, int startCol, int endCol) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style);
        if (startCol != endCol) {
            row.getSheet().addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), startCol, endCol));
        }
    }

    private void createCell(Sheet sheet, int rowIndex, int cellIndex, int value, CellStyle style) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private CellStyle createColor1Style(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.cloneStyleFrom(createHeaderStyle(workbook));
        return style;
    }

    private CellStyle createColor2Style(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private CellStyle createColor3Style(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.cloneStyleFrom(createColor1Style(workbook));
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private CellStyle createColumnStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return style;
    }

    private CellStyle createColumnNumberStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.cloneStyleFrom(createColumnStyle(workbook));
        style.setAlignment(HorizontalAlignment.RIGHT);
        return style;
    }

    private CellStyle createColumnNamaStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.cloneStyleFrom(createColumnStyle(workbook));
        style.setAlignment(HorizontalAlignment.LEFT);
        return style;
    }

}
