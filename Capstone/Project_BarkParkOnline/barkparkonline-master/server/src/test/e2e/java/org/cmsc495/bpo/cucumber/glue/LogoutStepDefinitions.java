package org.cmsc495.bpo.cucumber.glue;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.cmsc495.bpo.cucumber.drivers.FirefoxSeleniumDriver;
import org.cmsc495.bpo.cucumber.webpages.HomePage;
import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.Credentials;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LogoutStepDefinitions extends SpringIntegrationGlue {

    @Before(value = "@logout")
    public void init() {
        serverUrl = "http://localhost:" + port;
        
        // Here, we integrate the Selenium framework to test against
        // the actual client app using Firefox 
        firefox = new FirefoxSeleniumDriver(serverUrl + "/");
    }

    @Given("i am identified as {string}")
    public void identifiedAs(final String FULL_NAME) {
        HomePage page = HomePage.init();
        WebElement loggedInAs = page.getLoggedInAs();

        FirefoxSeleniumDriver.get().goTo(page);
        assertEquals(FULL_NAME, loggedInAs.getText());
    }

    @Given("^i access the home page$")
    public void accessHomePage() {
        FirefoxSeleniumDriver.get().goTo(HomePage.init());
    }

    @When("^i try to click the dropdown toggle$")
    public void clickDropdownToggle() {
        HomePage page = HomePage.init();
        try {
            FirefoxSeleniumDriver.get().goTo(page)
                .then()
                .clickDropdownToggle();
        }
        catch (Exception e) {
            // Do Nothing
        }
    }

    @When("^i press the logout button$")
    public void pressLogout() {
        HomePage page = HomePage.init();

        FirefoxSeleniumDriver.get().goTo(page)
            .then()
            .clickLogoutButton();
    }

    @Then("^i am taken to the logout page$")
    public void takenToLogoutPage() {
        assertTrue(firefox.getCurrentUrl().endsWith("logout"));
    }

    @Then("{string} is logged out of the server")
    public void isLoggedOut(final String USERNAME) {
        BasicUser user = new BasicUser()
            .withCredentials(new Credentials().withUsername(USERNAME));
        assertFalse(userService.getCurrentUsers().contains(user));
    }

    @Then("^i cannot click logout$")
    public void cannotLogout() {
        HomePage page = HomePage.init();
        assertThrows(WebDriverException.class, () -> {
            page.clickLogoutButton();
        });
    }

    @After(value = "@logout")
    public void shutdown() {
        firefox.closeWindow();
    }
}
