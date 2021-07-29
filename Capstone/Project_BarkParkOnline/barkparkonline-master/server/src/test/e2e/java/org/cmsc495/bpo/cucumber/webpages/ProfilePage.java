package org.cmsc495.bpo.cucumber.webpages;

import java.util.List;

import org.cmsc495.bpo.cucumber.drivers.FirefoxSeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfilePage implements WebPage {
    protected static final Logger log = LoggerFactory.getLogger(ProfilePage.class);
    private static final String URL = WebPage.BASE_URL + "/profile";

    WebDriver driver;

    @FindBy(id = "add-dog-btn")
    public WebElement addDogBtn;

    @FindBy(id = "dog-name")
    public WebElement dogNameInput;

    @FindBy(id = "dog-breed")
    public WebElement dogBreedInput;

    @FindBy(id = "dog-dob")
    public WebElement dogDobInput;

    @FindBy(xpath = "/html/body/div[2]/div[2]/div/mat-datepicker-content/div[2]/mat-calendar/div/mat-month-view/table/tbody/tr[2]/td[2]/div[1]")
    public WebElement dogDobPopup1st;

    @FindBy(xpath = "/html/body/div[2]/div[2]/div/mat-datepicker-content/div[2]/mat-calendar/div/mat-month-view/table/tbody/tr[2]/td[3]/div[1]")
    public WebElement dogDobPopup2nd;

    @FindBy(xpath = "/html/body/app-root/div/div/div/app-profile/div/div/div[2]/div[1]/div[4]/mat-form-field/div/div[1]/div/mat-select/div")
    public WebElement dogGenderInputSelect;

    @FindBy(xpath = "/html/body/div[2]/div[2]/div/div/div/mat-option[1]/span")
    public WebElement dogGenderMale;

    @FindBy(xpath = "/html/body/div[2]/div[2]/div/div/div/mat-option[2]/span")
    public WebElement dogGenderFemale;

    @FindBy(id = "select-dog")
    public WebElement selectDogDropdown;

    @FindBy(id = "save-dog-btn")
    public WebElement saveDogBtn;

    @FindBy(id = "update-dog-btn")
    public WebElement updateDogBtn;

    @FindBy(id = "remove-dog-btn")
    public WebElement removeDogBtn;

    public boolean selectDogFromEditDropdown(String dogname) {
        // Dog is found at an xpath like this ...
        //   /html/body/div[2]/div[2]/div/div/div/mat-option/span
        // but there is no guarentee what order the names may be in so
        // we have to dynamically figure it out.
        // #dog-editor
        //   #select-dog-panel
        selectDogDropdown.click();
        By by = By.cssSelector("mat-option span.mat-option-text");
        List<WebElement> elements = this.driver.findElements(by);
        log.info("SELECTING FROM {} ELEMENTS", elements.size());
        for ( WebElement el : elements ) {
            if ( el.getText().equals(dogname) ) {
                el.click();
                return true;
            }
        }

        return false;
    }

    private ProfilePage() {
        driver = FirefoxSeleniumDriver.getDriver();
        PageFactory.initElements(driver, this);
    }

    /**
     * Initializes the LoginPage with Selenium's Configs
     */
    public static ProfilePage init() {
        ProfilePage page = new ProfilePage();
        return page;
    }

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public ProfilePage then() {
        return this;
    }
}
