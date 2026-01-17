package com.qa.ecart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

    public static final String TEST_DATA_SHEET_PATH = "src/test/resources/TestData/ProductInfo.xlsx";
    public static Workbook book;
    public static Sheet sheet;

    public static Object[][] getTestData(String sheetName) throws InvalidFormatException {

        Object data[][] = null;

        try (FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH)) {

            book = WorkbookFactory.create(ip);
            sheet = book.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("Sheet: " + sheetName + " not found in Excel file: " + TEST_DATA_SHEET_PATH);
            }

            System.out.println("Total rows are : " + sheet.getLastRowNum() + " Columns are: " + sheet.getRow(0).getLastCellNum());

            data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
                    data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Excel file not found at path: " + TEST_DATA_SHEET_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read Excel file: " + TEST_DATA_SHEET_PATH);
        }

        return data;
    }

}
