package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Barang;
import com.template.eazypos.model.Suplier;
import com.template.eazypos.repository.BarangRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelBarang {
    @Autowired
    private BarangRepository barangRepository;

    @Transactional
    public List<Barang> importBarangFromExcel(MultipartFile file) throws IOException {
        List<Barang> barangList = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) continue; // Skip header row
                Barang barang = new Barang();

                barang.setBarcodeBarang(row.getCell(1).getStringCellValue());
                barang.setNamaBarang(row.getCell(2).getStringCellValue());
                barang.setUnit(row.getCell(3).getStringCellValue());
                barang.setHargaBeli(row.getCell(4).getStringCellValue());
                barang.setHargaBarang(row.getCell(5).getStringCellValue());
                barang.setDelFlag(1); // Assuming default value

                barangList.add(barang);
            }
        }
        return barangRepository.saveAll(barangList);
    }

    public byte[] exportToExcel() throws IOException {
        List<Barang> barangs = barangRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Barang");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("NO");
            headerRow.createCell(1).setCellValue("Barcode");
            headerRow.createCell(2).setCellValue("Nama Barang");
            headerRow.createCell(3).setCellValue("Unit");
            headerRow.createCell(4).setCellValue("Harga Beli");
            headerRow.createCell(5).setCellValue("Harga Jual");
            headerRow.createCell(6).setCellValue("Jumlah Stok");

            int rowCount = 1;
            int no =0;
            for (Barang barang : barangs) {
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(no++);
                row.createCell(1).setCellValue(barang.getBarcodeBarang());
                row.createCell(2).setCellValue(barang.getNamaBarang());
                row.createCell(3).setCellValue(barang.getUnit());
                row.createCell(4).setCellValue(barang.getHargaBeli());
                row.createCell(5).setCellValue(barang.getHargaBarang());
                row.createCell(6).setCellValue(barang.getJumlahStok());

            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
    public byte[] templateToExcel() throws IOException {

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Template Barang");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("NO");
            headerRow.createCell(1).setCellValue("Barcode");
            headerRow.createCell(2).setCellValue("Nama Barang");
            headerRow.createCell(3).setCellValue("Unit");
            headerRow.createCell(4).setCellValue("Harga Beli");
            headerRow.createCell(5).setCellValue("Harga Jual");


            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
}
