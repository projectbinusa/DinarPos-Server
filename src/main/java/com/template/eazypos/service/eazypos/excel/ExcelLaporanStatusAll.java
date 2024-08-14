package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Status;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelLaporanStatusAll {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERsStatus = {"NO", "NAMA CUSTOMER", "NAMA TEKNISI", "KETERANGAN", "SOLUSI", "STATUS", "TANGGAL", "TYPE", "VALIDASI"};
    static String[] HEADERsTemplate = {"NO", "NAMA CUSTOMER", "NAMA TEKNISI", "KETERANGAN", "SOLUSI", "STATUS", "TANGGAL", "TYPE", "VALIDASI"};
    static String SHEET = "Sheet1";

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static ByteArrayInputStream statusToExcel(List<Status> statuses) throws IOException {
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
            for (int col = 0; col < HEADERsStatus.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERsStatus[col]);
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
            for (Status status : statuses) {
                Row row = sheet.createRow(rowIdx++);
                Cell cellNo = row.createCell(0);
                cellNo.setCellValue(no);

                Cell cellCustomerName = row.createCell(1);
                if (status.getService() != null && status.getService().getCustomer() != null) {
                    cellCustomerName.setCellValue(status.getService().getCustomer().getNama_customer());
                }
                cellCustomerName.setCellStyle(dataStyle);

                Cell cellTechnicianName = row.createCell(2);
                if (status.getTeknisi() != null) {
                    cellTechnicianName.setCellValue(status.getTeknisi().getNama());
                }
                cellTechnicianName.setCellStyle(dataStyle);

                Cell cellKet = row.createCell(3);
                cellKet.setCellValue(status.getKet());
                cellKet.setCellStyle(dataStyle);

                Cell celSolusi = row.createCell(4);
                celSolusi.setCellValue(status.getSolusi());
                celSolusi.setCellStyle(dataStyle);

                Cell cellStatus = row.createCell(5);
                cellStatus.setCellValue(status.getStatus());
                cellStatus.setCellStyle(dataStyle);

                Cell cellTanggal = row.createCell(6);
                if (status.getTanggal() != null) {
                    cellTanggal.setCellValue(status.getTanggal());
                    cellTanggal.setCellStyle(dateStyle);
                }

                Cell cellType = row.createCell(7);
                cellType.setCellValue(status.getType());
                cellType.setCellStyle(dataStyle);

                Cell cellValidasi = row.createCell(8);
                cellValidasi.setCellValue(status.getValidasi());
                cellValidasi.setCellStyle(dataStyle);

                no++;
            }

            for (int col = 0; col < HEADERsStatus.length; col++) {
                sheet.autoSizeColumn(col);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    public static ByteArrayInputStream templateToExcel() throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);

            Row headerRow = sheet.createRow(0);
            CellStyle stringCellStyle = workbook.createCellStyle();
            DataFormat dataFormat = workbook.createDataFormat();
            stringCellStyle.setDataFormat(dataFormat.getFormat("@"));

            for (int col = 0; col < HEADERsTemplate.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERsTemplate[col]);
                cell.setCellStyle(stringCellStyle);
            }

            for (int col = 0; col < HEADERsTemplate.length; col++) {
                sheet.autoSizeColumn(col);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    public static List<Status> excelToStatus(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);

            List<Status> statusList = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Status status = new Status();

                for (int cellIdx = 0; cellIdx <9; cellIdx++) {
                    Cell currentCell = row.getCell(cellIdx, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    switch (cellIdx) {
                        case 3:
                            status.setKet(currentCell.getStringCellValue());
                            break;
                        case 4:
                            status.setSolusi(currentCell.getStringCellValue());
                            break;
                        case 5:
                            status.setStatus(currentCell.getStringCellValue());
                            break;
                        case 6:
                            status.setTanggal(currentCell.getDateCellValue());
                            break;
                        case 7:
                            status.setType(currentCell.getStringCellValue());
                            break;
                        case 8:
                            status.setValidasi(currentCell.getStringCellValue());
                            break;
                    }
                }
                statusList.add(status);

            }

            workbook.close();
            return statusList;
            } catch (IOException e) {
            throw new RuntimeException("fail to parse excel file: " + e.getMessage());
        }
    }
}
