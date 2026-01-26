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
		
<<<<<<< HEAD
		searchResultsPage=accPage.searchProduct("MacBook");
		
		productPage=searchResultsPage.selectProduct("MacBook");
=======
		SearchResultsPage=accPage.searchProduct("MacBook");
		
		productPage=SearchResultsPage.selectProduct("MacBook");
>>>>>>> 9e6b561aa016b1604d8bd139bc9b311790e43c59
		
		String productHeader=productPage.getProductHeader();
		
		Assert.assertEquals(productHeader,"MacBook");
		
	}
	
}
