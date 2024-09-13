package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.*;
import com.template.eazypos.repository.TransaksiRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ExcelLaporanServiceAll {
    public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERsLAPORANSERVICE = {"NO", "TANDA TERIMA", "NO FAKTUR", "STATUS", "TAKEN", "NAMA CUSTOMER", "ALAMAT", "NO TELP", "TEKNISI", "PRODUK", "KELUHAN", "PENERIMA", "TANGGAL MASUK", "TANGGAL JADI", "TANGGAL AMBIL", "BIAYA SPAREPART", "BIAYA SERVICE", "TOTAL"};
    private static final String SHEET_NAME = "Laporan Service";

    @Autowired
    private TransaksiRepository transaksiRepository;

    private static final String SHEET = "sheet1";

    public boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public ByteArrayInputStream laporanServiceToExcel(List<ServiceBarang> serviceBarangs) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header Style
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Create Header Row
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERsLAPORANSERVICE.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERsLAPORANSERVICE[col]);
                cell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(col);
            }

            // Data Style
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            dataStyle.setAlignment(HorizontalAlignment.LEFT);
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Date Style
            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.cloneStyleFrom(dataStyle);
            CreationHelper createHelper = workbook.getCreationHelper();
            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

            int rowIdx = 1;
            int no = 1;
            double totalBiayaSparepart = 0;
            double totalBiayaService = 0;
            double totalBiaya = 0;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (ServiceBarang serviceBarang : serviceBarangs) {
                Row row = sheet.createRow(rowIdx++);
                Cell cellNo = row.createCell(0);
                cellNo.setCellValue(no);
                cellNo.setCellStyle(dataStyle);

                Cell cellIdTT = row.createCell(1);
                cellIdTT.setCellValue(serviceBarang.getIdTT());
                cellIdTT.setCellStyle(dataStyle);

                Optional<String> noFakturOpt = transaksiRepository.findNoFakturByTandaTerima(serviceBarang.getIdTT());
                String noFaktur = noFakturOpt.orElse("-");
                Cell cellNoFaktur = row.createCell(2);
                cellNoFaktur.setCellValue(noFaktur);
                cellNoFaktur.setCellStyle(dataStyle);

                Cell cellStatusEnd = row.createCell(3);
                cellStatusEnd.setCellValue(serviceBarang.getStatusEnd());
                cellStatusEnd.setCellStyle(dataStyle);

                Cell cellTaken = row.createCell(4);
                cellTaken.setCellValue(serviceBarang.getTaken());
                cellTaken.setCellStyle(dataStyle);

                // Mendapatkan nama customer dari objek Customer
                String customerName = serviceBarang.getCustomer() != null ? serviceBarang.getCustomer().getNama_customer() : "Unknown";
                Cell cellCustomerName = row.createCell(5);
                cellCustomerName.setCellValue(customerName);
                cellCustomerName.setCellStyle(dataStyle);

                Cell cellAlamat = row.createCell(6);
                cellAlamat.setCellValue(serviceBarang.getAlamat());
                cellAlamat.setCellStyle(dataStyle);

                // Mendapatkan nomor telepon dari objek Customer
                String customerTelp = serviceBarang.getCustomer() != null ? serviceBarang.getCustomer().getTelp() : "Unknown";
                Cell cellCustomerTelp = row.createCell(7);
                cellCustomerTelp.setCellValue(customerTelp);
                cellCustomerTelp.setCellStyle(dataStyle);

                // Mendapatkan nama teknisi dari objek Teknisi
                String teknisiName = serviceBarang.getTeknisi() != null ? serviceBarang.getTeknisi().getNama() : "Unknown";
                Cell cellTeknisiName = row.createCell(8);
                cellTeknisiName.setCellValue(teknisiName);
                cellTeknisiName.setCellStyle(dataStyle);

                Cell cellProduk = row.createCell(9);
                cellProduk.setCellValue(serviceBarang.getProduk());
                cellProduk.setCellStyle(dataStyle);

                Cell cellKeluhan = row.createCell(10);
                cellKeluhan.setCellValue(serviceBarang.getKeluhan());
                cellKeluhan.setCellStyle(dataStyle);

                Cell cellPenerima = row.createCell(11);
                cellPenerima.setCellValue(serviceBarang.getPenerima());
                cellPenerima.setCellStyle(dataStyle);

                Cell cellTanggalMasuk = row.createCell(12);
                if (serviceBarang.getTanggalMasuk() != null) {
                    cellTanggalMasuk.setCellValue(dateFormat.format(serviceBarang.getTanggalMasuk()));
                    cellTanggalMasuk.setCellStyle(dateStyle);
                }

                Cell cellTanggalJadi = row.createCell(13);
                if (serviceBarang.getTanggalJadi() != null) {
                    cellTanggalJadi.setCellValue(dateFormat.format(serviceBarang.getTanggalJadi()));
                    cellTanggalJadi.setCellStyle(dateStyle);
                }

                Cell cellTanggalAmbil = row.createCell(14);
                if (serviceBarang.getTanggalAmbil() != null) {
                    cellTanggalAmbil.setCellValue(dateFormat.format(serviceBarang.getTanggalAmbil()));
                    cellTanggalAmbil.setCellStyle(dateStyle);
                }

                Cell cellBiayaSparepart = row.createCell(15);
                double biayaSparepart = serviceBarang.getBiayaSparepart();
                cellBiayaSparepart.setCellValue(biayaSparepart);
                cellBiayaSparepart.setCellStyle(dataStyle);
                totalBiayaSparepart += biayaSparepart;

                Cell cellBiayaService = row.createCell(16);
                double biayaService = serviceBarang.getBiayaService();
                cellBiayaService.setCellValue(biayaService);
                cellBiayaService.setCellStyle(dataStyle);
                totalBiayaService += biayaService;

                Cell cellTotal = row.createCell(17);
                double total = serviceBarang.getTotal();
                cellTotal.setCellValue(total);
                cellTotal.setCellStyle(dataStyle);
                totalBiaya += total;

                no++;
            }

            // Add totals row
            Row totalRow = sheet.createRow(rowIdx);
            CellStyle totalStyle = workbook.createCellStyle();
            totalStyle.cloneStyleFrom(dataStyle);
            totalStyle.setFont(headerFont);
            totalStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
            totalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            totalStyle.setBorderBottom(BorderStyle.THIN);
            totalStyle.setBorderTop(BorderStyle.THIN);
            totalStyle.setBorderLeft(BorderStyle.THIN);
            totalStyle.setBorderRight(BorderStyle.THIN);
            totalStyle.setAlignment(HorizontalAlignment.CENTER);
            totalStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            Cell cellTotalBiayaSparepart = totalRow.createCell(15);
            cellTotalBiayaSparepart.setCellValue(totalBiayaSparepart);
            cellTotalBiayaSparepart.setCellStyle(totalStyle);

            Cell cellTotalBiayaService = totalRow.createCell(16);
            cellTotalBiayaService.setCellValue(totalBiayaService);
            cellTotalBiayaService.setCellStyle(totalStyle);

            Cell cellTotalBiaya = totalRow.createCell(17);
            cellTotalBiaya.setCellValue(totalBiaya);
            cellTotalBiaya.setCellStyle(totalStyle);

            // Auto-size all columns
            for (int col = 0; col < HEADERsLAPORANSERVICE.length; col++) {
                sheet.autoSizeColumn(col);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static List<ServiceBarang> excelToLaporanService(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<ServiceBarang> serviceBarangList = new ArrayList<>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                ServiceBarang serviceBarang = new ServiceBarang();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 1:
                            // Mengambil ID TANDA TERIMA (idTT) dari Excel
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                serviceBarang.setIdTT((long) currentCell.getNumericCellValue());
                            } else if (currentCell.getCellType() == CellType.STRING) {
                                try {
                                    serviceBarang.setIdTT(Long.parseLong(currentCell.getStringCellValue()));
                                } catch (NumberFormatException e) {
                                    serviceBarang.setIdTT(null);
                                }
                            }
                            break;
                        case 3:
                            serviceBarang.setStatusEnd(currentCell.getStringCellValue());
                            break;
                        case 4:
                            serviceBarang.setTaken(currentCell.getStringCellValue());
                            break;
                        case 6:
                            serviceBarang.setAlamat(currentCell.getStringCellValue());
                            break;
                        case 9:
                            serviceBarang.setProduk(currentCell.getStringCellValue());
                            break;
                        case 10:
                            serviceBarang.setKeluhan(currentCell.getStringCellValue());
                            break;
                        case 11:
                            serviceBarang.setPenerima(currentCell.getStringCellValue());
                            break;
                        case 12:
                            serviceBarang.setTanggalMasuk(currentCell.getDateCellValue());
                            break;
                        case 13:
                            serviceBarang.setTanggalJadi(currentCell.getDateCellValue());
                            break;
                        case 14:
                            serviceBarang.setTanggalAmbil(currentCell.getDateCellValue());
                            break;
                        case 15:
                            serviceBarang.setBiayaSparepart((int) currentCell.getNumericCellValue());
                            break;
                        case 16:
                            serviceBarang.setBiayaService((int) currentCell.getNumericCellValue());
                            break;
                        case 17:
                            serviceBarang.setTotal((int) currentCell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                serviceBarangList.add(serviceBarang);
            }
            workbook.close();

            return serviceBarangList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
