package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Barang;
import com.template.eazypos.model.Suplier;
import com.template.eazypos.repository.BarangRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ExcelBarang {

    @Autowired
    private BarangRepository barangRepository;
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERsBarang = {"NO", "BARCODE BARANG", "NAMA BARANG", " UNIT BARANG", "HARGA BELI", "HARGA JUAL", "JUMLAH STOK"};
    static String[] HEADERsTemplate = {"NO", "BARCODE BARANG", "NAMA BARANG", " UNIT BARANG", "HARGA BELI", "HARGA JUAL"};

    static String SHEET = "Sheet1";

    // Memeriksa apakah file memiliki format Excel yang valid
    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    // Mengonversi daftar barang menjadi file Excel dan mengembalikannya sebagai ByteArrayInputStream
    public static ByteArrayInputStream barangToExcel(List<Barang> barangs) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);

            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERsBarang.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERsBarang[col]);
                sheet.autoSizeColumn(col);
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
                no++;
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    // Mengonversi template Excel untuk daftar barang
    public static ByteArrayInputStream templateToExcel(List<Barang> barangs) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERsTemplate.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERsTemplate[col]);
                sheet.autoSizeColumn(col);
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    // Mengonversi InputStream dari file Excel menjadi daftar barang
    public  List<Barang> excelToBarang(InputStream is) {
        Set<String> existingCodes = new HashSet<>(); // Untuk melacak kode yang sudah ada
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
                String barcodeBarang = "";
                boolean isValid = true;

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 1:
                            barcodeBarang = currentCell.getStringCellValue();
                            if (existingCodes.contains(barcodeBarang) || !barangRepository.findByBarcodeBarang(barcodeBarang).isEmpty()) {
                                isValid = false; // Kode sudah ada, jadi abaikan baris ini
                            } else {
                                barang.setBarcodeBarang(barcodeBarang);
                                existingCodes.add(barcodeBarang); // Tambahkan kode ke dalam Set
                            }

                            break;
                        case 2:
                            barang.setNamaBarang(currentCell.getStringCellValue());
                            break;
                        case 3:
                            barang.setUnit(currentCell.getStringCellValue());
                            barang.setIdSuplier(0L);
                            barang.setDelFlag(1);
                            break;
                        case 4:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                barang.setHargaBeli(String.valueOf(currentCell.getNumericCellValue()));
                            } else {
                                barang.setHargaBeli(currentCell.getStringCellValue());
                            }
                            break;
                        case 5:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                barang.setHargaBarang(String.valueOf(currentCell.getNumericCellValue()));
                            } else {
                                barang.setHargaBarang(currentCell.getStringCellValue());
                            }
                            break;

                        default:
                            break;
                    }
                    cellIdx++;
                }
                barangList.add(barang);
            }
            workbook.close();

            return barangList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
    public void saveBarang(MultipartFile file) {
        try {
            List<Barang> barangList = excelToBarang(file.getInputStream());
            barangRepository.saveAll(barangList);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

}
