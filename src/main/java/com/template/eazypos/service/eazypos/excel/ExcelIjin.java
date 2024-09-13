package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Ijin;
import com.template.eazypos.repository.IjinRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExcelIjin {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"NO", "DARI TANGGAL", "SAMPAI TANGGAL", "NAMA MARKETTING", "KETERANGAN"};

    static String SHEET = "Sheet1";

    // Memeriksa apakah file memiliki format Excel yang valid
    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    // Mengonversi daftar ijin menjadi file Excel dan mengembalikannya sebagai ByteArrayInputStream
    public static ByteArrayInputStream ijinToExcel(List<Ijin> ijins) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);

            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
                sheet.autoSizeColumn(col);
            }


            int rowIdx = 1;
            int no = 0;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (Ijin ijin : ijins) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(no);
                row.createCell(1).setCellValue(dateFormat.format(ijin.getTgl_a()));
                if (ijin.getTgl_b().equals(null)){
                row.createCell(2).setCellValue(dateFormat.format(ijin.getTgl_a()));
                }else {
                 row.createCell(2).setCellValue(dateFormat.format(ijin.getTgl_b()));
                }
                row.createCell(3).setCellValue(ijin.getSalesman().getNamaSalesman());
                row.createCell(4).setCellValue(ijin.getKet());
                no++;
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
