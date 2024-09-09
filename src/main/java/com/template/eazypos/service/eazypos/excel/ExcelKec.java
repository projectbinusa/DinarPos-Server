package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.KabKot;
import com.template.eazypos.model.Kec;
import com.template.eazypos.model.Prov;
import com.template.eazypos.repository.KabKotRepository;
import com.template.eazypos.repository.KecRepository;
import com.template.eazypos.repository.ProvRepository;
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
import java.io.InputStream;
import java.util.*;

@Service
public class ExcelKec {
    @Autowired
    private KecRepository kecRepository;

    @Autowired
    private KabKotRepository kabKotRepository;

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    static String[] HEADERsTemplate = {"NO", "NAMA PROVINSI" , "NAMA KABUPATEN / KOTA" , "NAMA KECAMATAN" };

    static String SHEET = "Sheet1";

    @Autowired
    private ProvRepository provRepository;
    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static ByteArrayInputStream templateToExcel() throws IOException {
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

    public List<Kec> excelToKec(InputStream is) {
        Set<String> existingCodes = new HashSet<>(); // Untuk melacak kode yang sudah ada
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Kec> kabkotList = new ArrayList<Kec>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Kec kabkot = new Kec();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 3:
                            kabkot.setNama_kec(currentCell.getStringCellValue());
                            break;
                        case 2:
                            KabKot kabKot = kabKotRepository.findByNama(currentCell.getStringCellValue()).orElseThrow(() -> new NotFoundException("Nama KabKot Not Found"));
                            kabkot.setKabKot(kabKot);
                            break;
                        case 1:
                            Prov prov = provRepository.findByNama(currentCell.getStringCellValue()).orElseThrow(() -> new NotFoundException("Nama Prov Not Found"));
                            kabkot.setProv(prov);
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                kabkotList.add(kabkot);
            }
            workbook.close();

            return kabkotList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
    public void saveBarang(MultipartFile file) {
        try {
            List<Kec> kabKot = excelToKec(file.getInputStream());
            kecRepository.saveAll(kabKot);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }
    public ByteArrayInputStream templateKec() throws IOException {
        ByteArrayInputStream in = templateToExcel();
        return in;
    }
}
