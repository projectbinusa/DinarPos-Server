package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Suplier;
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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelSuplier {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERsSuplier = {"NO", "KODE SUPLIER", "NAMA SUPLIER", " ALAMAT", "NO TELEPON", "KETERANGAN"};
    static String[] HEADERsTemplate = {"NO", "KODE SUPLIER", "NAMA SUPLIER", " ALAMAT", "NO TELEPON", "KETERANGAN"};

    static String SHEET = "Sheet1";

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }


    public static ByteArrayInputStream suplierToExcel(List<Suplier> supliers) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);

            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERsSuplier.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERsSuplier[col]);
            }


            int rowIdx = 1;
            int no = 0;
            for (Suplier suplier : supliers) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(no);
                row.createCell(1).setCellValue(suplier.getKodeSuplier());
                row.createCell(2).setCellValue(suplier.getNamaSuplier());
                row.createCell(3).setCellValue(suplier.getAlamatSuplier());
                row.createCell(4).setCellValue(suplier.getNoTelpSuplier());
                row.createCell(5).setCellValue(suplier.getKeterangan());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch(IOException e){
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
    public static ByteArrayInputStream templateToExcel(List<Suplier> supliers) throws IOException {
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
    public static List<Suplier> excelToSuplier(InputStream is){

        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Suplier> suplierList = new ArrayList<Suplier>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Suplier suplier = new Suplier();

                int cellIdx = 1;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 1:
                            suplier.setKodeSuplier(currentCell.getStringCellValue());
                            break;
                        case 2:
                            suplier.setNamaSuplier(currentCell.getStringCellValue());
                            break;
                        case 3:
                            suplier.setNoTelpSuplier(currentCell.getStringCellValue());
                            break;
                        case 4:
                            suplier.setAlamatSuplier(currentCell.getStringCellValue());
                            break;
                        case 5:
                            suplier.setKeterangan(currentCell.getStringCellValue());
                            break;

                        default:
                            break;
                    }
                    suplier.setDelFlag(1);
                    suplierList.add(suplier);
                    cellIdx++;

                }
            }
            workbook.close();
            return suplierList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }

    }

}

