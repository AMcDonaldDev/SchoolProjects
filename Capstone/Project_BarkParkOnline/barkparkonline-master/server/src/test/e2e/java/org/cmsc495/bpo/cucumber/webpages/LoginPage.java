package org.cmsc495.bpo.cucumber.webpages;

import org.cmsc495.bpo.cucumber.drivers.FirefoxSeleniumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage implements WebPage {
    protected static final Logger log = LoggerFactory.getLogger(LoginPage.class);
    private static final String URL = WebPage.BASE_URL + "/login";

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-btn")
    private WebElement loginButton;

    private LoginPage() {
        PageFactory.initElements(FirefoxSeleniumDriver.getDriver(), this);
    }

    /**
     * Initializes the LoginPage with Selenium's Configs
     */
    public static LoginPage init() {
        LoginPage page = new LoginPage();
        return page;
    }

    public LoginPage inputUsername(String username) {
        usernameInput.sendKeys(username);
        return this;
    }

    public LoginPage inputPassword(String password) {
        passwordInput.sendKeys(password);
        return this;
    }

    public LoginPage clickLoginButton() {
        loginButton.click();
        return this;
    }

    /**
     * Get the Username Input Field
     * 
     * @return
     */
    public WebElement getUsernameInput() { return this.usernameInput; }

    /**
     * Get the Password Input Field
     * @return
     */
    public WebElement getPasswordInput() { return this.passwordInput; }

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public LoginPage then() {
        return this;
    }
}
