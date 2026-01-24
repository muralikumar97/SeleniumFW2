package com.qa.ecart.tests;

import org.openqa.selenium.WebDriver;



import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.base.baseTestclass;
import com.qa.ecart.pages.AccountPage;
import com.qa.ecart.pages.loginPage;
import com.qa.ecart.utils.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("EPIC-101: Design login page for eCart application")
@Feature("US-101: eCart Login Page features")
@Story("US-101: Design login page with title, url, header and login features")
@Test(groups= {"Loginpage"})

public class LoginPageTest extends baseTestclass {
	
	
	@Description("Login Page Title Test")
	
	@Link("https://example.com/issue/EPIC-101")
	
	@Severity(SeverityLevel.BLOCKER)
	
	@Owner("QA Team")
	
	
    @Test
    public void getLoginTitle() {

        String actTitle = loginPage.getLoginpageTitle();
        
        ChainTestListener.log("Login Page Title is: " + actTitle);
        
        Assert.assertEquals(actTitle,AppConstants.LOGIN_PAGE_TITLE);
    }

    
    @Test
   
    public void getLoginURL() {

        String actURL = loginPage.getLoginpageURL();
        
        Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRURL));
    

    }
    
    @Test 
    public void isForgetLinkDisplayed() {

       boolean flag=loginPage.ForgetPassLink();
       
       Assert.assertTrue(flag);

    }
  
    
    public void getloginHeader() {
    	
    	boolean header=loginPage.doesLoginHeaderExist();
    	
    	Assert.assertEquals(header, AppConstants.LOGIN_HEADER);
    }
    
    public void loginTest() {
    	
    	AccountPage AccountTitle=loginPage.justLogin(prop.getProperty("username"),prop.getProperty("password"));
    	
    	Assert.assertEquals(AccountTitle,AppConstants.ACCOUNT_PAGE_TITLE);
    }
    
}

