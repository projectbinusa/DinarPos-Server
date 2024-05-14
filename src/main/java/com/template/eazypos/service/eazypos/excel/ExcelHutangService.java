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
        List<Hutang> hutangs = hutangRepository.findByTanggalBetween(tglAwal, tglAkhir);
        int rowNum = 3;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        double totalSisaHutang = 0;

        for (Hutang hutang : hutangs) {
            Row row = sheet.createRow(rowNum++);
            Date tanggalInvoice = hutang.getDate();
            String noInvoice = hutang.getTransaksiBeli().getNoFaktur();
            String namaSuplier = hutang.getTransaksiBeli().getNamaSuplier();
            double sisaPiutang = Double.parseDouble(hutang.getHutang());
            long umurPiutang = Period.between(
                    LocalDate.parse(dateFormat.format(tanggalInvoice)),
                    LocalDate.now()
            ).getDays();

            // Set cell values and styles
            for (int i = 0; i < 5; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(styleNumber); // Set default style to number style
                if (i == 0) {
                    cell.setCellValue(dateFormat.format(tanggalInvoice));
                } else if (i == 1) {
                    cell.setCellValue(noInvoice);
                } else if (i == 2) {
                    cell.setCellValue(namaSuplier);
                } else if (i == 3) {
                    cell.setCellValue(sisaPiutang);
                } else if (i == 4) {
                    cell.setCellValue(umurPiutang);
                    // Apply conditional formatting for UMUR PIUTANG column
                    if (umurPiutang > 90) {
                        cell.setCellStyle(styleColor3);
                    } else if (umurPiutang > 30) {
                        cell.setCellStyle(styleColor2);
                    } else if (umurPiutang > 14) {
                        cell.setCellStyle(styleColor1);
                    }
                }
            }

            totalSisaHutang += sisaPiutang;
        }


        // Total row
        Row totalRow = sheet.createRow(rowNum++);
        totalRow.createCell(0).setCellValue("TOTAL");
        totalRow.createCell(3).setCellValue(totalSisaHutang);
        for (int i = 0; i < headers.length; i++) {
            totalRow.getCell(i).setCellStyle(styleHeader);
        }
        totalRow.getCell(3).setCellStyle(styleHeader);

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        for (Hutang hutang : hutangs) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(dateFormat.format(hutang.getDate())); // Format the date
            TransaksiBeli transaksiBeli = transaksiBeliRepository.findById(hutang.getTransaksiBeli().getIdTransaksiBeli()).get();
            row.createCell(1).setCellValue(transaksiBeli.getNoFaktur());
            row.createCell(2).setCellValue(transaksiBeli.getNamaSuplier());
            row.createCell(3).setCellValue(hutang.getHutang());
            LocalDate tglHutang = LocalDate.parse(dateFormat.format(hutang.getDate()).substring(0, 10));
            int umurPiutang = Period.between(tglHutang, LocalDate.now()).getDays();
            Cell cellUmurPiutang = row.createCell(4);
            cellUmurPiutang.setCellValue(umurPiutang);

            // Apply cell style for UMUR PIUTANG column
            if (umurPiutang > 90) {
                cellUmurPiutang.setCellStyle(styleColor3);
            } else if (umurPiutang > 30) {
                cellUmurPiutang.setCellStyle(styleColor2);
            } else if (umurPiutang > 14) {
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
        response.setHeader("Content-Disposition", "attachment; filename=BukuHutang.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
