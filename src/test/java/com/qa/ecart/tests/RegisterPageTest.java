package com.qa.ecart.tests;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.base.baseTestclass;
import com.qa.ecart.utils.ExcelUtil;
import com.qa.ecart.utils.StringUtil;
import com.qa.ecart.pages.WishListPage;
import com.qa.ecart.utils.CSVUtil;   

public class RegisterPageTest extends baseTestclass {
	
	private static final Logger log=LogManager.getLogger(RegisterPageTest.class);

    @BeforeClass
    public void GotoRegisterPage() {
    	
        registerPage = loginPage.RegisterNavigate();
    }

    @DataProvider
    public Object[][] userRegsTest() throws InvalidFormatException {
    	
        return ExcelUtil.getTestData("RegsUsers"); 
    }
    
    
    @DataProvider
    public static Object[][] userRegsCSVTest() {  
    	
        return CSVUtil.csvData("RegsUsers"); 
    }

    @Test(dataProvider="userRegsCSVTest")
    public void doRegister(String firstName, String LastName, String contact, String password, String confirmPassword) throws InterruptedException {

      //  System.out.println("Registering user: " + firstName + " " + LastName + " | Contact: " + contact);
        
        log.info("Registering user: " + firstName + " " + LastName + " | Contact: " + contact);

        Thread.sleep(1000);

        boolean flag = registerPage.userRegister(firstName, LastName, StringUtil.generateEmail(), contact, password, confirmPassword);

        Assert.assertTrue(flag);
    }
}
