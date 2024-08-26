package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Kunjungan;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelKunjunganAll {
    public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERsLAPORANKUNJUNGAN = {"NO","NAMA CUSTOMER", "NAMA SALESMAN", "ACTION", "CP", "DEAL", "N VISIT", "PELUANG", "PEMBAYARAN", "TANGGAL DEAL", "TANGGAL KUNJUNGAN", "TUJUAN", "VISIT", "WAKTU PENGADAAN"};
    private static final String SHEET_NAME = "LAPORAN KUNJUNGAN";

    private static final String SHEET = "sheet1";

    public ByteArrayInputStream laporanKunjunganToExcel(List<Kunjungan> kunjungans) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header Style
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERsLAPORANKUNJUNGAN.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERsLAPORANKUNJUNGAN[col]);
                cell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(col);
            }

            // Data Style
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            dataStyle.setAlignment(HorizontalAlignment.LEFT);
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Date Style
            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.cloneStyleFrom(dataStyle);
            CreationHelper createHelper = workbook.getCreationHelper();
            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

            int rowIdx = 1;
            int no = 1;

            for (Kunjungan kunjungan : kunjungans) {
                Row row = sheet.createRow(rowIdx++);
                Cell cellNo = row.createCell(0);
                cellNo.setCellValue(no);
                cellNo.setCellStyle(dataStyle);

                String customerName = kunjungan.getCustomer() != null ? kunjungan.getCustomer().getNama_customer() : "";
                Cell cellCustomerName = row.createCell(1);
                cellCustomerName.setCellValue(customerName);
                cellCustomerName.setCellStyle(dataStyle);

                String salesmanName = kunjungan.getSalesman() != null ? kunjungan.getSalesman().getNamaSalesman() : "";
                Cell cellSalesmanName = row.createCell(2);
                cellSalesmanName.setCellValue(salesmanName);
                cellSalesmanName.setCellStyle(dataStyle);

                Cell cellAction = row.createCell(3);
                cellAction.setCellValue(kunjungan.getAction());
                cellAction.setCellStyle(dataStyle);

                Cell cellCp = row.createCell(4);
                cellCp.setCellValue(kunjungan.getCp());
                cellCp.setCellStyle(dataStyle);

                Cell cellDeal = row.createCell(5);
                cellDeal.setCellValue(kunjungan.getDeal());
                cellDeal.setCellStyle(dataStyle);

                Cell cellNVisit = row.createCell(6);
                cellNVisit.setCellValue(kunjungan.getnVisit());
                cellNVisit.setCellStyle(dataStyle);

                Cell cellPeluang = row.createCell(7);
                cellPeluang.setCellValue(kunjungan.getPeluang());
                cellPeluang.setCellStyle(dataStyle);

                Cell cellPembayaran = row.createCell(8);
                cellPembayaran.setCellValue(kunjungan.getPembayaran());
                cellPembayaran.setCellStyle(dataStyle);

                Cell cellTglDeal = row.createCell(9);
                cellTglDeal.setCellValue(kunjungan.getTanggalDeal());
                cellTglDeal.setCellStyle(dateStyle);

                Cell cellTglKunjungan = row.createCell(10);
                cellTglKunjungan.setCellValue(kunjungan.getTanggalKunjungan());
                cellTglKunjungan.setCellStyle(dateStyle);

                Cell cellTujuan = row.createCell(11);
                cellTujuan.setCellValue(kunjungan.getTujuan());
                cellTujuan.setCellStyle(dataStyle);

                Cell cellVisit = row.createCell(12);
                cellVisit.setCellValue(kunjungan.getVisit());
                cellVisit.setCellStyle(dataStyle);

                Cell cellWktPengadaan = row.createCell(13);
                cellWktPengadaan.setCellValue(kunjungan.getWaktuPengadaan());
                cellWktPengadaan.setCellStyle(dataStyle);

                no++;
            }

            for (int col = 0; col < HEADERsLAPORANKUNJUNGAN.length; col++) {
                sheet.autoSizeColumn(col);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to excel file: " + e.getMessage());
        }
    }
}
