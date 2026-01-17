package com.qa.ecart.tests;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.base.baseTestclass;

public class ProductWishTest extends baseTestclass {

    @BeforeClass
    public void ProductInfoSetup() {
        accPage = loginPage.justLogin("rah.kashyap87@ymail.com", "Ace2three@123");
    }

    @Test
    public void wishProductTest() throws InterruptedException {
        SearchResultsPage = accPage.searchProduct("MacBook Pro");
        productPage = SearchResultsPage.selectProduct("MacBook Pro");

        productPage.AddProdWish(); 

        Thread.sleep(6000); 

        Assert.assertTrue(productPage.getAlertText().contains("Success: You have added"));
    }

    @Test
    public void clickAlertWish() throws InterruptedException {
        SearchResultsPage = accPage.searchProduct("MacBook Pro");
        productPage = SearchResultsPage.selectProduct("MacBook Pro");

        productPage.AddProdWish(); 

        Thread.sleep(4000); 
        
        WebElement WishAlert = productPage.getAlertLink(); 

        Actions action = new Actions(driver);
        action.moveToElement(WishAlert).click().perform(); 
        
        

    // ---------------- Optional / commented tests left as-is ----------------
    // @Test
    // public void addCartDisplay() { ... }
    // @Test
    // public void reviewLinkDisplay() { ... }
    // @Test
    // public void quantityBoxDisplay() { ... }

}}
