package org.cmsc495.bpo.cucumber.glue;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.springframework.http.ResponseEntity;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.cmsc495.bpo.controllers.response.ServerStatus;
import org.cmsc495.bpo.controllers.response.ServerStatus.Status;
import org.cmsc495.bpo.cucumber.drivers.FirefoxSeleniumDriver;
import org.cmsc495.bpo.cucumber.webpages.LoginPage;
import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.Credentials;
import org.cmsc495.bpo.exceptions.UserNotFoundException;

/**
 * All Integration Step Definitions for Cucumber should be placed here to ensure
 * that there is no repitition of work. The Spring Application will automatically 
 * load so that a real context can be used for tests.
 */
public class LoginStepDefinitions extends SpringIntegrationGlue {

    protected static final Logger log = LoggerFactory.getLogger(LoginStepDefinitions.class);

    /** Basic User for Testing */
    protected final String JON_SNOW_USERNAME = "jonsnow";
    protected final String PASSWORD = "ghost4ever";
    protected final Credentials CREDENTIALS = new Credentials()
        .withUsername(JON_SNOW_USERNAME)
        .withPassword(PASSWORD);
    protected final BasicUser JON_SNOW = new BasicUser()
        .withCredentials(CREDENTIALS);

    @Before(value = "@login")
    public void init() {
        serverUrl = "http://localhost:" + port;
        
        // Here, we integrate the Selenium framework to test against
        // the actual client app using Firefox 
        firefox = new FirefoxSeleniumDriver(serverUrl + "/");
    }

    @Given("^the web server is online$")
    public void webServerOnline() {
        log.info("Server URL: {}", serverUrl);
        ResponseEntity<ServerStatus> response 
            = restTemplate.getForEntity(serverUrl  + "/" + "status" , ServerStatus.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(Status.UP_AND_RUNNING, response.getBody().getStatus());
    }

    @Given("^i can access the login page$")
    public void loginPageAccessible() throws Exception {
        LoginPage loginPage = LoginPage.init();
        FirefoxSeleniumDriver.get().goTo(loginPage);

        assertEquals(loginPage.getUrl(), FirefoxSeleniumDriver.get().getCurrentUrl());
    }

    @When("^i type in my username$")
    public void typeInUsername() throws Exception {
        typeInUsername(JON_SNOW_USERNAME);
    }

    @When("i type in my username as {string}")
    public void typeInUsernameAs(String username) throws Exception {
        LoginPage loginPage = LoginPage.init();
        FirefoxSeleniumDriver.get().goTo(loginPage);
        loginPage.inputUsername(username);

        assertThrows(UserNotFoundException.class, () -> {
            userService.getProfile(username);
        });
    }

    @When("^i type in my password$")
    public void typeInPassword() throws Exception {
        typeInPassword(PASSWORD);
    }

    @When("i type in my password as {string}")
    public void typeInPasswordAs(String password) throws Exception {
        LoginPage loginPage = LoginPage.init();
        FirefoxSeleniumDriver.get().goTo(loginPage);
        loginPage.inputPassword(password);

        assertNotEquals(loginPage.getPasswordInput().getAttribute("value"), PASSWORD);
    }

    @When("^i press the login button$")
    public void pressLoginBtn() {
        pressLogin();
    }

    @Then("^i am logged into the server$")
    public void userLoggedIn() {
        assertDoesNotThrow(() -> {
            userLoggedIn(JON_SNOW);
        });
    }

    @Then("^i am not logged into the server$")
    public void userNotLoggedIn() throws Exception {
        boolean NotLoggedIn = userService.getCurrentUsers().isEmpty();

        assertFalse(NotLoggedIn);
    }

    @After(value = "@login")
    public void shutdown() {
        firefox.closeWindow();
    }
}
