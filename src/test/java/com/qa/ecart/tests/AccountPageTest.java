package com.qa.ecart.tests;

import java.util.List;

import org.testng.Assert;
import com.qa.ecart.pages.AccountPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.base.baseTestclass;

public class AccountPageTest extends baseTestclass {
	
    @BeforeClass
    public void accSetup(){
        accPage = loginPage.justLogin(prop.getProperty("username"), prop.getProperty("password"));
    }

    @Test
    public void doesLogoutexist() {
        boolean flag = accPage.isLogoutLinkPresent();
        Assert.assertTrue(flag);	
    }

    @Test 
    public void accPageHeaders() {
        List<String> accPageHeaders = accPage.getAccountHeaders();
        Assert.assertEquals(accPageHeaders.size(), 4); // FIXED count
    }

    @Test
    public void searchProduct() {
        accPage.searchProduct("MacBook");
    }
}
