package com.qa.ecart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.ecart.utils.AppConstants;
import com.qa.ecart.utils.ElementUtil;

import io.qameta.allure.Step;

public class loginPage {

    private static final Logger log = LogManager.getLogger(loginPage.class);

    // Private by locators
    private By email = By.xpath("//input[@name='email']");
    
    private By passWord = By.xpath("//input[@name='password']");
    
    private final By loginOption = By.xpath("//input[@value='Login']");
    
    private By addressBook = By.xpath("//a[@class='list-group-item' and text()='Address Book']");
    
    private By Register = By.xpath("//a[@class='list-group-item' and text()='Register']");
    
    private By rewards = By.xpath("//a[@class='list-group-item' and text()='Reward Points']");
    
    private By forgetPassword = By.xpath("//a[@class='list-group-item' and text()='Forgotten Password']");
    
    private By transactions = By.xpath("//a[@class='list-group-item' and text()='Transactions']");
    
    private By NewsLetter = By.xpath("//a[@class='list-group-item' and text()='Newsletter']");
    
    private By continueRegister = By.xpath("//a[text()='Continue']");
    
    private final By header = By.tagName("h2");
    
    private By ErrorMessage = By.xpath("//div[@class='alert alert-danger alert-dismissible']");
    
    private By logoutExit = By.xpath("//a[contains(@class,'list-group-item') and contains(.,'Logout')]");

    private WebDriver driver;
    
    private ElementUtil elutil;

    // Public page class constructor
    public loginPage(WebDriver driver) {
        
    	this.driver = driver;
        
    	this.elutil = new ElementUtil(driver);
    }

    // Public actions methods
    
    public String getLoginpageTitle() {

        String title = elutil.waitforexactTitle(
        		
                AppConstants.LOGIN_PAGE_TITLE,
                AppConstants.DEFAULT_SHORT_TIME
        );

        log.info("Login Page title is: " + title);
        return title;
    }

    @Step("Getting login page URL")
    public String getLoginpageURL() {

        String url = elutil.waitforExactUrl(
                "account/login",
                AppConstants.DEFAULT_SHORT_TIME
        );

        log.info("Login Page URL is: " + url);
        return url;
    }

    public boolean doesLoginHeaderExist() {

        boolean flag = elutil.isElementDisplayed(header);
        
        log.info("Login Page header is: " + driver.findElement(header).getText());
        
        return flag;
    }

    public boolean ForgetPassLink() {
    	
        return driver.findElement(forgetPassword).isDisplayed();
    }

    public AccountPage justLogin(String Username, String Password) {

        log.info("Login Credentials are" + Username + "and Password is " + Password);

        elutil.waitForElementVisibility(email, AppConstants.DEFAULT_SHORT_TIME);
        
        elutil.doActionsSendKeys(email, "test124@ymail.com");
        
        elutil.doActionsSendKeys(passWord, Password);
        
        elutil.doClick(loginOption);
        

        elutil.waitforTitleContains(
        		
                getLoginpageTitle(),
                AppConstants.DEFAULT_SHORT_TIME
        );

        return new AccountPage(driver);
    }

    public RegisterPage RegisterNavigate() {

        elutil.waitForElementPresence(
                Register,
                AppConstants.DEFAULT_SHORT_TIME
        ).click();

        return new RegisterPage(driver);
    }

    public boolean LoginWarning(String WrongUN, String WrongPwd) {

        log.info("You have entered incorrect login credentials where username is "
                + WrongUN + " and password is " + WrongPwd);

        WebElement el = elutil.waitForElementPresence(
                email,
                AppConstants.DEFAULT_SHORT_TIME
        );
        el.clear();
        elutil.doSendKeys(email, WrongUN);

        WebElement el2 = elutil.waitForElementVisibility1(
                passWord,
                AppConstants.DEFAULT_SHORT_TIME
        );
        el2.clear();
        elutil.doSendKeys(passWord, WrongPwd);

        elutil.doClick(loginOption);

        String loginError = String.valueOf(
                elutil.waitForElementPresence(
                        ErrorMessage,
                        AppConstants.DEFAULT_SHORT_TIME
                )
        );

        log.error("The login error is " + loginError);

        if (loginError.contains(AppConstants.LOGIN_MAX_ATTEMPT_MSG)) {
        	
            return true;
            
        } else if (loginError.contains(AppConstants.LOGIN_INVALID_ERROR_MSG)) {
        	
            return true;
        }
        return false;
    }
}
