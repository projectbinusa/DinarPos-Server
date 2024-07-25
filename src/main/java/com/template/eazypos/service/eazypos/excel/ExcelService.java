package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.repository.PoinHistoryRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
@Service
public class ExcelService {
    @Autowired
    private PoinHistoryRepository poinHistoryRepository;

    public void generateReport(String bulanAwal, String bulanAkhir, HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("LaporanServicePerTim");

        // Styles
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle columnStyle = createColumnStyle(workbook);
        CellStyle numberStyle = createNumberStyle(workbook);
        CellStyle yellowStyle = createYellowStyle(workbook);
        CellStyle greenStyle = createGreenStyle(workbook);
        CellStyle blueStyle = createBlueStyle(workbook);

        int rowNum = 0;

        // Title
        Row titleRow = sheet.createRow(rowNum++);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("LAPORAN SERVICE TIM " + bulanAwal + " s.d " + bulanAkhir);
        titleCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));

        // Column Headers
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("Tanggal");
        headerRow.getCell(0).setCellStyle(columnStyle);
        headerRow.createCell(1).setCellValue("PC");
        headerRow.getCell(1).setCellStyle(blueStyle);
        headerRow.createCell(2).setCellValue("Elektro");
        headerRow.getCell(2).setCellStyle(yellowStyle);

        // Populate Data
        for (String bulan : getMonthRange(bulanAwal, bulanAkhir)) {
            rowNum = populateMonthlyData(sheet, rowNum, bulan, columnStyle, numberStyle, yellowStyle, blueStyle, greenStyle);
        }

        // Add Total Nominal and Percentage
        addTotalNominalAndPercentage(sheet, rowNum, greenStyle, numberStyle);

        // Write to response
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=LaporanServicePerTim.xlsx");

        try (OutputStream out = response.getOutputStream()) {
            workbook.write(out);
        }

        workbook.close();
    }

    private CellStyle createHeaderStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle createColumnStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        BorderStyle border = BorderStyle.THIN;
        style.setBorderTop(border);
        style.setBorderRight(border);
        style.setBorderBottom(border);
        style.setBorderLeft(border);
        return style;
    }

    private CellStyle createNumberStyle(XSSFWorkbook workbook) {
        CellStyle style = createColumnStyle(workbook);
        style.setAlignment(HorizontalAlignment.RIGHT);
        return style;
    }

    private CellStyle createYellowStyle(XSSFWorkbook workbook) {
        CellStyle style = createColumnStyle(workbook);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private CellStyle createGreenStyle(XSSFWorkbook workbook) {
        CellStyle style = createNumberStyle(workbook);
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private CellStyle createBlueStyle(XSSFWorkbook workbook) {
        CellStyle style = createColumnStyle(workbook);
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private String[] getMonthRange(String bulanAwal, String bulanAkhir) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        try {
            start.setTime(sdf.parse(bulanAwal));
            end.setTime(sdf.parse(bulanAkhir));
        } catch (Exception e) {
            e.printStackTrace();
        }

        int numOfMonths = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12 + end.get(Calendar.MONTH) - start.get(Calendar.MONTH) + 1;
        String[] months = new String[numOfMonths];
        for (int i = 0; i < numOfMonths; i++) {
            months[i] = sdf.format(start.getTime());
            start.add(Calendar.MONTH, 1);
        }
        return months;
    }

    private int populateMonthlyData(XSSFSheet sheet, int rowNum, String bulan, CellStyle columnStyle, CellStyle numberStyle, CellStyle yellowStyle, CellStyle blueStyle, CellStyle greenStyle) {
        // Add month header
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(bulan);
        row.getCell(0).setCellStyle(columnStyle);

        // Add weekly headers and data
        int[] mingguStart = {1, 11, 18, 25};
        int[] mingguEnd = {10, 17, 24, 31};
        String[] mingguLabels = {"MG I", "MG II", "MG III", "MG IV"};

        // Variables to store total PC and Elektro
        int totalPC = 0;
        int totalElektro = 0;

        for (int i = 0; i < mingguStart.length; i++) {
            row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(mingguLabels[i]);
            row.getCell(0).setCellStyle(yellowStyle);

            int ttlPC = 0;
            int ttlElektro = 0;

            for (int j = mingguStart[i]; j <= mingguEnd[i]; j++) {
                if (j <= 31) {
                    String tanggal = bulan + "-" + (j < 10 ? "0" + j : j);
                    Date date = parseDate(tanggal);
                    int nominalPC = getNominalPC(date);
                    int nominalElektro = getNominalElektro(date);

                    ttlPC += nominalPC;
                    ttlElektro += nominalElektro;

                    row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(tanggal);
                    row.getCell(0).setCellStyle(columnStyle);
                    row.createCell(1).setCellValue(nominalPC);
                    row.createCell(2).setCellValue(nominalElektro);
                    row.getCell(1).setCellStyle(numberStyle);
                    row.getCell(2).setCellStyle(numberStyle);
                }
            }

            totalPC += ttlPC;
            totalElektro += ttlElektro;
        }

        // Add totals and percentages for the month
        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Total " + bulan + ":");
        row.getCell(0).setCellStyle(greenStyle);
        row.createCell(1).setCellValue(totalPC);
        row.createCell(2).setCellValue(totalElektro);
        row.getCell(1).setCellStyle(numberStyle);
        row.getCell(2).setCellStyle(numberStyle);

        double target = 90000000;
        double percentagePC = ((double) totalPC / target) * 100;
        double percentageElektro = ((double) totalElektro / target) * 100;

        // Add target rows
        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Target PC:");
        row.getCell(0).setCellStyle(greenStyle);
        row.createCell(1).setCellValue(target);
        row.getCell(1).setCellStyle(numberStyle);

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Target Elektro:");
        row.getCell(0).setCellStyle(greenStyle);
        row.createCell(1).setCellValue(target);
        row.getCell(1).setCellStyle(numberStyle);

        // Add percentages
        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Persentase PC " + bulan + ":");
        row.getCell(0).setCellStyle(greenStyle);
        row.createCell(1).setCellValue(String.format("%.2f%%", percentagePC));
        row.getCell(1).setCellStyle(numberStyle);

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Persentase Elektro " + bulan + ":");
        row.getCell(0).setCellStyle(greenStyle);
        row.createCell(1).setCellValue(String.format("%.2f%%", percentageElektro));
        row.getCell(1).setCellStyle(numberStyle);

        // Return rowNum to the next available row
        return rowNum;
    }

    private void addTotalNominalAndPercentage(XSSFSheet sheet, int rowNum, CellStyle greenStyle, CellStyle numberStyle) {
        // Add a row for the total
        Row totalRow = sheet.createRow(rowNum++);
        totalRow.createCell(0).setCellValue("Total Nominal:");
        totalRow.getCell(0).setCellStyle(greenStyle);

        // Assuming totalPC and totalElektro are the summed totals of all PC and Elektro values
        int totalPC = 0; // Replace with the actual total if available
        int totalElektro = 0; // Replace with the actual total if available

        totalRow.createCell(1).setCellValue(totalPC);
        totalRow.createCell(2).setCellValue(totalElektro);
        totalRow.getCell(1).setCellStyle(numberStyle);
        totalRow.getCell(2).setCellStyle(numberStyle);

        // Calculate the percentage
        double totalTarget = 90000;
        double totalAchieved = totalPC + totalElektro;
        double percentage = (totalAchieved / totalTarget) * 100;

        // Add a row for the percentage
        Row percentageRow = sheet.createRow(rowNum++);
        percentageRow.createCell(0).setCellValue("Persentase Pencapaian:");
        percentageRow.getCell(0).setCellStyle(greenStyle);

        percentageRow.createCell(1).setCellValue(percentage + "%");
        percentageRow.getCell(1).setCellStyle(numberStyle);
    }

    private Date parseDate(String tanggal) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(tanggal);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Integer getNominalPC(Date tanggal) {
        Integer nominal = poinHistoryRepository.findNominalByBagianAndTanggal("PC", tanggal);
        return nominal != null ? nominal : 0;
    }

    private Integer getNominalElektro(Date tanggal) {
        Integer nominal = poinHistoryRepository.findNominalByBagianAndTanggal("Elektro", tanggal);
        return nominal != null ? nominal : 0;
    }
}
