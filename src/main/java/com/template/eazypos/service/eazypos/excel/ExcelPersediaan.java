package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Persediaan;
import org.apache.poi.ss.usermodel.*;
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
public class ExcelPersediaan {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERsPersediaan = {"NO", "BARANG SIAP JUAL", "PENJUALAN", "PEMBELIAN", "PERSEDIAAN AWAL", "PERSEDIAAN AKHIR", "TANGGAL"};
    static String[] HEADERsTemplate = {"NO", "BARANG SIAP JUAL", "PENJUALAN", "PEMBELIAN", "PERSEDIAAN AWAL", "PERSEDIAAN AKHIR", "TANGGAL"};
    static String SHEET = "Sheet1";

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static ByteArrayInputStream persediaanToExcel(List<Persediaan> persediaans) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Membuat baris header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERsPersediaan.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERsPersediaan[col]);
            }

            int rowIdx = 1;
            int no = 1;
            for (Persediaan persediaan : persediaans) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(no++);
                row.createCell(1).setCellValue(persediaan.getBarangSiapJual());
                row.createCell(2).setCellValue(persediaan.getPenjualan());
                row.createCell(3).setCellValue(persediaan.getPembelian());
                row.createCell(4).setCellValue(persediaan.getPersediaanAwal());
                row.createCell(5).setCellValue(persediaan.getPersediaanAkhir());
                row.createCell(6).setCellValue(persediaan.getDate().toString());
            }

            // Menyesuaikan lebar kolom sesuai konten
            for (int col = 0; col < HEADERsPersediaan.length; col++) {
                sheet.autoSizeColumn(col);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    public static ByteArrayInputStream templateToExcel() throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Membuat baris header
            Row headerRow = sheet.createRow(0);
            CellStyle stringCellStyle = workbook.createCellStyle();
            DataFormat dataFormat = workbook.createDataFormat();
            stringCellStyle.setDataFormat(dataFormat.getFormat("@"));

            for (int col = 0; col < HEADERsTemplate.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERsTemplate[col]);
                cell.setCellStyle(stringCellStyle);
            }

            // Menyesuaikan lebar kolom sesuai konten
            for (int col = 0; col < HEADERsTemplate.length; col++) {
                sheet.autoSizeColumn(col);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    public static List<Persediaan> excelToPersediaan(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);

            List<Persediaan> persediaanList = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Persediaan persediaan = new Persediaan();

                for (int cellIdx = 0; cellIdx < 7; cellIdx++) {
                    Cell currentCell = row.getCell(cellIdx, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    switch (cellIdx) {
                        case 1:
                            persediaan.setBarangSiapJual(currentCell.getStringCellValue());
                            break;
                        case 2:
                            persediaan.setPenjualan(currentCell.getStringCellValue());
                            break;
                        case 3:
                            persediaan.setPembelian(currentCell.getStringCellValue());
                            break;
                        case 4:
                            persediaan.setPersediaanAwal(currentCell.getStringCellValue());
                            break;
                        case 5:
                            persediaan.setPersediaanAkhir(currentCell.getStringCellValue());
                            break;
                        case 6:
                            persediaan.setDate(currentCell.getDateCellValue());
                    }
                }
                persediaanList.add(persediaan);
            }

            workbook.close();
            return persediaanList;
        } catch (IOException e) {
            throw new RuntimeException("Fail to parse Excel file: " + e.getMessage());
        }
    }
}
