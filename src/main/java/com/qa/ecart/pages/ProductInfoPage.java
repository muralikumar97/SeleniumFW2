package com.qa.ecart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.ecart.utils.AppConstants;
import com.qa.ecart.utils.ElementUtil;

public class ProductInfoPage {
	
    private static final Logger log = LogManager.getLogger(ProductInfoPage.class);

    private final By header = By.tagName("h1");
    private final By images = By.xpath("//a[@class='thumbnail']");
    private final By wishlist = By.xpath("//button[@data-original-title='Add to Wish List']");
    private final By alert = By.xpath("//button[@data-original-title='Add to Wish List']");
    private final By wishAlertClick = By.xpath("//a[text()='wish list']");
    private final By prodData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
    private final By prodPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");

    public Map<String, String> ProdInfo;

    private WebDriver driver;
    private ElementUtil elutil;

    public ProductInfoPage(WebDriver driver) {
        this.driver = driver;
        elutil = new ElementUtil(driver);
    }

    public String getProductHeader() {
        String headerVal = elutil
                .waitForElementVisibility(header, AppConstants.DEFAULT_SHORT_TIME)
                .getText();

        log.info("Product Info Page Header is : " + headerVal);
        return headerVal;
    }

    public void getProductData() {

        // ✅ FIX: ensure map is initialized before put()
        if (ProdInfo == null) {
            ProdInfo = new HashMap<>();
        }

        List<WebElement> prodsinfo =
                elutil.waitforElementsVisibility(prodData, AppConstants.DEFAULT_SHORT_TIME);

        log.info("Total size of the meta data is : " + prodsinfo.size());

        for (WebElement e : prodsinfo) {

            String text = e.getText();

            if (text.contains(":")) {
                String meta[] = text.split(":", 2);
                String key = meta[0].trim();
                String value = meta[1].trim();
                ProdInfo.put(key, value);
            }
        }
    }

    public void getProductDetails() {

        // ✅ FIX: safety init
        if (ProdInfo == null) {
            ProdInfo = new HashMap<>();
        }

        List<WebElement> prodpriceinfo =
                elutil.waitforElementsVisibility(prodPriceData, AppConstants.DEFAULT_SHORT_TIME);

        String Prodprice = prodpriceinfo.get(0).getText();
        String noProdtax = prodpriceinfo.get(1).getText().split(":")[1].trim();

        ProdInfo.put("Product Price", Prodprice);
        ProdInfo.put("ExTax", noProdtax);
    }

    public void getProdAvailability() {

        // ✅ FIX: safety init
        if (ProdInfo == null) {
            ProdInfo = new HashMap<>();
        }

        String availability =
                driver.findElement(By.xpath("//li[contains(text(),'Availability')]")).getText();

        ProdInfo.put("Availability", availability.split(":")[1].trim());
    }

    public void getProdBrand() {

        // ✅ FIX: safety init
        if (ProdInfo == null) {
            ProdInfo = new HashMap<>();
        }

        List<WebElement> BrandProdInfo =
                elutil.waitforElementsVisibility(prodData, AppConstants.DEFAULT_SHORT_TIME);

        for (WebElement e : BrandProdInfo) {

            String text = e.getText();

            if (text.startsWith("Brand")) {
                ProdInfo.put("Brand", text.split(":", 2)[1].trim());
            }

            if (text.startsWith("Reward Points")) {
                ProdInfo.put("Reward Points", text.split(":", 2)[1].trim());
            }
        }
    }

    public Map<String, String> getEveryProductInfo() {

        ProdInfo = new HashMap<>();

        ProdInfo.put("name", getProductHeader());

        getProductData();
        getProductDetails();
        getProdBrand();
        getProdAvailability();

        log.info("Complete product info is " + ProdInfo);
        return ProdInfo;
    }

    public int getImagesCount() {

        int imagesCount =
                elutil.waitforElementsVisibility(images, AppConstants.DEFAULT_MEDIUM_TIME).size();

        log.info("Overall images of the selected product is : " + imagesCount);
        return imagesCount;
    }

    public void AddProdWish() {
        elutil.doClick(wishlist);
    }

    public String getAlertText() {
        return elutil
                .waitForElementVisibility(alert, AppConstants.DEFAULT_SHORT_TIME)
                .getText();
    }

    public WebElement getAlertLink() {
        return elutil
                .waitForElementVisibility(wishAlertClick, AppConstants.DEFAULT_SHORT_TIME);
    }
}