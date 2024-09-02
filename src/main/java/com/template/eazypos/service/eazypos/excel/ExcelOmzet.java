package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Omzet;
import com.template.eazypos.model.Piutang;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.repository.OmzetRepository;
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
public class ExcelOmzet {
    @Autowired
    private OmzetRepository omzetRepository;

    public void excelLaporanOmzet(Date tglAwal, Date tglAkhir ,Long id, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("LaporanOmzet");

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
        Row headerRow = sheet.createRow(4);
        String[] headers = {"NO" ,"TANGGAL","NAMA CUSTOMER", "OMZET"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(styleHeader);
        }

        // Data
        List<Omzet> omzets = omzetRepository.findByDateAndSalesman(tglAwal, tglAkhir ,id);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int rowNum = 5;
        int no = 0;
        for (Omzet omzet : omzets) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(no);
            row.createCell(1).setCellValue(dateFormat.format(omzet.getTgl()));
            row.createCell(2).setCellValue(omzet.getCustomer().getNama_customer());
            row.createCell(3).setCellValue(omzet.getOmzet());
            no++;
        }




        // Auto size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=LaporanOmzet.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }


}
