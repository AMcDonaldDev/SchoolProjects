package org.cmsc495.bpo.cucumber.glue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.cmsc495.bpo.Application;
import org.cmsc495.bpo.cucumber.drivers.FirefoxSeleniumDriver;
import org.cmsc495.bpo.cucumber.webpages.LoginPage;
import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.Credentials;
import org.cmsc495.bpo.dao.DogProfile;
import org.cmsc495.bpo.dao.UserProfile;
import org.cmsc495.bpo.dao.DogProfile.Gender;
import org.cmsc495.bpo.dao.interfaces.User;
import org.cmsc495.bpo.repositories.BarkParkRepository;
import org.cmsc495.bpo.repositories.BasicUserRepository;
import org.cmsc495.bpo.services.ParkSchedulingService;
import org.cmsc495.bpo.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringIntegrationGlue {
    @LocalServerPort
    protected int port;
    protected RestTemplate restTemplate = new RestTemplate();

    @Value(value = "${spring.server.url}")
    protected String serverUrl;

    protected FirefoxSeleniumDriver firefox;
    protected BasicUser DUMMY_USER;

    @Autowired
    protected UserService userService;

    @Autowired
    protected BarkParkRepository parkRepo;

    @Autowired
    protected BasicUserRepository userRepo;

    @Autowired
    protected ParkSchedulingService schedulingService;

    /**
     * Sleeps the current thread for X seconds. Useful for e2e tests when watching
     * the Browser.
     * 
     * @param seconds
     */
    protected void sleepFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception e) {
        }
    }

    protected void sleepForMs(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
        }
    }

    protected void userLoggedIn(String username) throws Exception {
        BasicUser user = new BasicUser()
            .withCredentials(new Credentials().withUsername(username));
        userLoggedIn(user);
    }

    protected void userLoggedIn(User user) throws Exception {
        boolean loggedIn = userService.getCurrentUsers().contains(user);
        if (!loggedIn) {  
            sleepFor(5); // in case of race condition
            loggedIn = userService.getCurrentUsers().contains(user);
        }
        assertTrue(loggedIn);
    }

    protected void typeInPassword(final String PASSWORD) {
        LoginPage loginPage = LoginPage.init();
        FirefoxSeleniumDriver.get().goTo(loginPage);
        loginPage.inputPassword(PASSWORD);

        assertEquals(
            PASSWORD, 
            loginPage.getPasswordInput().getAttribute("value")
        );
    }

    protected void typeInUsername(final String USERNAME) {
        LoginPage loginPage = LoginPage.init();
        FirefoxSeleniumDriver.get().goTo(loginPage);
        loginPage.inputUsername(USERNAME);

        assertEquals(
            USERNAME, 
            loginPage.getUsernameInput().getAttribute("value")
        );
    }

    protected void pressLogin() {
        LoginPage loginPage = LoginPage.init();
        FirefoxSeleniumDriver.get().goTo(loginPage);

        loginPage.clickLoginButton();
        sleepFor(1);
    }


    protected DogProfile getDummyDog() {
        return getDummyDog("DEFAULT");
    }

    protected DogProfile getDummyDog(String name) {
        return new DogProfile()
        .withDogBreed("dog")
        .withDogGender(Gender.FEMALE)
        .withDogName(name)
        .withDogDob(LocalDate.of(2020, 1, 1));
    }

    protected BasicUser getDummyUser() {
        return new BasicUser()
        .withCredentials(new Credentials()
        .withEmail("dummy@example.com")
        .withPassword("dummypassword0123")
        .withUsername("DummyUser"))
        .withUserProfile(new UserProfile()
        .withFirstName("Dummy")
        .withLastName("TheUser")
        .withPhoneNumber("123-456-7890")
        .withDog(getDummyDog()));
    }    
}
