package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Hutang;
import com.template.eazypos.model.TransaksiBeli;
import com.template.eazypos.repository.HutangRepository;
import com.template.eazypos.repository.TransaksiBeliRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ExcelHutangService {

    @Autowired
    private HutangRepository hutangRepository;

    @Autowired
    private TransaksiBeliRepository transaksiBeliRepository;

    public void excelRekapHutang(Date tglAwal, Date tglAkhir, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("RekapHutang");

        // Cell styles
        CellStyle styleHeader = workbook.createCellStyle();
        styleHeader.setAlignment(HorizontalAlignment.CENTER);
        styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        styleHeader.setBorderTop(BorderStyle.THIN);
        styleHeader.setBorderRight(BorderStyle.THIN);
        styleHeader.setBorderBottom(BorderStyle.THIN);
        styleHeader.setBorderLeft(BorderStyle.THIN);
        styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle styleNumber = workbook.createCellStyle();
        styleNumber.setAlignment(HorizontalAlignment.RIGHT);
        styleNumber.setVerticalAlignment(VerticalAlignment.CENTER);
        styleNumber.setBorderTop(BorderStyle.THIN);
        styleNumber.setBorderRight(BorderStyle.THIN);
        styleNumber.setBorderBottom(BorderStyle.THIN);
        styleNumber.setBorderLeft(BorderStyle.THIN);

        // Conditional formatting colors
        CellStyle styleColor1 = workbook.createCellStyle();
        styleColor1.cloneStyleFrom(styleNumber);
        styleColor1.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        styleColor1.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle styleColor2 = workbook.createCellStyle();
        styleColor2.cloneStyleFrom(styleNumber);
        styleColor2.setFillForegroundColor(IndexedColors.RED.getIndex());
        styleColor2.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle styleColor3 = workbook.createCellStyle();
        styleColor3.cloneStyleFrom(styleNumber);
        styleColor3.setFillForegroundColor(IndexedColors.DARK_RED.getIndex());
        styleColor3.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle styleColor4 = workbook.createCellStyle();
        styleColor4.cloneStyleFrom(styleNumber);
        styleColor4.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        styleColor4.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Header
        Row headerRow = sheet.createRow(2);
        String[] headers = {"TGL INVOICE", "NO INVOICE", "NAMA SUPLIER", "SISA PIUTANG (Rp)", "UMUR PIUTANG (Hari)"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(styleHeader);
        }

        // Data
        List<Hutang> hutangs = hutangRepository.findByTanggalBetween(tglAwal, tglAkhir);
        int rowNum = 3;
        for (Hutang hutang : hutangs) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(hutang.getDate());
            TransaksiBeli transaksiBeli = transaksiBeliRepository.findById(hutang.getTransaksiBeli().getIdTransaksiBeli()).get();
            row.createCell(1).setCellValue(transaksiBeli.getNoFaktur());
            row.createCell(2).setCellValue(transaksiBeli.getNamaSuplier());
            row.createCell(3).setCellValue(hutang.getHutang());
            long tglKembali = Math.abs(new Date().getTime() - hutang.getDate().getTime());
            long convert = TimeUnit.DAYS.convert(tglKembali, TimeUnit.MILLISECONDS);
            Cell cellUmurPiutang = row.createCell(4);
            cellUmurPiutang.setCellValue(convert);

            // Apply cell style for UMUR PIUTANG column
            if (convert > 90) {
                cellUmurPiutang.setCellStyle(styleColor3);
            } else if (convert > 30) {
                cellUmurPiutang.setCellStyle(styleColor2);
            } else if (convert > 14) {
                cellUmurPiutang.setCellStyle(styleColor1);
            } else {
                cellUmurPiutang.setCellStyle(styleNumber);
            }

            // Apply cell styles for other columns
            for (int i = 0; i < 4; i++) {
                row.getCell(i).setCellStyle(styleNumber);
            }
        }

        // Auto size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=RekapHutang.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    public void excelHutang(HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("BukuHutang");

        // Cell styles
        CellStyle styleHeader = workbook.createCellStyle();
        styleHeader.setAlignment(HorizontalAlignment.CENTER);
        styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        styleHeader.setBorderTop(BorderStyle.THIN);
        styleHeader.setBorderRight(BorderStyle.THIN);
        styleHeader.setBorderBottom(BorderStyle.THIN);
        styleHeader.setBorderLeft(BorderStyle.THIN);
        styleHeader.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle styleNumber = workbook.createCellStyle();
        styleNumber.setAlignment(HorizontalAlignment.RIGHT);
        styleNumber.setVerticalAlignment(VerticalAlignment.CENTER);
        styleNumber.setBorderTop(BorderStyle.THIN);
        styleNumber.setBorderRight(BorderStyle.THIN);
        styleNumber.setBorderBottom(BorderStyle.THIN);
        styleNumber.setBorderLeft(BorderStyle.THIN);

        // Conditional formatting colors
        CellStyle styleColor1 = workbook.createCellStyle();
        styleColor1.cloneStyleFrom(styleNumber);
        styleColor1.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        styleColor1.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle styleColor2 = workbook.createCellStyle();
        styleColor2.cloneStyleFrom(styleNumber);
        styleColor2.setFillForegroundColor(IndexedColors.RED.getIndex());
        styleColor2.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle styleColor3 = workbook.createCellStyle();
        styleColor3.cloneStyleFrom(styleNumber);
        styleColor3.setFillForegroundColor(IndexedColors.DARK_RED.getIndex());
        styleColor3.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Header
        Row headerRow = sheet.createRow(2);
        String[] headers = {"TGL INVOICE", "NO INVOICE", "NAMA SUPLIER", "SISA PIUTANG (Rp)", "UMUR PIUTANG (Hari)"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(styleHeader);
        }

        // Data
        List<Hutang> hutangs = hutangRepository.findAll();
        int rowNum = 3;
        int total = 0;
        for (Hutang hutang : hutangs) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(hutang.getDate());
            TransaksiBeli transaksiBeli = transaksiBeliRepository.findById(hutang.getTransaksiBeli().getIdTransaksiBeli()).get();
            row.createCell(1).setCellValue(transaksiBeli.getNoFaktur());
            row.createCell(2).setCellValue(transaksiBeli.getNamaSuplier());
            row.createCell(3).setCellValue(hutang.getHutang());
            long tglKembali = Math.abs(new Date().getTime() - hutang.getDate().getTime());
            long convert = TimeUnit.DAYS.convert(tglKembali, TimeUnit.MILLISECONDS);
            Cell cellUmurPiutang = row.createCell(4);
            cellUmurPiutang.setCellValue(convert);

            // Apply cell style for UMUR PIUTANG column
            if (convert > 90) {
                cellUmurPiutang.setCellStyle(styleColor3);
            } else if (convert > 30) {
                cellUmurPiutang.setCellStyle(styleColor2);
            } else if (convert > 14) {
                cellUmurPiutang.setCellStyle(styleColor1);
            } else {
                cellUmurPiutang.setCellStyle(styleNumber);
            }

            // Apply cell styles for other columns
            for (int i = 0; i < 4; i++) {
                row.getCell(i).setCellStyle(styleNumber);
            }
            int kekurangan = Integer.parseInt(hutang.getHutang());
            total += kekurangan;
        }


        // Auto size columns
        for (int i = 0; i < headers.length;  i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=BukuHutang.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
