package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Barang;
import com.template.eazypos.model.Suplier;
import com.template.eazypos.repository.BarangRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ExcelBarang {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERsBarang = {"NO", "BARCODE BARANG", "NAMA BARANG", " UNIT BARANG", "HARGA BELI", "HARGA JUAL", "JUMLAH STOK"};
    static String[] HEADERsTemplate = {"NO", "BARCODE BARANG", "NAMA BARANG", " UNIT BARANG", "HARGA BELI", "HARGA JUAL"};

    static String SHEET = "Sheet1";

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }


    public static ByteArrayInputStream barangToExcel(List<Barang> barangs) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);

            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERsBarang.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERsBarang[col]);
            }


            int rowIdx = 1;
            int no = 0;
            for (Barang barang : barangs) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(no);
                row.createCell(1).setCellValue(barang.getBarcodeBarang());
                row.createCell(2).setCellValue(barang.getNamaBarang());
                row.createCell(3).setCellValue(barang.getUnit());
                row.createCell(4).setCellValue(barang.getHargaBeli());
                row.createCell(5).setCellValue(barang.getHargaBarang());
                row.createCell(6).setCellValue(barang.getJumlahStok());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch(IOException e){
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
    public static ByteArrayInputStream templateToExcel(List<Barang> barangs) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERsTemplate.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERsTemplate[col]);
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch(IOException e){
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
    public static List<Barang> excelToBarang(InputStream is){

        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Barang> barangList = new ArrayList<Barang>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Barang barang = new Barang();

                int cellIdx = 1;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 1:
                            barang.setBarcodeBarang(currentCell.getStringCellValue());
                            break;
                        case 2:
                            barang.setNamaBarang(currentCell.getStringCellValue());
                            break;
                        case 3:
                            barang.setUnit(currentCell.getStringCellValue());
                            break;
                        case 4:
                            barang.setHargaBeli(currentCell.getStringCellValue());
                            break;
                        case 5:
                            barang.setHargaBarang(currentCell.getStringCellValue());
                            break;

                        default:
                            break;
                    }
                    barang.setIdSuplier(0L);
                    barang.setDelFlag(1);
                    barangList.add(barang);
                    cellIdx++;

                }
            }
            workbook.close();
            return barangList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }

    }
}
