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

    public void excelKasHarian(Date tglAwal, Date tglAkhir) throws IOException {
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
        createMergedCell(headerRow2, 0, "DATE", styleColor1); // A3:A3 (no merge needed)
        createMergedCell(headerRow2, 1, "INVOICE NUMB.", styleColor1); // B3:B3 (no merge needed)
        createMergedCell(headerRow2, 2, "CUSTOMERS", styleColor1); // C3:C3 (no merge needed)
        createMergedCell(headerRow2, 3, "NAMA BARANG / JASA", styleColor1); // D3:D3 (no merge needed)
        createMergedCell(headerRow2, 4, "JML BRG", styleColor1); // E3:E3 (no merge needed)
        createMergedCell(headerRow2, 5, "DEBET", styleColor1); // F3:H3 (merged region F3:G3 is adjusted)
        createMergedCell(headerRow2, 8, "KREDIT", styleColor1); // I3:K3
        createMergedCell(headerRow2, 12, "SALDO", styleColor1); // M3:N3

        // Dates


        int rowIndex = 5;
        int endRowSiang = 5;
        int saldoAwal = 0;
        int saldoAwalS = 0;

        while (!tglAwal.equals(tglAkhir)) {

            // Shift Pagi
            createMergedCell(sheet.createRow(rowIndex), 0, "Shift Pagi", styleColumn);
            createMergedCell(sheet.createRow(rowIndex), 2, "Saldo Awal", styleColor2);
            createCell(sheet, rowIndex, 5, saldoAwal, styleColumnNumber);

            rowIndex++;

            // Your data retrieval logic goes here, omitted for brevity

            rowIndex++;
            createCell(sheet, rowIndex, 5, saldoAwal, styleColor3);

            rowIndex++;

            // Shift Siang
            createMergedCell(sheet.createRow(rowIndex), 0, "Shift Siang", styleColumn);
            createMergedCell(sheet.createRow(rowIndex), 2, "Saldo Awal", styleColor2);
            createCell(sheet, rowIndex, 5, saldoAwalS, styleColumnNumber);

            rowIndex++;

            // Your data retrieval logic goes here, omitted for brevity

            rowIndex++;
            createCell(sheet, rowIndex, 5, saldoAwalS, styleColor3);

            rowIndex++;


        }

        // Write the output to a file
        try (FileOutputStream fileOut = new FileOutputStream("KasHarian.xlsx")) {
            workbook.write(fileOut);
        }

        // Closing the workbook
        workbook.close();
    }

    private void createMergedCell(Row row, int cellIndex, String value, CellStyle style) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style);
        row.getSheet().addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), cellIndex, cellIndex + 1));
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
