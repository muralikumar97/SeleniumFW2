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
import com.qa.ecart.utils.ExcelUtil;



public class ProductInfoTest extends baseTestclass {
	
	private static final Logger log=LogManager.getLogger(ProductInfoTest.class);

    @BeforeClass
    public void ProductInfoSetup() {
        accPage = loginPage.justLogin(prop.getProperty("username"), prop.getProperty("password"));
    }

    @DataProvider
    public Object[][] getProdImages() throws InvalidFormatException {
       
    	return ExcelUtil.getTestData("Products");
    }

    @Test(dataProvider = "getProdImages")
    public void productImageCount(String searchValue, String productName, String imageCount) {

        int expectedImages = Integer.parseInt(imageCount);

      //  System.out.println("Running test for: " + searchValue + " | " + productName + " | Expected images: " + expectedImages);
        
        log.info("Running test for: " + searchValue + " | " + productName + " | Expected images: " + expectedImages);

        SearchResultsPage = accPage.searchProduct(searchValue);
        productPage = SearchResultsPage.selectProduct(productName);

        int selectedProductImages = productPage.getImagesCount();
        Assert.assertEquals(selectedProductImages, expectedImages);
    }

    @Test
    public void getProductDetails() {

        SearchResultsPage = accPage.searchProduct("MacBook");
        productPage = SearchResultsPage.selectProduct("MacBook Pro");

        Map<String, String> EveryProdDetails = productPage.getEveryProductInfo();

        // Debug print to verify values are not null
      //  System.out.println("Complete product info is " + EveryProdDetails);
        
        log.info("Complete product info is " + EveryProdDetails);
    }

    @Test
    public void ProductBrand() {

        SoftAssert softAssert = new SoftAssert();

        SearchResultsPage = accPage.searchProduct("MacBook");
        productPage = SearchResultsPage.selectProduct("MacBook Pro");

        Map<String, String> EveryProdDetails = productPage.getEveryProductInfo();

       // System.out.println("Asserting product info: " + EveryProdDetails);
        
        log.info("Asserting product info: " + EveryProdDetails);

        softAssert.assertEquals(EveryProdDetails.get("name"), "MacBook Pro");
        softAssert.assertEquals(EveryProdDetails.get("Brand"), "Apple");
        softAssert.assertEquals(EveryProdDetails.get("Product Code"), "Product 18");

        softAssert.assertAll();
    }

    public void tearDown() {
        driver.quit();
    }
}
