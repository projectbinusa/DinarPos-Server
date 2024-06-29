package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Hutang;
import com.template.eazypos.model.TransaksiBeli;
import com.template.eazypos.repository.HutangRepository;
import com.template.eazypos.repository.TransaksiBeliRepository;
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
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ExcelHutangService {

    @Autowired
    private HutangRepository hutangRepository;

    @Autowired
    private TransaksiBeliRepository transaksiBeliRepository;

    // Method untuk menghasilkan Excel Buku Hutang berdasarkan rentang tanggal
    public void excelBukuHutang(Date tglAwal, Date tglAkhir, HttpServletResponse response) throws IOException {
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
        String[] headers = {"BULAN" ,"TAHUN","NAMA SUPLIER", "TGL INVOICE", "NO INVOICE","NOMINAL INVOICE","PEMBAYARAN", "SISA HUTANG (Rp)", "UMUR HUTANG (Hari)"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(styleHeader);
        }

        // Data
        List<Hutang> hutangs = hutangRepository.findByTanggalBetween(tglAwal, tglAkhir);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        int rowNum = 3;
        double sisaHutang = 0;
        double nominalInvoice = 0;
        double pembayaran = 0;

        for (Hutang hutang : hutangs) {
            TransaksiBeli transaksiBeli = transaksiBeliRepository.findById(hutang.getTransaksiBeli().getIdTransaksiBeli()).get();
            Row row = sheet.createRow(rowNum++);
            Date now = hutang.getDate();
            row.createCell(0).setCellValue(month.format(now));
            row.createCell(1).setCellValue(year.format(now));
            row.createCell(2).setCellValue(transaksiBeli.getNamaSuplier());
            row.createCell(3).setCellValue(dateFormat.format(transaksiBeli.getTanggal()));
            row.createCell(4).setCellValue(transaksiBeli.getNoFaktur());
            row.createCell(5).setCellValue(transaksiBeli.getTotalBelanja());
            row.createCell(6).setCellValue(transaksiBeli.getPembayaran());
            row.createCell(7).setCellValue(hutang.getHutang());
            double sisaHutangSementara = Double.parseDouble(hutang.getHutang());
            double nominalInvoiceSementara = Double.parseDouble(transaksiBeli.getTotalBelanja());
            double pembayaranSementara = transaksiBeli.getPembayaran();
            sisaHutang += sisaHutangSementara;
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
        totalNominalCell.setCellValue(sisaHutang);
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
        response.setHeader("Content-Disposition", "attachment; filename=BukuHutang.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // Method untuk menghasilkan Excel Rekap Hutang
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
        String[] headers = {"TGL INVOICE", "NO INVOICE", "NAMA SUPLIER", "SISA HUTANG (Rp)", "UMUR HUTANG (Hari)"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(styleHeader);
        }

        // Data
        List<TransaksiBeli> hutangs = transaksiBeliRepository.findAllHutang();
        int rowNum = 3;
        int total = 0;
        for (TransaksiBeli hutang : hutangs) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(hutang.getTanggal());
            row.createCell(1).setCellValue(hutang.getNoFaktur());
            row.createCell(2).setCellValue(hutang.getNamaSuplier());
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
        response.setHeader("Content-Disposition", "attachment; filename=RekapHutang.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
    public void exportHistoryHutang(HttpServletResponse response, Date startDate, Date endDate) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("HistoryHutang");

        // Create styles
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFont(headerFont);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        CellStyle numberStyle = workbook.createCellStyle();
        numberStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));
        numberStyle.setBorderTop(BorderStyle.THIN);
        numberStyle.setBorderBottom(BorderStyle.THIN);
        numberStyle.setBorderLeft(BorderStyle.THIN);
        numberStyle.setBorderRight(BorderStyle.THIN);

        // Create header row
        Row headerRow = sheet.createRow(2);
        String[] headers = {"TGL INVOICE", "NAMA SUPLIER", "NO INVOICE", "HUTANG (Rp)", "PEMBAYARAN 1 (Rp)", "PEMBAYARAN 2 (Rp)", "PEMBAYARAN 3 (Rp)", "PEMBAYARAN 4 (Rp)", "SISA HUTANG (Rp)", "TGL PEMBAYARAN TERAKHIR", "KETERANGAN"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Create data rows
        int rowNum = 3;
        List<TransaksiBeli> hutangDataList = transaksiBeliRepository.findHistoryHutang(startDate, endDate);
        double totalHutang = 0;

        for (TransaksiBeli data : hutangDataList) {
            Row row = sheet.createRow(rowNum++);
            List<Hutang> hutangs = hutangRepository.findByidTransaksiBeli(data.getIdTransaksiBeli());

            double pelunasan1 = hutangs.size() > 0 ? Double.parseDouble(hutangs.get(0).getPelunasan()) : 0;
            double pelunasan2 = hutangs.size() > 1 ? Double.parseDouble(hutangs.get(1).getPelunasan()) : 0;
            double pelunasan3 = hutangs.size() > 2 ? Double.parseDouble(hutangs.get(2).getPelunasan()) : 0;
            double pelunasan4 = hutangs.size() > 3 ? Double.parseDouble(hutangs.get(3).getPelunasan()) : 0;

            double sisaHutang = data.getNominalHutang() - (pelunasan1 + pelunasan2 + pelunasan3 + pelunasan4);
            totalHutang += data.getNominalHutang();

            Optional<Date> tglPelunasanTerakhir = hutangRepository.findLatestPelunasanDateByIdTransaksiBeli(data.getIdTransaksiBeli());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            createCell(row, 0, dateFormat.format(data.getTanggal()), cellStyle);
            createCell(row, 1, data.getNamaSuplier(), cellStyle);
            createCell(row, 2, data.getNoFaktur(), cellStyle);
            createCell(row, 3, data.getNominalHutang(), numberStyle);
            createCell(row, 4, pelunasan1, numberStyle);
            createCell(row, 5, pelunasan2, numberStyle);
            createCell(row, 6, pelunasan3, numberStyle);
            createCell(row, 7, pelunasan4, numberStyle);
            createCell(row, 8, sisaHutang, numberStyle);
            createCell(row, 9, tglPelunasanTerakhir.map(dateFormat::format).orElse("-"), cellStyle);
            createCell(row, 10, data.getKeterangan(), cellStyle);
        }

        // Total row
        Row totalRow = sheet.createRow(rowNum);
        createCell(totalRow, 2, "Total", headerStyle);
        createCell(totalRow, 3, totalHutang, numberStyle);

        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Set response headers
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"HistoryHutang.xlsx\"");

        // Write to response stream
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private void createCell(Row row, int column, Object value, CellStyle style) {
        Cell cell = row.createCell(column);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        }
        cell.setCellStyle(style);
    }
}
