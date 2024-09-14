package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Customer;
import com.template.eazypos.model.CustomerCP;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ExcelCustomerCP {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    static String[] HEADERs = {"NO", "TIMESTAMP" , "MARKETING", "NAMA CUSTOMER","INSTANSI","JABATAN", "NO HANDPHONE" };

    static String SHEET = "Sheet1";

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    // Mengonversi daftar customer menjadi file Excel dan mengembalikannya sebagai ByteArrayInputStream
    public static ByteArrayInputStream customerToExcel(List<CustomerCP> customers) throws IOException {
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
            for (CustomerCP customer : customers) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(no);
                row.createCell(1).setCellValue(dateFormat.format(customer.getCreated_date()));
                row.createCell(2).setCellValue(customer.getSalesman().getNamaSalesman());
                row.createCell(3).setCellValue(customer.getCustomer().getNama_customer());
                row.createCell(4).setCellValue(customer.getCustomer().getJenis());
                row.createCell(5).setCellValue(customer.getJabatan());
                row.createCell(6).setCellValue(customer.getNo_hp());
                no++;
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
