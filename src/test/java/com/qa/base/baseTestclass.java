package com.qa.base;

import java.util.Properties;




import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Optional;

import com.aventstack.chaintest.plugins.ChainTestListener;

import com.qa.ecart.pages.AccountPage;
import com.qa.ecart.pages.ProductInfoPage;
import com.qa.ecart.pages.RegisterPage;
import com.qa.ecart.pages.WishListPage;
import com.qa.ecart.pages.loginPage;
import com.qa.ecart.pages.searchResultsPage;
import com.qa.ecart.factory.DriverFactory;
import com.qa.ecart.listeners.TestAllureListener;

@Listeners({ChainTestListener.class,TestAllureListener.class})
public class baseTestclass {

    protected DriverFactory df;
    protected WebDriver driver;
    protected loginPage loginPage;   
    protected AccountPage accPage;
    protected searchResultsPage searchResultsPage;
    protected ProductInfoPage productPage;
    protected WishListPage wishlistPage;
    protected RegisterPage registerPage;
    protected Properties prop;
    
    @Parameters({"browser"})
    @BeforeTest
    public void testSetUp(@Optional("chrome") String browserName){

        df = new DriverFactory();   
        prop = df.initProp();
        
        if(browserName != null) {
            prop.setProperty("browser", browserName);
        }
        
        driver = df.initDriver(prop);
        loginPage = new loginPage(driver);  
    }
    
    @AfterMethod
	public void attachScreenshot(ITestResult result)
	{
		if(!result.isSuccess())

		{
			ChainTestListener.embed(DriverFactory.getScreenshotAsFile(), "image/png");
			
		}
		
	}
	
	@AfterTest
	public void tearDown()
	{
		driver.quit();
		
	}
}
