package com.qa.ecart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.ecart.utils.AppConstants;
import com.qa.ecart.utils.ElementUtil;

public class WishListPage {
	
    private static final Logger log=LogManager.getLogger(WishListPage.class);
	
	private final By header=By.tagName("h2");
	
	private final By addtocart= By.xpath("//button[@data-original-title='Add to Cart']");
	
	private final By removelisted= By.xpath("//a[@data-original-title='Remove']");
	
	private final By clickwishprod= By.xpath("//div[@id='account-wishlist']//a[text()='MacBook Pro']");

    private WebDriver driver;
	
	private static ElementUtil elutil;
	
public WishListPage (WebDriver driver) {
		
		this.driver=driver;
		
		elutil=new ElementUtil(driver);
				
	}

public String getPageHeader() {
	
	String ProductHeaderValue=
	
	elutil.waitForElementVisibility(header,AppConstants.DEFAULT_SHORT_TIME).getText();
	
	//System.out.println("Your selected Product header is "+ ProductHeaderValue );
	
	log.info("Your selected Product header is "+ ProductHeaderValue );
	
	
	
	return ProductHeaderValue;
}
	
}
