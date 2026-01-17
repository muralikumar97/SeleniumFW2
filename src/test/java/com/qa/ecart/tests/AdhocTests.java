package com.qa.ecart.tests;

import com.qa.base.baseTestclass;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AdhocTests extends baseTestclass {

    @DataProvider(name = "NegativeLoginData")
    
    public Object[][] testNegativecases() {
    	
        return new Object[][]{
                {"", "gameon"},
                {"gameon", ""},
                {"", ""},
                {"&", "12"}
        };
    }

    @Test(dataProvider = "NegativeLoginData")
    public void IncorrectLoginDetails(String username, String password) {
    	
    	
        boolean flag = loginPage.LoginWarning(username, password);
        
        Assert.assertTrue(flag, "Login warning should be displayed for invalid credentials");
    }
}
