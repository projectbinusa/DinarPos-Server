package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.exception.BadRequestException;
import com.template.eazypos.model.Suplier;
import com.template.eazypos.repository.SuplierRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExcelSuplier {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERsSuplier = {"NO", "KODE SUPLIER", "NAMA SUPLIER", "ALAMAT SUPLIER", "NO TELEPON", "KETERANGAN"};
    static String[] HEADERsTemplate = {"NO", "KODE SUPLIER", "NAMA SUPLIER", "ALAMAT SUPLIER", "NO TELEPON", "KETERANGAN"};
    static String SHEET = "Sheet1";
    private SuplierRepository suplierRepository;

    // Method untuk memeriksa apakah file memiliki format Excel yang sesuai
    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    // Method untuk mengkonversi daftar Suplier menjadi format Excel
    public static ByteArrayInputStream suplierToExcel(List<Suplier> supliers) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);

            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERsSuplier.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERsSuplier[col]);
                sheet.autoSizeColumn(col);
            }

            int rowIdx = 1;
            int no = 1;
            for (Suplier suplier : supliers) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(no++);
                row.createCell(1).setCellValue(suplier.getKodeSuplier());
                row.createCell(2).setCellValue(suplier.getNamaSuplier());
                row.createCell(3).setCellValue(suplier.getAlamatSuplier());
                row.createCell(4).setCellValue(suplier.getNoTelpSuplier());
                row.createCell(5).setCellValue(suplier.getKeterangan());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    // Method untuk menghasilkan template Excel untuk data Suplier
    public static ByteArrayInputStream templateToExcel() throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);

            Row headerRow = sheet.createRow(0);
            CellStyle stringCellStyle = workbook.createCellStyle();
            DataFormat dataFormat = workbook.createDataFormat();
            stringCellStyle.setDataFormat(dataFormat.getFormat("@")); // Set format to text

            for (int col = 0; col < HEADERsTemplate.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERsTemplate[col]);
                cell.setCellStyle(stringCellStyle); // Set the cell style to string type
                sheet.autoSizeColumn(col);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    // Method untuk mengkonversi data Excel menjadi daftar Suplier
    public static List<Suplier> excelToSuplier(InputStream is) {
        Set<String> existingCodes = new HashSet<>(); // Untuk melacak kode yang sudah ada
        List<Suplier> suplierList = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet(SHEET);

            for (Row row : sheet) {
                // Lewati baris pertama (header)
                if (row.getRowNum() == 0) continue;

                Suplier suplier = new Suplier();

                String kodeSuplier = "";
                boolean isValid = true;

                for (int cellIdx = 0; cellIdx < 6; cellIdx++) {
                    Cell currentCell = row.getCell(cellIdx, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    switch (cellIdx) {
                        case 1:
                            kodeSuplier = currentCell.getStringCellValue();
//                            if (existingCodes.contains(kodeSuplier) || !suplierRepository.findByCode(kodeSuplier).isEmpty()) {
//                                isValid = false; // Kode sudah ada, jadi abaikan baris ini
//                            } else {
//                                suplier.setKodeSuplier(kodeSuplier);
//                                existingCodes.add(kodeSuplier); // Tambahkan kode ke dalam Set
//                            }
                            break;
                        case 2:
                            // Nama Suplier
                            suplier.setNamaSuplier(currentCell.getStringCellValue());
                            suplier.setDelFlag(1);
                            break;
                        case 4:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                suplier.setNoTelpSuplier(String.valueOf(currentCell.getNumericCellValue()));
                            } else {
                                suplier.setNoTelpSuplier(currentCell.getStringCellValue());
                            }
                            break;
                        case 3:
                            suplier.setAlamatSuplier(currentCell.getStringCellValue());
                            break;
                        case 5:
                            suplier.setKeterangan(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                }

                if (isValid) {
                    suplierList.add(suplier);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Fail to parse Excel file: " + e.getMessage());
        }

        return suplierList;
    }
}
