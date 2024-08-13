package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Customer;
import com.template.eazypos.model.ServiceBarang;
import com.template.eazypos.model.Status;
import com.template.eazypos.model.Teknisi;
import com.template.eazypos.repository.CustomerRepository;
import com.template.eazypos.repository.ServiceRepository;
import com.template.eazypos.repository.TeknisiRepository;
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
import java.util.*;

@Service
public class ExcelLaporanStatus {

    public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERsLAPORANSTATUS = {"NO", "TANDA TERIMA", "NAMA CUSTOMER", "TANGGAL MASUK", "NO FAKTUR", "KELUHAN", "TANGGAL", "TEKNISI", "STATUS", "SOLUSI"};
    private static final String SHEET_NAME = "Laporan Status Service";

    @Autowired
    private TeknisiRepository teknisiRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private static final String SHEET = "sheet1";

    public boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public ByteArrayInputStream laporanStatusToExcel(List<Object[]> statuses) {
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
            for (int col = 0; col < HEADERsLAPORANSTATUS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERsLAPORANSTATUS[col]);
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

            for (Object[] rowData : statuses) {
                Row row = sheet.createRow(rowIdx++);

                Cell cellNo = row.createCell(0);
                cellNo.setCellValue(no);
                cellNo.setCellStyle(dataStyle);

                Cell cellIdTT = row.createCell(1);
                if (rowData.length > 9 && rowData[9] != null) {
                    cellIdTT.setCellValue(rowData[9].toString());
                } else {
                    cellIdTT.setCellValue("");
                }
                cellIdTT.setCellStyle(dataStyle);

                Cell cellCustomerName = row.createCell(2);
                if (rowData.length > 9 && rowData[9] != null) {
                    String cuntomerIdString = rowData[9].toString().trim();
                    Long customerId = null;
                    try {
                        customerId = Long.parseLong(cuntomerIdString);
                    } catch (NumberFormatException e) {
                        customerId = null;
                    }

                    if (customerId != null) {
                        Optional<Customer> customerOpt = customerRepository.findById(customerId);
                        if (customerOpt.isPresent()) {
                            Customer customer = customerOpt.get();
                            String customerName = customer != null ? customer.getNama_customer() : "";
                            cellCustomerName.setCellValue(customerName);
                        } else {
                            cellCustomerName.setCellValue("");
                        }
                    } else {
                        cellCustomerName.setCellValue("");
                    }
                } else {
                    cellCustomerName.setCellValue("");
                }
                cellCustomerName.setCellStyle(dataStyle);

                Cell cellTanggalMasuk = row.createCell(3);
                if (rowData.length > 9 && rowData[9] != null) {
                    Long idTT = Long.parseLong(rowData[9].toString());
                    Optional<ServiceBarang> serviceOpt = serviceRepository.findById(idTT);
                    if (serviceOpt.isPresent()) {
                        ServiceBarang serviceBarang = serviceOpt.get();
                        Date tanggalMasuk = serviceBarang.getTanggalMasuk();
                        if (tanggalMasuk != null) {
                            cellTanggalMasuk.setCellValue(tanggalMasuk);
                            cellTanggalMasuk.setCellStyle(dateStyle);
                        } else {
                            cellTanggalMasuk.setCellValue("");
                            cellTanggalMasuk.setCellStyle(dateStyle);
                        }
                    } else {
                        cellTanggalMasuk.setCellValue("");
                        cellTanggalMasuk.setCellStyle(dateStyle);
                    }
                } else {
                    cellTanggalMasuk.setCellValue("");
                    cellTanggalMasuk.setCellStyle(dateStyle);
                }

                Cell cellNoFaktur = row.createCell(4);
                if (rowData.length > 9 && rowData[9] != null) {
                    Long idTT = Long.parseLong(rowData[9].toString());
                    Optional<String> noFakturOpt = transaksiRepository.findNoFakturByTandaTerima(idTT);
                    String noFaktur = noFakturOpt.orElse("");
                    cellNoFaktur.setCellValue(noFaktur);
                } else {
                    cellNoFaktur.setCellValue("");
                }
                cellNoFaktur.setCellStyle(dataStyle);

                Cell cellKeluhan = row.createCell(5);
                if (rowData.length > 9 && rowData[9] != null) {
                    String serviceIdString = rowData[9].toString().trim();
                    Long serviceId = null;
                    try {
                        serviceId = Long.parseLong(serviceIdString);
                    } catch (NumberFormatException e) {
                        serviceId = null;
                    }

                    if (serviceId != null) {
                        Optional<ServiceBarang> serviceOpt = serviceRepository.findById(serviceId);
                        if (serviceOpt.isPresent()) {
                            ServiceBarang serviceBarang = serviceOpt.get();
                            String serviceKeluhan = serviceBarang != null ? serviceBarang.getKeluhan() : "";
                            cellKeluhan.setCellValue(serviceKeluhan);
                        } else {
                            cellKeluhan.setCellValue("");
                        }
                    } else {
                        cellKeluhan.setCellValue("");
                    }
                } else {
                    cellKeluhan.setCellValue("");
                }
                cellKeluhan.setCellStyle(dataStyle);

                Cell cellTanggal = row.createCell(6);
                if (rowData.length > 6 && rowData[6] instanceof Date) {
                    cellTanggal.setCellValue((Date) rowData[6]);
                    cellTanggal.setCellStyle(dateStyle);
                } else {
                    cellTanggal.setCellValue("");
                }

                Cell cellTeknisiName = row.createCell(7);
                if (rowData.length > 10 && rowData[10] != null) {
                    String teknisiIdString = rowData[10].toString().trim();
                    Long teknisiId = null;
                    try {
                        teknisiId = Long.parseLong(teknisiIdString);
                    } catch (NumberFormatException e) {
                        teknisiId = null;
                    }

                    if (teknisiId != null) {
                        Optional<Teknisi> teknisiOpt = teknisiRepository.findById(teknisiId);
                        if (teknisiOpt.isPresent()) {
                            Teknisi teknisi = teknisiOpt.get();
                            String teknisiName = teknisi != null ? teknisi.getNama() : "";
                            cellTeknisiName.setCellValue(teknisiName);
                        } else {
                            cellTeknisiName.setCellValue("");
                        }
                    } else {
                        cellTeknisiName.setCellValue("");
                    }
                } else {
                    cellTeknisiName.setCellValue("");
                }
                cellTeknisiName.setCellStyle(dataStyle);

                Cell cellStatus = row.createCell(8);
                cellStatus.setCellValue(rowData[5] != null ? rowData[5].toString() : "");
                cellStatus.setCellStyle(dataStyle);

                Cell cellSolusi = row.createCell(9);
                cellSolusi.setCellValue(rowData[4] != null ? rowData[4].toString() : "");
                cellSolusi.setCellStyle(dataStyle);

                no++;
            }
            for (int col = 0; col < HEADERsLAPORANSTATUS.length; col++) {
                sheet.autoSizeColumn(col);
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to excel file: " + e.getMessage());
        }
    }

    public static List<Status> excelToLaporanStatus(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<Status> statusList = new ArrayList<>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                Status status = new Status();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 6:
                            status.setTanggal(currentCell.getDateCellValue());
                            break;
                        case 8:
                            status.setStatus(currentCell.getStringCellValue());
                            break;
                        case 9:
                            status.setSolusi(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                statusList.add(status);
            }
            workbook.close();

            return statusList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse excel file: " + e.getMessage());
        }
    }
}
