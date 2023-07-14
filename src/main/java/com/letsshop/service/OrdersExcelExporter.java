package com.letsshop.service;

import com.letsshop.dto.Orders;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static org.apache.poi.ss.util.CellUtil.createCell;

public class OrdersExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Orders> ordersList;

    public OrdersExcelExporter(List<Orders> ordersList){
        this.ordersList = ordersList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Orders");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Order Id", style);
        createCell(row, 1, "Product Name", style);
        createCell(row, 2, "Price", style);
        createCell(row, 3, "Order Date", style);


    }
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
        sheet.autoSizeColumn(columnCount);
    }


    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Orders order : ordersList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, order.getOrderId(), style);
            createCell(row, columnCount++, order.getProductName(), style);
            createCell(row, columnCount++, order.getPrice(), style);
            createCell(row, columnCount++, order.getOrderDate(), style);


        }
    }
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=orders.xlsx");

        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }
    public void export(OutputStream outputStream) throws IOException {
        writeHeaderLine();
        writeDataLines();

        workbook.write(outputStream);
        workbook.close();
    }

}
