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

    private By email = By.xpath("//input[@name='email']");
    private By passWord = By.xpath("//input[@name='password']");
    private final By loginOption = By.xpath("//input[@value='Login']");
    private By header = By.tagName("h2");
    private By forgetPassword = By.xpath("//a[@class='list-group-item' and text()='Forgotten Password']");
    private By ErrorMessage = By.xpath("//div[@class='alert alert-danger alert-dismissible']");
    private By Register = By.xpath("//a[@class='list-group-item' and text()='Register']");

    private WebDriver driver;
    private ElementUtil elutil;

    public loginPage(WebDriver driver) {
        this.driver = driver;
        this.elutil = new ElementUtil(driver);
    }

    @Step("Getting login page URL")
    public String getLoginpageURL() {
        String url = elutil.waitforExactUrl("account/login", AppConstants.DEFAULT_SHORT_TIME);
        log.info("Login Page URL is: " + url);
        return url;
    }

    public boolean doesLoginHeaderExist() {
        boolean flag = elutil.isElementDisplayed(header);
        log.info("Login Page header is: " + driver.findElement(header).getText());
        return flag;
    }

    public boolean ForgetPassLink() {
        return elutil.isElementDisplayed(forgetPassword);
    }

    public AccountPage justLogin(String username, String password) {
        log.info("Login Credentials are " + username + " and Password is " + password);

        elutil.waitForElementVisibility(email, AppConstants.DEFAULT_SHORT_TIME);
        elutil.doActionsSendKeys(email, username);
        elutil.doActionsSendKeys(passWord, password);
        elutil.doClick(loginOption);

        // ✅ FIX: Removed incorrect wait for Login Page title after login

        return new AccountPage(driver);
    }
    
    public String getLoginpageTitle() {
        String title = driver.getTitle();
        log.info("Login Page title is: " + title);
        return title;
    }


    public RegisterPage RegisterNavigate() {
        elutil.waitForElementPresence(Register, AppConstants.DEFAULT_SHORT_TIME).click();
        return new RegisterPage(driver);
    }

    public boolean LoginWarning(String wrongUsername, String wrongPassword) {
        log.info("Entered incorrect login credentials: username=" + wrongUsername + ", password=" + wrongPassword);

        WebElement el = elutil.waitForElementPresence(email, AppConstants.DEFAULT_SHORT_TIME);
        el.clear();
        elutil.doSendKeys(email, wrongUsername);

        WebElement el2 = elutil.waitForElementVisibility1(passWord, AppConstants.DEFAULT_SHORT_TIME);
        el2.clear();
        elutil.doSendKeys(passWord, wrongPassword);

        elutil.doClick(loginOption);

        String loginError = elutil.waitForElementPresence(ErrorMessage, AppConstants.DEFAULT_SHORT_TIME).getText();

        log.error("The login error is " + loginError);

        return loginError.contains(AppConstants.LOGIN_MAX_ATTEMPT_MSG)
                || loginError.contains(AppConstants.LOGIN_INVALID_ERROR_MSG);
    }
}
