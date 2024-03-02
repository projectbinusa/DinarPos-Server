package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Suplier;
import com.template.eazypos.repository.SuplierRepository;
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
public class ExcelSuplier {
    @Autowired
    private SuplierRepository suplierRepository;



    @Transactional
    public void importSuplierFromExcel(MultipartFile file) throws IOException {
        List<Suplier> suplierList = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) continue; // Skip header row
                Suplier suplier = new Suplier();

                suplier.setKodeSuplier(row.getCell(1).getStringCellValue());
                suplier.setNamaSuplier(row.getCell(2).getStringCellValue());
                suplier.setAlamatSuplier(row.getCell(3).getStringCellValue());
                suplier.setNoTelpSuplier(row.getCell(4).getStringCellValue());
                suplier.setKeterangan(row.getCell(5).getStringCellValue());
                suplier.setDelFlag(1); // Assuming default value

                suplierList.add(suplier);
            }
        }

        // Simpan semua suplier ke dalam database
        suplierRepository.saveAll(suplierList);
    }
    public byte[] exportSuplierToExcel() throws IOException {
        List<Suplier> supliers = suplierRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Suplier");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("NO");
            headerRow.createCell(1).setCellValue("Kode Suplier");
            headerRow.createCell(2).setCellValue("Nama Suplier");
            headerRow.createCell(3).setCellValue("Alamat Suplier");
            headerRow.createCell(4).setCellValue("No. Telepon");
            headerRow.createCell(5).setCellValue("Keterangan");

            int rowCount = 1;
            int no =0;
            for (Suplier suplier : supliers) {
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(no++);
                row.createCell(1).setCellValue(suplier.getKodeSuplier());
                row.createCell(2).setCellValue(suplier.getNamaSuplier());
                row.createCell(3).setCellValue(suplier.getAlamatSuplier());
                row.createCell(4).setCellValue(suplier.getNoTelpSuplier());
                row.createCell(5).setCellValue(suplier.getKeterangan());

            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
    public byte[] templateSuplierToExcel() throws IOException {

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Template Suplier");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("NO");
            headerRow.createCell(1).setCellValue("Kode Suplier");
            headerRow.createCell(2).setCellValue("Nama Suplier");
            headerRow.createCell(3).setCellValue("Alamat Suplier");
            headerRow.createCell(4).setCellValue("No. Telepon");
            headerRow.createCell(5).setCellValue("Keterangan");


            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

}

