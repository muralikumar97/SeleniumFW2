package com.qa.ecart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ecart.utils.AppConstants;
import com.qa.ecart.utils.ElementUtil;

public class searchResultsPage {
	
	private static final Logger log = LogManager.getLogger(searchResultsPage.class);
	
	private WebDriver driver;
	
	private ElementUtil elutil;
	
	private final By searchResults = By.cssSelector("div.product-thumb");
	
	private final By resultsHeader = By.tagName("h1");

	// Public page class constructor
	public searchResultsPage(WebDriver driver) {
		
		this.driver = driver;
		
		elutil = new ElementUtil(driver);
	}
		
	public int getsearchResults() {
			
		int count = elutil.waitforElementsPresence(
				searchResults, AppConstants.DEFAULT_SHORT_TIME).size();
		
		log.info("Search results count is " + count + " products");
						
		return count;
	}
		
	public String getResultsHeader() {
			
		String ResultHeader = elutil.doElementGetText(resultsHeader);
			
		log.info("The search results header is " + ResultHeader);
			
		return ResultHeader;
	}

	public ProductInfoPage selectProduct(String ProductName) {
			
		log.info("Product to be select is " + ProductName);
			
		elutil.doClick(By.linkText(ProductName));
			
		return new ProductInfoPage(driver);
	}
}
