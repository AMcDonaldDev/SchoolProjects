package org.cmsc495.bpo.cucumber.glue;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.cmsc495.bpo.cucumber.drivers.FirefoxSeleniumDriver;
import org.cmsc495.bpo.cucumber.webpages.LoginPage;
import org.cmsc495.bpo.cucumber.webpages.ProfilePage;
import org.cmsc495.bpo.dao.DogProfile;
import org.cmsc495.bpo.dao.interfaces.User;
import org.cmsc495.bpo.exceptions.UserNotFoundException;

/**
 * All Integration Step Definitions for Cucumber should be placed here to ensure
 * that there is no repitition of work. The Spring Application will automatically 
 * load so that a real context can be used for tests.
 */
public class ProfileStepDefinitions extends SpringIntegrationGlue {
    protected static final Logger log = LoggerFactory.getLogger(ProfileStepDefinitions.class);

    @Before(value = "@profile")
    public void ensureDummyUserExists() throws Exception {
        DUMMY_USER = getDummyUser();
        try {
            User user = userService.getUser(DUMMY_USER.getUsername());
            userService.removeUser(user);
        } catch(UserNotFoundException ex) {
            // ...
        } finally {
            userService.addUser(DUMMY_USER.copy());
            User user = userService.getUser(DUMMY_USER.getUsername());
            DUMMY_USER.setId(user.getId());
        }
    }

    @Before(value = "@profile")
    public void init() {
        serverUrl = "http://localhost:" + port;
        
        // Here, we integrate the Selenium framework to test against
        // the actual client app using Firefox 
        firefox = new FirefoxSeleniumDriver(serverUrl + "/");
    }

    @Given("^i am logged in$")
    public void dummyUserIsLoggedIn() throws Exception {
        LoginPage page = LoginPage.init();
        FirefoxSeleniumDriver.get().goTo(page);
        page.getUsernameInput().clear();
        page.getPasswordInput().clear();
        log.info("ENTERING USERNAME: {}", DUMMY_USER.getUsername());
        page.inputUsername(DUMMY_USER.getUsername());
        log.info("ENTERING PASSWORD: {}", DUMMY_USER.getPassword());
        page.inputPassword(DUMMY_USER.getPassword());
        page.clickLoginButton();
        sleepForMs(1000);
        boolean loggedIn = userService.getCurrentUsers().contains(DUMMY_USER);
        if (!loggedIn) {  
            sleepFor(2);
            loggedIn = userService.getCurrentUsers().contains(DUMMY_USER);
        }
        assertTrue(loggedIn);
    }

    @Given("^i am on my profile page$")
    public void loginPageAccessible() throws Exception {
        ProfilePage page = ProfilePage.init();
        FirefoxSeleniumDriver.get().goTo(page);
        assertEquals(page.getUrl(), firefox.getCurrentUrl());
        sleepForMs(500);
    }

    @When("i click the +1") 
    public void clickPlusOne() {
        ProfilePage page = ProfilePage.init();
        page.addDogBtn.click();
        sleepForMs(1000);
        assertTrue("#save-dog-btn is visible", page.saveDogBtn.isDisplayed());
    }

    @When("i enter the name {string}") 
    public void enterDogName(String name) {
        ProfilePage page = ProfilePage.init();
        page.dogNameInput.clear();
        page.dogNameInput.sendKeys(name);
    }

    @When("i enter the breed {string}") 
    public void enterDogBreed(String breed) {
        ProfilePage page = ProfilePage.init();
        page.dogBreedInput.clear();
        page.dogBreedInput.sendKeys(breed);;
    }

    @When("i enter the birthday {string}") 
    public void enterDogDOB(String dob) {
        ProfilePage page = ProfilePage.init();
        page.dogDobInput.click();
        sleepForMs(500);
        page.dogDobPopup1st.click();
        sleepForMs(500);
    }

    @When("i select gender {string}") 
    public void enterDogGender(String gender) {
        log.info("Setting gender {}", gender);
        ProfilePage page = ProfilePage.init();
        page.dogGenderInputSelect.click();
        if ( "FEMALE".equals(gender) ) {
            page.dogGenderFemale.click();
        } else if ( "MALE".equals(gender) ) {
            page.dogGenderMale.click();
        }
        sleepForMs(500);
    }

    @When("i click save dog") 
    public void clickSaveDog() {
        ProfilePage page = ProfilePage.init();
        page.saveDogBtn.click();
    }

    @Then("dog {string} is added") 
    public void dogIsAdded(String name) throws UserNotFoundException {
        User user1 = userService.getUser(DUMMY_USER.getUsername());
        assertTrue(dogExists(user1, name));
    }

    @Then("dog {string} is not added") 
    public void dogIsNotAdded(String name) throws UserNotFoundException {
        User user1 = userService.getUser(DUMMY_USER.getUsername());
        assertFalse(dogExists(user1, name));
    }

    public boolean dogExists(User user1, String name) {
        for(DogProfile dog: user1.getUserProfile().getDogs()){
            if (dog.getDogName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @When("i select a dog {string} to update")
    public void selectDogFromEditDropdown(String dogname) {
        ProfilePage page = ProfilePage.init();
        assertTrue("Select dog from edit dropdown", page.selectDogFromEditDropdown(dogname));
        sleepForMs(250);
    }

    @When("i click update dog")
    public void clickUpdateDog() {
        ProfilePage page = ProfilePage.init();
        page.updateDogBtn.click();
        sleepForMs(250);
    }

    @Then("dog {string} {string} {string} is updated") 
    public void dogIsUpdated(String name, String breed, String gender) throws UserNotFoundException {
        User user1 = userService.getUser(DUMMY_USER.getUsername());
        assertTrue(infoMatch(user1, name, breed, gender));
    }

    @Then("dog {string} {string} {string} is not updated") 
    public void dogIsNotUpdated(String name, String breed, String gender) throws UserNotFoundException {
        User user1 = userService.getUser(DUMMY_USER.getUsername());
        assertFalse(infoMatch(user1, name, breed, gender));
    }

    public boolean infoMatch(User user1, String name, String breed, String gender) {
        for(DogProfile dog: user1.getUserProfile().getDogs())
            if (   dog.getDogName().equals(name)
                && dog.getDogBreed().equals(breed)
                && dog.getDogGender().toString().equals(gender)
            ) return true;
        return false;
    }

    @When("i click remove dog")
    public void clickRemoveDog() {
        ProfilePage page = ProfilePage.init();
        page.removeDogBtn.click();
        sleepForMs(250);
    }

    @Then("dog {string} is removed")
    public void dogIsRemoved(String name) throws UserNotFoundException {
        User user1 = userService.getUser(DUMMY_USER.getUsername());
        assertFalse(dogExists(user1, name));
    }

    @Then("dog {string} is in view")
    public void dogIsInView(String name) throws UserNotFoundException {
        sleepForMs(2000);
        ProfilePage page = ProfilePage.init();
        Boolean inView = page.dogNameInput.getAttribute("value").equals(name);
        assertTrue(inView);
    }


    @After(value = "@profile")
    public void shutdown() {
        firefox.closeWindow();
        try {
            if ( DUMMY_USER.getId() != null )
                userService.removeUser(DUMMY_USER);
        } catch(Exception ex) {
            // ...
        }
    }
}
