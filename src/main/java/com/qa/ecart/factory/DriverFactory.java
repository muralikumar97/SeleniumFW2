package com.qa.ecart.factory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.ecart.exceptions.FrameworkException;
import com.qa.ecart.utils.AppErrors;

public class DriverFactory {

    public WebDriver driver;
   
    public Properties prop;
    
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

    private static final Logger log = LogManager.getLogger(DriverFactory.class);

    public OptionsManager optionsManager;

    public WebDriver initDriver(Properties prop) {

        String browser = prop.getProperty("browser");
        
        optionsManager = new OptionsManager(prop);
        
        if (browser == null) {
            throw new FrameworkException("Browser is not specified in config.properties");
        }

        boolean remoteExecution = Boolean.parseBoolean(prop.getProperty("remote", "false"));

        log.info("Automation Test suite is running on " + browser);

        switch (browser.trim().toLowerCase()) {

        case "chrome":
            log.info("Launching the Chrome Browser");
           
            if (remoteExecution) {
                init_remoteDriver(browser);
            } else {
                tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
            }
            break;

        case "safari":
            log.info("Launching the Safari Browser");
            tlDriver.set(new SafariDriver());
            break;

        case "edge":
            log.info("Launching the Edge Browser");
           
            if (remoteExecution) {
                init_remoteDriver(browser);
            } else {
                tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
            }
            break;

        case "firefox":
            log.info("Launching the Firefox Browser");
           
            if (remoteExecution) {
                init_remoteDriver(browser);
            } else {
                tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
            }
            break;

        default:
            log.warn(AppErrors.INVALID_BROWSER);
            throw new FrameworkException("Incorrect Browser name is entered, please check");
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();

        String url = prop.getProperty("url");
        if (url == null) {
            throw new FrameworkException("URL is not specified in config.properties");
        }

        getDriver().get(url);
        return getDriver();
    }

    public Properties initProp() {

        prop = new Properties();
        InputStream fp = null;

        String envName = System.getProperty("env");

        try {
            if (envName == null) {
                log.info("No Env is given hence running the test case on default env");
                fp = DriverFactory.class.getClassLoader().getResourceAsStream("config/config.properties");
            } else {
                switch (envName.toLowerCase().trim()) {

                case "dev":
                    log.info("Running the test cases on " + envName + "env");
                    fp = DriverFactory.class.getClassLoader().getResourceAsStream("config/config_dev.properties");
                    break;

                case "uat":
                    log.info("Running the test cases on " + envName + "env");
                    fp = DriverFactory.class.getClassLoader().getResourceAsStream("config/config_uat.properties");
                    break;

                case "prod":
                    log.info("Running the test cases on " + envName + "env");
                    fp = DriverFactory.class.getClassLoader().getResourceAsStream("config/config_prod.properties");
                    break;

                default:
                    log.error("Wrong Env Name is Passed : " + envName);
                    throw new FrameworkException("===INVALID ENV PASSED===");
                }
            }

            if (fp == null) {
                throw new RuntimeException("Config file not found in classpath");
            }

            prop.load(fp);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file", e);
        }

        return prop;
    }

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    private void init_remoteDriver(String browser) {
        try {
            switch (browser.trim().toLowerCase()) {

            case "chrome":
                log.info("Executing tests on remote Chrome Browser");
                tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
                break;

            case "firefox":
                log.info("Executing tests on remote Firefox Browser");
                tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
                break;

            case "edge":
                log.info("Executing tests on remote Edge Browser");
                tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
                break;

            default:
                log.info("Incorrect Browser is given. Please check the browser name");
                throw new FrameworkException("Incorrect Browser for remote execution");
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid huburl", e);
        }
    }

    public static File getScreenshotAsFile() {
        File file = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        return file;
    }
}
