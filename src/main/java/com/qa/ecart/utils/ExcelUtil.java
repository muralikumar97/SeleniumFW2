package com.qa.ecart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;   // ✅ FIXED: Added to handle numbers properly
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;

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

            if (sheet.getRow(0) == null) {
                throw new RuntimeException("Sheet: " + sheetName + " is empty in Excel file: " + TEST_DATA_SHEET_PATH);
            }

            int totalRows = sheet.getLastRowNum();
            int totalCols = sheet.getRow(0).getLastCellNum();  // ✅ Correct column count from first row

            System.out.println("Total rows are : " + totalRows + " Columns are: " + totalCols);

            data = new Object[totalRows][totalCols];

            DataFormatter formatter = new DataFormatter(); // ✅ Handles numbers and strings correctly

            for (int i = 0; i < totalRows; i++) {
                for (int j = 0; j < totalCols; j++) {
                    if (sheet.getRow(i + 1) != null && sheet.getRow(i + 1).getCell(j) != null) {
                        data[i][j] = formatter.formatCellValue(sheet.getRow(i + 1).getCell(j));
                    } else {
                        data[i][j] = "";
                    }
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

    @DataProvider(name = "getProdImages")
    public static Object[][] productData() throws InvalidFormatException {
        return getTestData("Products");
    }
}