package org.cmsc495.bpo.cucumber.webpages;

import org.cmsc495.bpo.cucumber.drivers.FirefoxSeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignUpPage implements WebPage {
    protected static final Logger log = LoggerFactory.getLogger(SignUpPage.class);
    private static final String URL = WebPage.BASE_URL + "/signup";

    @FindBy(id = "username")
    private WebElement username;

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "password-check")
    private WebElement passwordCheck;

    @FindBy(id = "first-name")
    private WebElement firstName;

    @FindBy(id = "last-name")
    private WebElement lastName;

    @FindBy(id = "area")
    private WebElement area;

    @FindBy(id = "exchange")
    private WebElement exchange;

    @FindBy(id = "subscriber")
    private WebElement subscriber;

    @FindBy(id = "profile-photo-url")
    private WebElement profilePhotoUrl;

    @FindBy(id = "dog-name")
    private WebElement dogName;

    @FindBy(id = "dog-dob")
    private WebElement dogDob;

    @FindBy(id = "dog-breed")
    private WebElement dogBreed;

    @FindBy(id = "sign-up-btn")
    private WebElement signUpButton;

    @FindBy(id = "step-2")
    private WebElement stepTwo;

    @FindBy(id = "step-3")
    private WebElement stepThree;

    @FindBy(id = "step-4")
    private WebElement stepFour;

    private WebDriver driver;

    private SignUpPage() {
        this.driver = FirefoxSeleniumDriver.getDriver();
        PageFactory.initElements(this.driver, this);
    }

    /**
     * Initializes the LoginPage with Selenium's Configs
     */
    public static SignUpPage init() {
        SignUpPage page = new SignUpPage();
        return page;
    }

    @Override
    public String getUrl() {
        return URL;
    }

    public SignUpPage inputUsername(String username) {
        this.username.sendKeys(username);
        return this;
    }

    public SignUpPage inputPassword(String password) {
        this.password.clear();
        this.password.sendKeys(password);
        return this;
    }

    public SignUpPage inputPasswordCheck(String password) {
        this.passwordCheck.clear();
        this.passwordCheck.sendKeys(password);
        return this;
    }

    public SignUpPage inputFirstName(String firstName) {
        this.firstName.clear();
        this.firstName.sendKeys(firstName);
        return this;
    }

    public SignUpPage inputLastName(String lastName) {
        this.lastName.sendKeys(lastName);
        return this;
    }

    public SignUpPage inputEmail(String email) {
        this.email.sendKeys(email);
        return this;
    }

    public SignUpPage inputProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl.sendKeys(profilePhotoUrl);
        return this;
    }

    public SignUpPage inputDogName(String dogName) {
        this.dogName.sendKeys(dogName);
        return this;
    }

    public SignUpPage inputDogBreed(String dogBreed) {
        this.dogBreed.sendKeys(dogBreed);
        return this;
    }
    
    public SignUpPage inputDogDob(String dob) {
        ((JavascriptExecutor) driver).executeScript("document.getElementById(\"dog-dob\").removeAttribute(\"readonly\")");
        this.dogDob.sendKeys(dob);
        return this;
    }

    public SignUpPage inputArea(String area) {
        this.area.sendKeys(area);
        return this;
    }

    public SignUpPage inputExchange(String exchange) {
        this.exchange.sendKeys(exchange);
        return this;
    }

    public SignUpPage inputSubscriber(String subscriber) {
        this.subscriber.sendKeys(subscriber);
        return this;
    }

    public SignUpPage clickStepTwo() {
        this.stepTwo.click();
        return this;
    }

    public SignUpPage clickStepThree() {
        this.stepThree.click();
        return this;
    }

    public SignUpPage clickStepFour() {
        this.stepFour.click();
        return this;
    }

    public SignUpPage clickSignUpButton() {
        signUpButton.click();
        return this;
    }

    public SignUpPage goToStep(int step) {
        int actualStepId = (step-1);
        WebElement signUpStep = driver.findElement(By.id("cdk-step-label-0-" + actualStepId));
        new Actions(driver).moveToElement(signUpStep).click().perform();
        return this;
    }

    public SignUpPage pressNextForStep(int step) {
        WebElement signUpStep = driver.findElement(By.id("step-" + step));
        signUpStep.click();
        return this;
    }

    public WebElement getUsername() {
        return this.username;
    }

    public WebElement getEmail() {
        return this.email;
    }

    public WebElement getPassword() {
        return this.password;
    }

    public WebElement getPasswordCheck() {
        return this.passwordCheck;
    }

    public WebElement getFirstName() {
        return this.firstName;
    }

    public WebElement getLastName() {
        return this.lastName;
    }

    public WebElement getProfilePhotoUrl() {
        return this.profilePhotoUrl;
    }

    public WebElement getDogName() {
        return this.dogName;
    }

    public WebElement getDogBreed() {
        return this.dogBreed;
    }

    public WebElement getDogDob() {
        return this.dogDob;
    }

    public WebElement getSignUpButton() {
        return this.signUpButton;
    }    
    
    public WebElement getArea() {
        return this.area;
    }

    public WebElement getExchange() {
        return this.exchange;
    }

    public WebElement getSubscriber() {
        return this.subscriber;
    }

    public WebElement getStepTwo() {
        return this.stepTwo;
    }

    public WebElement getStepThree() {
        return this.stepThree;
    }

    public WebElement getStepFour() {
        return this.stepFour;
    }

    @Override
    public SignUpPage then() {
        return this;
    }
}
