package org.cmsc495.bpo.cucumber.drivers;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.cmsc495.bpo.cucumber.webpages.WebPage;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@SuppressWarnings("all")
public class FirefoxSeleniumDriver {
    protected static final Logger log = LoggerFactory.getLogger(FirefoxSeleniumDriver.class);
    protected WebDriver driver;

    protected static FirefoxSeleniumDriver FIREFOX;

    static {
        if ( System.getProperty("os.name").toLowerCase().contains("linux") ) {
            System.setProperty("webdriver.gecko.driver", findFile("src/test/resources/drivers/linux/geckodriver"));
        } else {
            System.setProperty("webdriver.gecko.driver", findFile("src/test/resources/drivers/geckodriver"));
        }
    }
    	 
	static private String findFile(String filename) {
        String paths[] = {"", "bin/", "target/classes"};
        String os = System.getProperty("os.name");
        log.info("PLATFORM for Gecko Driver: {}", os);
        String file = filename;
        if (os.toLowerCase().contains("windows")) file = file  + ".exe";

        for (String path : paths) {
           if (new File(path + file).exists())
               return path + file;
        }
        return "";
     }
    
    public FirefoxSeleniumDriver(String startUrl) {
	    Capabilities capabilities = DesiredCapabilities.firefox();
	    driver = new FirefoxDriver(capabilities);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        FIREFOX = this;
    }

    public void closeWindow() { this.driver.close(); }

    public String getTitle() { return this.driver.getTitle(); }

    public String getCurrentUrl() { return this.driver.getCurrentUrl(); }

    public static WebDriver getDriver() { return FIREFOX.driver; }

    /**
     * Goes to the given Web Page unless the driver is already there.
     * 
     * @param webPage
     */
    public <E> E goTo(E webPage) {
        WebPage wp = (WebPage) webPage;
        String targetUrl = wp.getUrl();
        if (targetUrl.equals(this.driver.getCurrentUrl())) {
            return webPage; // Already on page
        }
        this.driver.navigate().to(wp.getUrl());
        return webPage;
    }

    public static FirefoxSeleniumDriver get() {
        return FIREFOX;
    }
}
