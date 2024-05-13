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

    public void excelHutang(Date tglAwal, Date tglAkhir, HttpServletResponse response) throws IOException {
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
            row.createCell(0).setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(hutang.getDate()));
            TransaksiBeli transaksiBeli = transaksiBeliRepository.findById(hutang.getTransaksiBeli().getIdTransaksiBeli()).get();
            row.createCell(1).setCellValue(transaksiBeli.getNoFaktur());
            row.createCell(2).setCellValue(transaksiBeli.getNamaSuplier());
            row.createCell(3).setCellValue(hutang.getHutang());
            int umurPiutang = Period.between(LocalDate.parse(hutang.getDate().toString()), LocalDate.now()).getDays();
            row.createCell(4).setCellValue(umurPiutang);

            for (int i = 0; i < headers.length; i++) {
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

    public void excelRekapHutang(HttpServletResponse response) throws IOException {
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
        for (Hutang hutang : hutangs) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(hutang.getDate()));
            TransaksiBeli transaksiBeli = transaksiBeliRepository.findById(hutang.getTransaksiBeli().getIdTransaksiBeli()).get();
            row.createCell(1).setCellValue(transaksiBeli.getNoFaktur());
            row.createCell(2).setCellValue(transaksiBeli.getNamaSuplier());
            row.createCell(3).setCellValue(hutang.getHutang());
            int umurPiutang = Period.between(LocalDate.parse(hutang.getDate().toString()), LocalDate.now()).getDays();
            row.createCell(4).setCellValue(umurPiutang);

            for (int i = 0; i < headers.length; i++) {
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
}

