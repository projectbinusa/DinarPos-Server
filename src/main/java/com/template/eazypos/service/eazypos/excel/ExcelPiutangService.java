package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Piutang;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.repository.PiutangRepository;
import com.template.eazypos.repository.TransaksiRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ExcelPiutangService {

    @Autowired
    private PiutangRepository hutangRepository;

    @Autowired
    private TransaksiRepository transaksiBeliRepository;

    // Method untuk membuat laporan buku piutang dalam format Excel
    public void excelBukuPiutang(Date tglAwal, Date tglAkhir, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("BukuPiutang");

        // Cell styles
        CellStyle styleHeader = workbook.createCellStyle();
        styleHeader.setAlignment(HorizontalAlignment.CENTER);
        styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        styleHeader.setBorderTop(BorderStyle.THIN);
        styleHeader.setBorderRight(BorderStyle.THIN);
        styleHeader.setBorderBottom(BorderStyle.THIN);
        styleHeader.setBorderLeft(BorderStyle.THIN);

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
        styleColor2.setFillForegroundColor(IndexedColors.ROSE.getIndex());
        styleColor2.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle styleColor3 = workbook.createCellStyle();
        styleColor3.cloneStyleFrom(styleNumber);
        styleColor3.setFillForegroundColor(IndexedColors.RED.getIndex());
        styleColor3.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle styleColor4 = workbook.createCellStyle();
        styleColor4.cloneStyleFrom(styleNumber);
        styleColor4.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        styleColor4.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Header
        Row headerRow = sheet.createRow(2);
        String[] headers = {"BULAN" ,"TAHUN","NAMA CUSTOMER", "TGL INVOICE", "NO INVOICE","NOMINAL INVOICE","PEMBAYARAN", "SISA PIUTANG (Rp)", "UMUR PIUTANG (Hari)"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(styleHeader);
        }

        // Data
        List<Piutang> hutangs = hutangRepository.findByTanggalBetween(tglAwal, tglAkhir);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        int rowNum = 3;
        double sisaPiutang = 0;
        double nominalInvoice = 0;
        double pembayaran = 0;

        for (Piutang hutang : hutangs) {
            Transaksi transaksiBeli = transaksiBeliRepository.findById(hutang.getTransaksi().getIdTransaksi()).get();
            Row row = sheet.createRow(rowNum++);
            Date now = hutang.getDate();
            row.createCell(0).setCellValue(month.format(now));
            row.createCell(1).setCellValue(year.format(now));
            row.createCell(2).setCellValue(transaksiBeli.getNamaCustomer());
            row.createCell(3).setCellValue(dateFormat.format(transaksiBeli.getTanggal()));
            row.createCell(4).setCellValue(transaksiBeli.getNoFaktur());
            row.createCell(5).setCellValue(transaksiBeli.getTotalBelanja());
            row.createCell(6).setCellValue(transaksiBeli.getPembayaran());
            row.createCell(7).setCellValue(hutang.getKekurangan());
            double sisaPiutangSementara = Double.parseDouble(hutang.getKekurangan());
            double nominalInvoiceSementara = transaksiBeli.getTotalBelanja();
            double pembayaranSementara = transaksiBeli.getPembayaran();
            sisaPiutang += sisaPiutangSementara;
            nominalInvoice += nominalInvoiceSementara;
            pembayaran += pembayaranSementara;
            long tglKembali = Math.abs(new Date().getTime() - hutang.getDate().getTime());
            long convert = TimeUnit.DAYS.convert(tglKembali, TimeUnit.MILLISECONDS);
            Cell cellUmurPiutang = row.createCell(8);
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

        // Totals row
        Row totalRow = sheet.createRow(rowNum++);
        Cell totalCell = totalRow.createCell(0);
        totalCell.setCellValue("TOTAL");
        totalCell.setCellStyle(styleHeader);

        // Merge cells for the 'TOTAL' label
        sheet.addMergedRegion(new CellRangeAddress(totalRow.getRowNum(), totalRow.getRowNum(), 0, 4));

        // Set and style the totals
        Cell totalNominalCell = totalRow.createCell(7);
        totalNominalCell.setCellValue(sisaPiutang);
        totalNominalCell.setCellStyle(styleNumber);

        Cell invoce = totalRow.createCell(5);
        invoce.setCellValue(nominalInvoice);
        invoce.setCellStyle(styleNumber);

        Cell pembayarancell = totalRow.createCell(6);
        pembayarancell.setCellValue(pembayaran);
        pembayarancell.setCellStyle(styleNumber);


        // Auto size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=BukuPiutang.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // Method untuk membuat laporan rekap piutang dalam format Excel
    public void excelRekapPiutang(HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("RekapPiutang");

        // Cell styles
        CellStyle styleHeader = workbook.createCellStyle();
        styleHeader.setAlignment(HorizontalAlignment.CENTER);
        styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        styleHeader.setBorderTop(BorderStyle.THIN);
        styleHeader.setBorderRight(BorderStyle.THIN);
        styleHeader.setBorderBottom(BorderStyle.THIN);
        styleHeader.setBorderLeft(BorderStyle.THIN);

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
        styleColor2.setFillForegroundColor(IndexedColors.ROSE.getIndex());
        styleColor2.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle styleColor3 = workbook.createCellStyle();
        styleColor3.cloneStyleFrom(styleNumber);
        styleColor3.setFillForegroundColor(IndexedColors.RED.getIndex());
        styleColor3.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle styleColor4 = workbook.createCellStyle();
        styleColor4.cloneStyleFrom(styleNumber);
        styleColor4.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        styleColor4.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Header
        Row headerRow = sheet.createRow(2);
        String[] headers = {"TGL INVOICE", "NO INVOICE", "NAMA CUSTOMER", "SISA piutang (Rp)", "UMUR piutang (Hari)"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(styleHeader);
        }

        // Data
        List<Transaksi> hutangs = transaksiBeliRepository.findAllPiutang();
        int rowNum = 3;
        int total = 0;
        for (Transaksi hutang : hutangs) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(hutang.getTanggal());
            row.createCell(1).setCellValue(hutang.getNoFaktur());
            row.createCell(2).setCellValue(hutang.getNamaCustomer());
            row.createCell(3).setCellValue(hutang.getKekurangan());
            long tglKembali = Math.abs(new Date().getTime() - hutang.getTanggal().getTime());
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
            int kekurangan = Integer.parseInt(hutang.getKekurangan());
            total += kekurangan;
        }

        Row totalRow = sheet.createRow(rowNum++);
        Cell totalCell = totalRow.createCell(0);
        totalCell.setCellValue("TOTAL");
        totalCell.setCellStyle(styleHeader);

        // Merge cells for the 'TOTAL' label
        sheet.addMergedRegion(new CellRangeAddress(totalRow.getRowNum(), totalRow.getRowNum(), 0, 2));

        // Set and style the totals
        Cell totalNominalCell = totalRow.createCell(3);
        totalNominalCell.setCellValue(total);
        totalNominalCell.setCellStyle(styleNumber);

        // Auto size columns
        for (int i = 0; i < headers.length;  i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=RekapPiutang.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
