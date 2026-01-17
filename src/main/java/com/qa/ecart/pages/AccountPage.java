package com.qa.ecart.pages;

import java.util.List;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import com.qa.ecart.utils.AppConstants;

import com.qa.ecart.utils.ElementUtil;

public class AccountPage {

    private static final Logger log = LogManager.getLogger(AccountPage.class);

    private By accheaders = By.tagName("h2");
    
    private final By logoutExit = By.linkText("Logout"); // Logout Link
    
    private final By searchBar = By.name("search");
    
    private final By searchOption = By.xpath("//div[@id='search']//button");

    private WebDriver driver;
    
    private ElementUtil elutil;

    // Public page class constructor
    public AccountPage(WebDriver driver) {
        this.driver = driver;
        elutil = new ElementUtil(driver);
    }

    public List<String> getAccountHeaders() {
        return elutil.getElementsTextList(accheaders);
    }

    public boolean LogoutExist() {
    	
        boolean flag = elutil.isElementDisplayed(logoutExit);
        
        return flag;
    }

    public searchResultsPage searchProduct(String searchItem) {

        log.info("Product to be searched is " + searchItem);

        WebElement el = elutil.waitForElementPresence(searchBar,AppConstants.DEFAULT_SHORT_TIME);

        el.clear();
        
        el.sendKeys(searchItem);
        
        elutil.doClick(searchOption);

        return new searchResultsPage(driver);
    }
}
