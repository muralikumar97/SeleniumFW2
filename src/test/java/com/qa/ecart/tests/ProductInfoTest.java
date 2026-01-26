package com.qa.ecart.tests;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.base.baseTestclass;
import com.qa.ecart.pages.loginPage;
import com.qa.ecart.pages.searchResultsPage;
import com.qa.ecart.utils.ExcelUtil;

public class ProductInfoTest extends baseTestclass {

    private static final Logger log = LogManager.getLogger(ProductInfoTest.class);

    @BeforeClass
    public void ProductInfoSetup() {
        loginPage lp = new loginPage(driver);
        accPage = lp.justLogin(prop.getProperty("username"), prop.getProperty("password"));
    }

    @DataProvider(name = "getProdImages")
    public static Object[][] getProdImages() throws InvalidFormatException {
        return ExcelUtil.getTestData("Products");
    }

    // ✅ FIX: Method now matches 6 Excel columns
    @Test(dataProvider = "getProdImages")
    public void productImageCount(String searchValue, String productName, String imageCount,
                                  String brand, String productCode, String price) {

        int expectedImages = (int) Double.parseDouble(imageCount.trim());

        log.info("Running test for: " + searchValue + " | " + productName + " | Expected images: " + expectedImages);

        searchResultsPage searchResultsPage = accPage.searchProduct(searchValue);
        productPage = searchResultsPage.selectProduct(productName);

        int selectedProductImages = productPage.getImagesCount();
        Assert.assertEquals(selectedProductImages, expectedImages);
    }

    @Test
    public void getProductDetails() {

        searchResultsPage searchResultsPage = accPage.searchProduct("MacBook");
        productPage = searchResultsPage.selectProduct("MacBook");

        Map<String, String> everyProdDetails = productPage.getEveryProductInfo();
        log.info("Complete product info is " + everyProdDetails);
    }

    @Test
    public void ProductBrand() {

        SoftAssert softAssert = new SoftAssert();

        searchResultsPage searchResultsPage = accPage.searchProduct("MacBook");
        productPage = searchResultsPage.selectProduct("MacBook");

        Map<String, String> everyProdDetails = productPage.getEveryProductInfo();
        log.info("Asserting product info: " + everyProdDetails);

        softAssert.assertEquals(everyProdDetails.get("name"), "MacBook");
        softAssert.assertEquals(everyProdDetails.get("Brand"), "Apple");
        softAssert.assertEquals(everyProdDetails.get("Product Code"), "Product 16");

        softAssert.assertAll();
    }

    public void tearDown() {
        driver.quit();
    }
}
