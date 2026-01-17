package com.qa.ecart.pages;

import org.openqa.selenium.By;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qa.ecart.utils.AppConstants;
import com.qa.ecart.utils.ElementUtil;

public class RegisterPage {
	
	By firstName=By.id("input-firstname");

	By lastName=By.id("input-lastname");
	
	By email=By.id("input-email");
	
	By contact=By.id("input-telephone");
	
	By passWord=By.xpath("//input[@type='password' and @name='password']");
	
    By confirmPass=By.xpath("//input[@type='password' and @name='confirm']");
    
    By privacycheck= By.xpath("//input[@type='checkbox' and @name='agree']");
    
   By registerOption= By.xpath("//input[@value='Continue']");
   
   By RegisterSuccess=By.xpath("//h1[text()='Your Account Has Been Created!']");
   
   By agreeCheckBox = By.name("agree");
   
   By logoutLink=By.linkText("Logout");
	
   By RegisterLink=By.linkText("Register");
   
private static WebDriver driver;
	
	private static ElementUtil elutil;
	
	//Public page class constructor
	
	
	public RegisterPage (WebDriver driver) {
		
		this.driver=driver;
		
		elutil=new ElementUtil(driver);
		
	}
	
	public boolean userRegister(String firstName, String LastName, String email, String contact, String password, String confirmPassword) throws InterruptedException {
		
		//driver.findElement(By.id(firstName)).sendKeys("Murali");
		
		WebElement elr=	elutil.waitForElementVisibility1(this.firstName, AppConstants.DEFAULT_SHORT_TIME);
		
		elr.clear();
		
		elutil.doSendKeys(this.firstName, firstName);
		
		Thread.sleep(4000);
		
     WebElement elr2=elutil.waitForElementVisibility1(lastName, AppConstants.DEFAULT_SHORT_TIME);
     
     elr2.clear();
		
		elutil.doSendKeys(this.lastName,"Kumar");
		
	WebElement elr3= elutil.waitForElementVisibility1(this.email, AppConstants.DEFAULT_SHORT_TIME);
		
		elr3.clear();
		
		elutil.doSendKeys(this.email, email);
		
		WebElement elr4= elutil.waitForElementVisibility1(this.contact, AppConstants.DEFAULT_SHORT_TIME);
		
		elr4.clear();
		
		
       WebElement elr5= elutil.waitForElementVisibility1(this.contact, AppConstants.DEFAULT_SHORT_TIME);

        elr5.clear();
		
		elutil.doSendKeys(this.contact, contact);
		
		WebElement elr6= elutil.waitForElementVisibility1(passWord, AppConstants.DEFAULT_SHORT_TIME);
		
		elr6.clear();
		
		WebElement elr7= elutil.waitForElementVisibility1(passWord, AppConstants.DEFAULT_SHORT_TIME);

		elr7.clear();
		
		elutil.doSendKeys(passWord, password);
		
		WebElement elr8= elutil.waitForElementVisibility1(confirmPass, AppConstants.DEFAULT_SHORT_TIME);
		
		elr8.clear();
		
		elutil.doSendKeys(confirmPass, password);
		
		Thread.sleep(2000);
		
		elutil.doClick(privacycheck);
		
	//	elutil.doClick(agreeCheckBox);
		
		elutil.doClick(registerOption);
		
		String successMsg=elutil.waitForElementPresence(RegisterSuccess, AppConstants.DEFAULT_SHORT_TIME).getText();
		
		System.out.println("Registeration success message is "+successMsg);
		
		elutil.doClick(logoutLink);
		
		elutil.doClick(By.linkText("Register"));
		
		if(successMsg.equals(AppConstants.REGISTER_SUCCESS_MSG)) {
			
			return true;
		}
		
		//Assert.assertEquals(successMsg, AppConstants.REG_SUCCESS);
		
		else {
			return false;
		}
	}

}
