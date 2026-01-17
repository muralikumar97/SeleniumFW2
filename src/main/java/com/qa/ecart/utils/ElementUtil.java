package com.qa.ecart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.ecart.exceptions.ElementException;

public class ElementUtil {

    private static final Logger log = LogManager.getLogger(ElementUtil.class);

    private WebDriver driver;
    private Actions act;

    public ElementUtil(WebDriver driver) {
        this.driver = driver;
        act = new Actions(driver);
    }

    public WebElement getElement(By locator) {
        WebElement el = driver.findElement(locator);
        return el;
    }

    public List<WebElement> getElements(By locator) {
        List<WebElement> elList = driver.findElements(locator);
        return elList;
    }

    public void doSendKeys(By locator, String value) {
        if (value == null) {
            throw new ElementException("===Invalid String value. Null Not Allowed.===");
        } else {
            getElement(locator).sendKeys(value);
        }
    }

    public void doMultipleSendKeys(By locator, CharSequence... value) {
        getElement(locator).sendKeys(value);
    }

    public void doClick(By locator) {
        getElement(locator).click();
    }

    public String doElementGetText(By locator) {
        String text = getElement(locator).getText();
        return text;
    }

    public boolean isElementDisplayed(By locator) {
        try {
            getElement(locator).isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            log.error("Locators are not correct");
            e.printStackTrace();
            return false;
        }
    }

    public boolean isElementEnabled(By locator) {
        try {
            getElement(locator).isEnabled();
            return true;
        } catch (NoSuchElementException e) {
            log.error("Element is not interactable or enabled on the Page");
            e.printStackTrace();
            return false;
        }
    }

    public String getElementDomPropertyValue(By locator, String attrName) {
        String attrValue = getElement(locator).getDomProperty(attrName);
        return attrValue;
    }

    public String getElementDomAttributeValue(By locator, String attrName) {
        String attrValue = getElement(locator).getDomAttribute(attrName);
        return attrValue;
    }

    public int getTotalElementsCount(By locator) {
        int count = getElements(locator).size();
        return count;
    }

    public List<String> getElementsTextList(By locator) {
        List<WebElement> elList = getElements(locator);
        List<String> textList = new ArrayList<String>();

        for (WebElement e : elList) {
            String text = e.getText();
            if (text.length() != 0) {
                textList.add(text);
            }
        }
        return textList;
    }

    public boolean isElementExist(By locator) {
        int count = getTotalElementsCount(locator);
        if (count == 1) {
            log.info("Element is available on the page 1 time");
            return true;
        } else {
            log.info("Element is not available on the webpage");
            return false;
        }
    }

    public boolean isElementExist(By locator, int expectedCount) {
        int count = getTotalElementsCount(locator);
        if (count == expectedCount) {
            log.info("Element is available on the page " + expectedCount + " times");
            return true;
        } else {
            log.info("Element is not available on the webpage");
            return false;
        }
    }

    public void oneClickElementFromList(By locator, String value) {
        List<WebElement> elList = getElements(locator);
        for (WebElement e : elList) {
            String text = e.getText();
            if (text.equals(value)) {
                e.click();
                break;
            }
        }
    }

    public void doSearchAutoClickSuggestion(By searchLocator, String searchValue, By suggestionLocator,
                                           String suggestedValue) throws InterruptedException {

        Thread.sleep(2000);
        doSendKeys(searchLocator, searchValue);
        Thread.sleep(3000);

        List<WebElement> suggestionList = getElements(suggestionLocator);
        log.info("Total size of the suggestion is " + suggestionList.size());

        for (WebElement e : suggestionList) {
            String text = e.getText();
            if (text.equals(suggestedValue)) {
                e.click();
                break;
            }
        }
    }

    // Dropdown methods

    public void doSelectByIndex(By locator, int index) {
        Select sec = new Select(getElement(locator));
        sec.selectByIndex(index);
    }

    public void doSelectByValue(By locator, String value) {
        Select sec = new Select(getElement(locator));
        sec.selectByValue(value);
    }

    public void doSelectByVisibleText(By locator, String text) {
        Select sec = new Select(getElement(locator));
        sec.selectByVisibleText(text);
    }

    public void doDeSelectByIndex(By locator, int index) {
        Select sec = new Select(getElement(locator));
        sec.deselectByIndex(index);
    }

    public void doDeSelectByValue(By locator, String value) {
        Select sec = new Select(getElement(locator));
        sec.deselectByValue(value);
    }

    public void doDeSelectByVisibleText(By locator, String text) {
        Select sec = new Select(getElement(locator));
        sec.deselectByVisibleText(text);
    }

    public List<String> getDropdownValuesList(By locator) {
        Select sec = new Select(getElement(locator));
        List<WebElement> optionsList = sec.getOptions();
        List<String> textList = new ArrayList<String>();

        log.info("Total country are  " + optionsList.size());

        for (WebElement e : optionsList) {
            textList.add(e.getText());
        }
        return textList;
    }

    public int getDropdownOptionsCount(By locator) {
        Select sec = new Select(getElement(locator));
        return sec.getOptions().size();
    }

    // Action methods

    public void SubMenuHandle(By parentMenuLocator, By childMenuLocator) {
        act.moveToElement(getElement(parentMenuLocator)).perform();
        act.click(getElement(childMenuLocator)).perform();
    }

    public void SubMenuHandleL4(By parentMenuLocator, By subMenu1Locator, By subMenu2Locator,
                               By subMenu3Locator) throws InterruptedException {

        doClick(parentMenuLocator);
        Thread.sleep(2000);
        act.moveToElement(getElement(subMenu1Locator)).perform();
        Thread.sleep(2000);
        act.moveToElement(getElement(subMenu2Locator)).perform();
        Thread.sleep(2000);
        act.moveToElement(getElement(subMenu3Locator))
                .click(driver.findElement(subMenu3Locator))
                .perform();
    }

    // ✅ ONLY FIX APPLIED HERE
    public void doActionsSendKeys(By locator, String value) {
        act.sendKeys(driver.findElement(locator), value).perform();
    }

    public void doActionsClick(By locator, String value) {
        act.click(driver.findElement(locator)).perform();
    }

    // Wait utilities

    public WebElement waitForElementPresence(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement waitForElementVisibility1(By firstName, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
    }

    public WebElement waitForElementVisibility(By email, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(email));
    }

    public Alert waitforAlert(int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public String getAlertTextWithWait(int timeOut) {
        return waitforAlert(timeOut).getText();
    }

    public void acceptAlertWithWait(int timeOut) {
        waitforAlert(timeOut).accept();
    }

    public void dismissAlertWithWait(int timeOut) {
        waitforAlert(timeOut).dismiss();
    }

    public String waitforTitleContains(String expectedValue, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            wait.until(ExpectedConditions.titleContains(expectedValue));
        } catch (TimeoutException e) {
            log.info("Expected Title is not matching");
            e.printStackTrace();
        }
        return driver.getTitle();
    }

    public String waitforexactTitle(String expectedValue, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            wait.until(ExpectedConditions.titleIs(expectedValue));
        } catch (TimeoutException e) {
            log.info("Expected Title is not matching");
            e.printStackTrace();
        }
        return driver.getTitle();
    }

    public String waitforUrlContains(String expectedValue, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            wait.until(ExpectedConditions.urlContains(expectedValue));
        } catch (TimeoutException e) {
            log.info("Expected Url is not matching");
            e.printStackTrace();
        }
        return driver.getCurrentUrl();
    }

    public String waitforExactUrl(String expectedValue, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            wait.until(ExpectedConditions.urlToBe(expectedValue));
        } catch (TimeoutException e) {
            log.info("Expected Url is not matching");
            e.printStackTrace();
        }
        return driver.getCurrentUrl();
    }

    public boolean waitforFrameAndSwitchtoIt(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
            return true;
        } catch (TimeoutException e) {
            log.info("Frame is not available");
            e.printStackTrace();
            return false;
        }
    }

    public List<WebElement> waitforElementsPresence(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public List<WebElement> waitforElementsVisibility(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public void waitForElementClickable(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
}
