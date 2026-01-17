package com.qa.ecart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.base.baseTestclass;

public class SearchProductTest extends baseTestclass {
	
	@BeforeClass

	public void searchProductSetup(){
			
		accPage = loginPage.justLogin(prop.getProperty("username"),prop.getProperty("password"));
	
	}

	@Test
	
	public void searchProductTest() {
		
		SearchResultsPage=accPage.searchProduct("MacBook");
		
		productPage=SearchResultsPage.selectProduct("MacBook");
		
		String productHeader=productPage.getProductHeader();
		
		Assert.assertEquals(productHeader,"MacBook");
		
	}
	
}
