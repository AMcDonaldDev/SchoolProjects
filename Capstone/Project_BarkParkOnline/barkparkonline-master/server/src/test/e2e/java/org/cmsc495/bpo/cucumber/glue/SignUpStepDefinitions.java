package org.cmsc495.bpo.cucumber.glue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.cmsc495.bpo.cucumber.drivers.FirefoxSeleniumDriver;
import org.cmsc495.bpo.cucumber.webpages.SignUpPage;
import org.cmsc495.bpo.dao.Credentials;
import org.cmsc495.bpo.dao.UserProfile;
import org.cmsc495.bpo.dao.interfaces.User;
import org.cmsc495.bpo.exceptions.UserNotFoundException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.function.ThrowingRunnable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SignUpStepDefinitions extends SpringIntegrationGlue {

    protected static final Logger log = LoggerFactory.getLogger(SignUpStepDefinitions.class);

    /** Sign Up Attributes */
    private final String FIRST_NAME = "Monty";
    private final String LAST_NAME = "Python";
    private final String PASSWORD = "hellocats";
    private final String EMAIL = "mony@python.com";
    private final String AREA = "303";
    private final String EXCHANGE = "419";
    private final String SUBSCRIBER = "3627";
    private final String DOG_NAME = "Barky";
    private final String DOG_BREED = "Beagle";

    @Before(value = "@signup")
    public void init() {
        serverUrl = "http://localhost:" + port;
        
        // Here, we integrate the Selenium framework to test against
        // the actual client app using Firefox 
        firefox = new FirefoxSeleniumDriver(serverUrl + "/");
    }

    protected boolean removeUser(String username) throws UserNotFoundException {
        return userService.removeUser(userService.getUser(username));
    }

    @Given("^i can access the sign up page$")
    public void signUpPageAccessible() throws Exception {
        SignUpPage signUpPage = SignUpPage.init();
        FirefoxSeleniumDriver.get().goTo(signUpPage);

        assertEquals(signUpPage.getUrl(), firefox.getCurrentUrl());
    }

    @Given("{string} is removed as a user")
    public void userIsRemoved(String username) {
        try {
            assertTrue(removeUser(username));
        }
        catch (UserNotFoundException e) {
            // User does not exist, so test passes
        }
    }

    @When("i complete the sign up form as {string}")
    public void signUpFormCompleted(String username) throws Exception {
        SignUpPage signUpPage = SignUpPage.init();
        FirefoxSeleniumDriver.get().goTo(signUpPage);

        signUpStepOne(username);
        signUpPage.getStepTwo().click();

        if (username.length() < 8) { // Username is invalid
            assertThrows(NoSuchElementException.class, () ->
            {
                signUpStepTwo(username);
                sleepFor(1);
                signUpPage.getStepThree().click();
            });
            return;
        }

        signUpStepTwo(username);
        sleepFor(1);
        signUpPage.getStepThree().click();
    }

    @When("i complete step 1 of the sign up form as {string}")
    public void signUpStepOne(String username) {
        SignUpPage signUpPage = SignUpPage.init();
        FirefoxSeleniumDriver.get().goTo(signUpPage);
        signUpPage.goToStep(1);

        // Step 1
        signUpPage.inputUsername(username);
        signUpPage.inputEmail(EMAIL);
        signUpPage.inputPassword(PASSWORD);
        signUpPage.inputPasswordCheck(PASSWORD);
        signUpPage.getPasswordCheck().sendKeys(Keys.TAB);

        assertEquals(username, signUpPage.getUsername().getAttribute("value"));
        assertEquals(PASSWORD, signUpPage.getPassword().getAttribute("value"));
        assertEquals(PASSWORD, signUpPage.getPasswordCheck().getAttribute("value"));
        assertEquals(EMAIL, signUpPage.getEmail().getAttribute("value"));
    }

    @When("i complete step 2 of the sign up form as {string}")
    public void signUpStepTwo(String username) {
        SignUpPage signUpPage = SignUpPage.init();
        FirefoxSeleniumDriver.get().goTo(signUpPage);
        signUpPage.goToStep(2);

        signUpPage.inputFirstName(FIRST_NAME);
        signUpPage.inputLastName(LAST_NAME);
        signUpPage.inputArea(AREA);
        signUpPage.inputExchange(EXCHANGE);
        signUpPage.inputSubscriber(SUBSCRIBER);
        signUpPage.getSubscriber().sendKeys(Keys.TAB);

        assertEquals(FIRST_NAME, signUpPage.getFirstName().getAttribute("value"));
        assertEquals(LAST_NAME, signUpPage.getLastName().getAttribute("value"));
    }

    @When("i go to step {int}")
    public void goToStep(int step) throws Exception {
        SignUpPage signUpPage = SignUpPage.init();
        signUpPage.goToStep(2);
        sleepFor(1);
        signUpPage.getFirstName().click();
        signUpPage.getFirstName().sendKeys(Keys.TAB);
    }

    @When("i set first name to {string}")
    public void signUpFirstNameSet(String firstName) throws Exception {
        SignUpPage signUpPage = SignUpPage.init();
        FirefoxSeleniumDriver.get().goTo(signUpPage);
        signUpPage.goToStep(2);

        signUpPage.inputFirstName(firstName);
        assertEquals(firstName, signUpPage.getFirstName().getAttribute("value"));
    }

    @When("i set the dog age to {string}")
    public void signUpDogAgeSet(String dogAge) throws Exception {
        SignUpPage signUpPage = SignUpPage.init();
        FirefoxSeleniumDriver.get().goTo(signUpPage);
        signUpPage.goToStep(3);

        signUpPage.getDogDob().clear();
        signUpPage.inputDogDob(dogAge);
    }

    @When("i set the dog name to {string}")
    public void signUpDogNameSet(String dogName) throws Exception {
        SignUpPage signUpPage = SignUpPage.init();
        FirefoxSeleniumDriver.get().goTo(signUpPage);
        signUpPage.goToStep(3);

        signUpPage.getDogName().clear();
        signUpPage.inputDogName(dogName + " ");
        signUpPage.getDogName().clear();
        signUpPage.inputDogName(dogName);
        assertEquals(dogName, signUpPage.getDogName().getAttribute("value"));
    }

    @When("i set the dog breed to {string}")
    public void signUpDogBreedSet(String dogBreed) throws Exception {
        SignUpPage signUpPage = SignUpPage.init();
        FirefoxSeleniumDriver.get().goTo(signUpPage);
        signUpPage.goToStep(3);
        sleepFor(1);

        signUpPage.getDogBreed().clear();
        signUpPage.inputDogBreed(dogBreed + " ");
        signUpPage.getDogBreed().clear();
        signUpPage.inputDogBreed(dogBreed);
        signUpPage.getDogDob().sendKeys(Keys.TAB);
        assertEquals(dogBreed, signUpPage.getDogBreed().getAttribute("value"));
    }

    @When("i set the password to {string}")
    public void signUpPassworsSet(String password) throws Exception {
        SignUpPage signUpPage = SignUpPage.init();
        FirefoxSeleniumDriver.get().goTo(signUpPage);
        signUpPage.goToStep(1);
        sleepFor(1);

        signUpPage.inputPassword(password);
        signUpPage.inputPasswordCheck(password);
        assertEquals(password, signUpPage.getPassword().getAttribute("value"));
        assertEquals(password, signUpPage.getPasswordCheck().getAttribute("value"));
    }

    @When("^i complete a dog profile$")
    public void dogProfileCompleted() throws Exception {
        SignUpPage signUpPage = SignUpPage.init();
        FirefoxSeleniumDriver.get().goTo(signUpPage);
        signUpPage.goToStep(3);

        signUpPage.inputDogName(DOG_NAME);
        signUpPage.inputDogBreed(DOG_BREED);
        signUpPage.inputDogDob("1/2/2003");
        signUpPage.getDogDob().sendKeys(Keys.TAB);

        assertEquals(DOG_NAME, signUpPage.getDogName().getAttribute("value"));
        assertEquals(DOG_BREED, signUpPage.getDogBreed().getAttribute("value"));
        assertFalse(signUpPage.getDogDob().getAttribute("value").equals(""));
    }

    @When("^i press the sign up button$")
    public void signUpBtnPressed() throws Exception {
        SignUpPage signUpPage = SignUpPage.init();
        FirefoxSeleniumDriver.get().goTo(signUpPage);

        // First try to reach the Sign Up Button by clicking on
        // the 'next' on step 3.
        try {
            signUpPage.getStepFour().click();

        } catch (ElementNotInteractableException e) {
            log.info("Could not reach Sign Up via Step 4 button. {}",
                "Trying to go to Step 4 directly");
        }
        signUpPage.goToStep(4);
        sleepFor(1);

        signUpPage.clickSignUpButton();
    }

    @Then("^i cannot press the sign up button$")
    public void cannotPressSignUpBtn() throws Exception {
        Exception e = assertThrows(NoSuchElementException.class, () ->
        {
            signUpBtnPressed();
        });
        e.printStackTrace();
    }

    @Then("{string} is signed up")
    public void userHasAccount(String username) throws Exception {
        sleepFor(4); // Prevent Race Condition
        User user = userService.getUser(username);
        UserProfile profile = user.getUserProfile();
        Credentials credentials = user.getCredentials();

        assertEquals(username, user.getUsername());
        assertEquals(FIRST_NAME, profile.getFirstName());
        assertEquals(LAST_NAME, profile.getLastName());
        assertEquals(EMAIL, credentials.getEmail()); 
        assertTrue(profile.getPhoneNumber().contains(AREA));
        assertTrue(profile.getPhoneNumber().contains(EXCHANGE));
        assertTrue(profile.getPhoneNumber().contains(SUBSCRIBER));
        assertEquals(1, profile.getDogs().size());
    }

    @Then("{string} does not have an account")
    public void userDoesNotHaveAccount(String username) throws Exception {
        sleepFor(2); // Prevent Race Condition
        assertThrows(UserNotFoundException.class, new ThrowingRunnable(){
			@Override
			public void run() throws Throwable {
                User user = userService.getUser(username);
                // Should not get here because throws
                assertNull(user);
			}
        });
    }

    @Then("i cannot procede to step {int}")
    public void cannotProcedeToStep(int step) throws Exception {
        SignUpPage signUpPage = SignUpPage.init();
        int currentStep = step - 1;
        signUpPage.goToStep(currentStep);
        assertThrows(Exception.class, () ->
        {
            signUpPage.pressNextForStep(step);
        });
    }

    @After(value = "@signup")
    public void shutdown() {
        firefox.closeWindow();
    }
}
