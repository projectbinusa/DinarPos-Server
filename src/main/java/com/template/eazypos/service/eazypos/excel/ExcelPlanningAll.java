package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Customer;
import com.template.eazypos.model.Planning;
import com.template.eazypos.repository.MarkettingRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelPlanningAll {
    public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERsLAPORANPLANNING = {"NO", "MARKETING", "TANGGAL", "NAMA CUSTOMER", "JENIS", "DAERAH", "PIHAK DITUJU", "TUJUAN", "TIMESTAMP"};
    private static final String SHEET_NAME = "LAPORAN PLANNING";

    @Autowired
    private MarkettingRepository markettingRepository;

    private static final String SHEET = "sheet1";

    public ByteArrayInputStream laporanPlanningToExcel(List<Planning> plannings) {
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
            for (int col = 0; col < HEADERsLAPORANPLANNING.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERsLAPORANPLANNING[col]);
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

            // Timestamp Style
            CellStyle timestampStyle = workbook.createCellStyle();
            timestampStyle.cloneStyleFrom(dataStyle);
            timestampStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

            int rowIdx = 1;
            int no = 1;

            for (Planning planning : plannings) {
                Row row = sheet.createRow(rowIdx++);
                Cell cellNo = row.createCell(0);
                cellNo.setCellValue(no);
                cellNo.setCellStyle(dataStyle);

                Cell cellTgl = row.createCell(2);
                cellTgl.setCellValue(planning.getTgl());
                cellTgl.setCellStyle(dateStyle);

                String customerName = planning.getCustomer() != null ? planning.getCustomer().getNama_customer() : "";
                Cell cellCustomerName = row.createCell(3);
                cellCustomerName.setCellValue(customerName);
                cellCustomerName.setCellStyle(dataStyle);

                String customerJenis = planning.getCustomer() != null ? planning.getCustomer().getJenis() : "";
                Cell cellCustomerJenis = row.createCell(4);
                cellCustomerJenis.setCellValue(customerJenis);
                cellCustomerJenis.setCellStyle(dataStyle);

                String daerah = "";
                if (planning.getCustomer() != null) {
                    Customer customer = planning.getCustomer();
                    String kecName = customer.getKec() != null ? customer.getKec().getNama_kec() : "";
                    String kabKotName = customer.getKabKot() != null ? customer.getKabKot().getNama_kabkot() : "";
                    String provName = customer.getProv() != null ? customer.getProv().getNamaProv() : "";

                    daerah = customer.getAlamat() + " / " +
                             kecName + " / " +
                             kabKotName + " / " +
                             provName + " / ";
                }
                Cell cellDaerah = row.createCell(5);
                cellDaerah.setCellValue(daerah);
                cellDaerah.setCellStyle(dataStyle);

                Cell cellKet = row.createCell(7);
                cellKet.setCellValue(planning.getKet());
                cellKet.setCellStyle(dataStyle);

                Cell cellTimestamp = row.createCell(8);
                cellTimestamp.setCellValue(planning.getTimestamp());
                cellTimestamp.setCellStyle(timestampStyle);

                no++;
            }

            for (int col = 0; col < HEADERsLAPORANPLANNING.length; col++) {
                sheet.autoSizeColumn(col);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail toimport data to excel file: " + e.getMessage());
        }
    }
}
