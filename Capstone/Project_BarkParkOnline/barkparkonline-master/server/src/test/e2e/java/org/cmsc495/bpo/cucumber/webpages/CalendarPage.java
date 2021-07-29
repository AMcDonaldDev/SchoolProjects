package org.cmsc495.bpo.cucumber.webpages;

import java.util.List;

import org.cmsc495.bpo.cucumber.drivers.FirefoxSeleniumDriver;
import org.cmsc495.bpo.dao.interfaces.Park;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalendarPage implements WebPage {
    protected static final Logger log = LoggerFactory.getLogger(CalendarPage.class);
    private static final String URL = WebPage.BASE_URL + "/calendar";

    @FindBy(id = "open-visit-scheduler-btn")
    private WebElement visitSchedulerBtn;

    @FindBy(id = "new-visit-btn")
    private WebElement newVisitBtn;

    @FindBy(id = "visit-datetime")
    private WebElement newVisitDatetime;

    @FindBy(id = "select-dog")
    private WebElement selectDogDropdown;

    @FindBy(id = "add-visit")
    private WebElement addVisitBtn;

    @FindBy (id = "change-park-btn")
    private WebElement changeParkBtn;

    @Override
    public String getUrl() {
        return URL;
    }

    private CalendarPage() {
        PageFactory.initElements(FirefoxSeleniumDriver.getDriver(), this);
    }

    public WebElement getSelectDogDropdown() {
        return this.selectDogDropdown;
    }

    public CalendarPage openSelectDogDropdown() {
        this.selectDogDropdown.click();
        return this;
    }

    public CalendarPage closeSelectDogDropdown() {
        this.selectDogDropdown.sendKeys(Keys.TAB);
        return this;
    }

    public CalendarPage deselectAllOptions() {
        List<WebElement> options = FirefoxSeleniumDriver.getDriver()
            .findElements(By.className("mat-option"));

        for (WebElement option : options) {
            if (Boolean.parseBoolean(option.getAttribute("aria-selected"))) {
                option.click();
            }
        }
        return this;
    }

    /**
     * Initializes the LoginPage with Selenium's Configs
     */
    public static CalendarPage init() {
        CalendarPage page = new CalendarPage();
        return page;
    }

    public CalendarPage openVisitScheduler() {
        this.visitSchedulerBtn.click();
        return this;
    }

    public CalendarPage editVisit(String visitId) {
        WebElement editVisitBtn = FirefoxSeleniumDriver.getDriver().findElement(By.id(visitId + "-edit-btn"));
        editVisitBtn.click();
        return this;
    }

    public CalendarPage editVisitArrivingDateAndTime(String visitId, String datetime) {
        WebElement arriving = FirefoxSeleniumDriver.getDriver().findElement(By.id(visitId + "-arriving-input"));
        String clear = "";
        log.info("Clearing {} values using backspace", arriving.getAttribute("value").length());
        for (int i = 0; i < arriving.getAttribute("value").length(); i++) {
            clear += Keys.BACK_SPACE;
        }
        arriving.sendKeys(clear + datetime);
        return this;
    }

    public CalendarPage editNewVisitArrivingDateAndTime(String datetime) {
        String clear = "";
        log.info("Clearing {} values using backspace", this.newVisitDatetime.getAttribute("value").length());
        for (int i = 0; i < newVisitDatetime.getAttribute("value").length(); i++) {
            clear += Keys.BACK_SPACE;
        }
        newVisitDatetime.sendKeys(clear + datetime);
        return this;
    }

    public CalendarPage pressSaveButtonFor(String visitId) {
        WebElement saveBtn = FirefoxSeleniumDriver.getDriver().findElement(By.id(visitId + "-save-btn"));
        saveBtn.click();
        return this;
    }

    public CalendarPage pressDeleteButtonFor(String visitId) {
        WebElement saveBtn = FirefoxSeleniumDriver.getDriver().findElement(By.id(visitId + "-delete-btn"));
        saveBtn.click();
        return this;
    }

    public CalendarPage clickAddVisitButton() {
        this.addVisitBtn.click();
        return this;
    }

    public CalendarPage selectPark(Park park) {
        this.changeParkBtn.click();
        WebElement parkSelectionBtn = FirefoxSeleniumDriver.getDriver().findElement(By.id(park.getId() + "-park-selection"));
        parkSelectionBtn.click();
        return this;
    }

    @Override
    public CalendarPage then() {
        return this;
    }

	public CalendarPage clickAddNewVisitButton() {
        this.newVisitBtn.click();
        return this;
	}
}
