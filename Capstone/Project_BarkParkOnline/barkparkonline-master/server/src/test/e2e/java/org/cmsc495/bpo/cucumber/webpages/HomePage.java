package org.cmsc495.bpo.cucumber.webpages;

import org.cmsc495.bpo.cucumber.drivers.FirefoxSeleniumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage implements WebPage {
    private static final String URL = WebPage.BASE_URL + "/";

    @FindBy(id = "edit-profile-btn")
    private WebElement editProfileButton;

    @FindBy(id = "logout-btn")
    private WebElement logoutButton;

    @FindBy(id = "dropdown-toggle")
    private WebElement dropdownToggle;

    @FindBy(id = "logged-in-as")
    private WebElement loggedInAs;

    private HomePage() {
        PageFactory.initElements(FirefoxSeleniumDriver.getDriver(), this);
    }

    /**
     * Initializes the HomePage with Selenium's Configs
     */
    public static HomePage init() {
        HomePage page = new HomePage();
        return page;
    }

    public HomePage clickEditProfileButton() {
        this.editProfileButton.click();
        return this;
    }

    public HomePage clickLogoutButton() {
        this.logoutButton.click();
        return this;
    }

    public HomePage clickDropdownToggle() {
        this.dropdownToggle.click();
        return this;
    }

    public WebElement getLoggedInAs() {
        return this.loggedInAs;
    }

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public HomePage then() {
        return this;
    }
}
