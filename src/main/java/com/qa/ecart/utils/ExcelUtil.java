package com.qa.ecart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
<<<<<<< HEAD
import org.apache.poi.ss.usermodel.DataFormatter;   // ✅ ADDED
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;
=======
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
>>>>>>> 9e6b561aa016b1604d8bd139bc9b311790e43c59

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

<<<<<<< HEAD
            if (sheet.getRow(0) == null) {
                throw new RuntimeException("Sheet: " + sheetName + " is empty in Excel file: " + TEST_DATA_SHEET_PATH);
            }

            int totalRows = sheet.getLastRowNum();
            int totalCols = 6;  // ✅ FIX: Restrict to actual Excel columns only

            System.out.println("Total rows are : " + totalRows + " Columns are: " + totalCols);

            data = new Object[totalRows][totalCols];

            DataFormatter formatter = new DataFormatter(); // ✅ FIX: Handles numbers correctly

            for (int i = 0; i < totalRows; i++) {
                for (int j = 0; j < totalCols; j++) {

                    if (sheet.getRow(i + 1) != null && sheet.getRow(i + 1).getCell(j) != null) {
                        data[i][j] = formatter.formatCellValue(sheet.getRow(i + 1).getCell(j));  // ✅ FIX
                    } else {
                        data[i][j] = "";
                    }
=======
            System.out.println("Total rows are : " + sheet.getLastRowNum() + " Columns are: " + sheet.getRow(0).getLastCellNum());

            data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
                    data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
>>>>>>> 9e6b561aa016b1604d8bd139bc9b311790e43c59
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

<<<<<<< HEAD
    @DataProvider(name = "getProdImages")
    public static Object[][] productData() throws InvalidFormatException {
        return getTestData("Products");
    }
}
=======
}
>>>>>>> 9e6b561aa016b1604d8bd139bc9b311790e43c59
