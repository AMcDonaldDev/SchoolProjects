package org.cmsc495.bpo.cucumber.glue;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.bson.Document;
import org.cmsc495.bpo.controllers.response.SchedulingMessage;
import org.cmsc495.bpo.cucumber.drivers.FirefoxSeleniumDriver;
import org.cmsc495.bpo.cucumber.webpages.LoginPage;
import org.cmsc495.bpo.cucumber.webpages.CalendarPage;
import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.DogProfile;
import org.cmsc495.bpo.dao.UserProfile;
import org.cmsc495.bpo.dao.Visit;
import org.cmsc495.bpo.dao.interfaces.Park;
import org.cmsc495.bpo.dao.interfaces.User;
import org.cmsc495.bpo.exceptions.VisitNotFoundException;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class VisitSchedulingStepDefinitions extends SpringIntegrationGlue {
    protected static final Logger log = LoggerFactory.getLogger(VisitSchedulingStepDefinitions.class);

    protected Visit visit;
    protected User user;
    protected UserProfile profile;
    protected DogProfile dog;
    protected Park park;

    @Before(value = "@visitscheduling")
    public void schedulingInit() throws Exception {
        serverUrl = "http://localhost:" + port;
        visit = new Visit();
        
        // Here, we integrate the Selenium framework to test against
        // the actual client app using Firefox 
        firefox = new FirefoxSeleniumDriver(serverUrl + "/");
    }
    
    @Given("i login as {string} with password {string}")
    public void loginAs(String username, String password) throws Exception {
        LoginPage loginPage = LoginPage.init();
        FirefoxSeleniumDriver.get().goTo(loginPage);
        
        typeInUsername(username);
        typeInPassword(password);
        pressLogin();
        userLoggedIn(username);
    }

    @Given("i do not have a visit scheduled for {string} at {string}")
    public void iDontHaveAVisitScheduled(String date, String time) throws Exception {
        // This will throw an exception if there is a visit scheduled
        iHaveAVisitScheduled(date, time);
        visitIsUnscheduled();
    }

    @Given("i have a visit scheduled on {string} at {string}")
    @SuppressWarnings("all")
    public void iHaveAVisitScheduled(String date, String time) throws Exception {
        String [] dateArr = date.split("-");
        String [] timeArr = time.split(":");

        visit.setDate( LocalDate.of(
            Integer.parseInt(dateArr[0]), 
            Month.of(Integer.parseInt(dateArr[1])), 
            Integer.parseInt(dateArr[2]))
        );
        
        visit.setTime(LocalTime.of(
            Integer.parseInt(timeArr[0]), 
            Integer.parseInt(timeArr[1]))
        );

        park = parkRepo.getAll(0, 1, "name").iterator().next();
        visit.setParkId(park.getId());
        visit.setVisitorFullName(user.getUserProfile().getFullName());
        visit.setVisitorDogNames( Set.of(
            user.getUserProfile().getDogs().iterator().next().getDogName())
        );

        SchedulingMessage message = schedulingService.scheduleVisits(park, user, visit);
        assertTrue(message.getAccepted());

        ArrayList<Document> o = (ArrayList<Document>) message.getMessage();
        visit.setVisitId((o.get(0).get("_id").toString()));
        Visit savedVisit = schedulingService.findVisit(visit.getVisitId());
        assertNotNull(savedVisit);
        assertEquals(visit, savedVisit);
        log.info("Visit Id {} Scheduled Successfully", visit.getVisitId());
    }

    @Given("{string} has an account")
    public void hasAnAccount(String username) {
        assertDoesNotThrow(() -> {
            user = userService.getUser(username);
        });
    }

    @When("^i go to the calendar page$")
    public void goToCalendarPage() {
        FirefoxSeleniumDriver.get().goTo(CalendarPage.init());
    }

    @When("^i open the visit scheduler form$")
    public void openVisitSchedulerForm() {
        CalendarPage page = CalendarPage.init();

        FirefoxSeleniumDriver.get()
            .goTo(page)
            .then()
            .clickAddNewVisitButton();
    }

    @When("i open the visit scheduler")
    public void openVisitScheduler() {
        CalendarPage page = CalendarPage.init();
        FirefoxSeleniumDriver.get()
            .goTo(page)
            .then()
            .openVisitScheduler();
        sleepFor(1);
    }

    @When("^i select a random park$")
    public void selectRandomPark() {
        park = parkRepo.getAll(0, 1, "name").iterator().next();
        assertNotNull(park);
        
        FirefoxSeleniumDriver.get()
            .goTo(CalendarPage.init())
            .then()
            .selectPark(park);
        sleepFor(2);
    }

    @When("i fill out the arrival datetime as {string} at {string}")
    public void fillOutArrivalWith(String date, String time) {
        // Setup
        CalendarPage page = CalendarPage.init();
        String [] dateArr = date.split("-");
        String parsedDate = dateArr[1] + "/" + dateArr[2] + "/" + dateArr[0];

        FirefoxSeleniumDriver.get()
            .goTo(page)
            .then()
            .editNewVisitArrivingDateAndTime(parsedDate + ", " + time);
    }

    @When("i edit the visit")
    public void editVisit() {
        FirefoxSeleniumDriver.get()
            .goTo(CalendarPage.init())
            .then()
            .editVisit(visit.getVisitId());
    }

    @When("^i have selected no dog to bring$")
    public void selectedNoDogs() {
        CalendarPage page = CalendarPage.init();
        FirefoxSeleniumDriver.get()
            .goTo(page)
            .then()
            .openSelectDogDropdown()
            .then()
            .deselectAllOptions()
            .then()
            .closeSelectDogDropdown();
    }

    @When("i change the visit to {string} at {string}")
    public void changeVisitArriving(String date, String time) {
        // Setup
        String [] dateArr = date.split("-");
        String parsedDate = dateArr[1] + "/" + dateArr[2] + "/" + dateArr[0];

        FirefoxSeleniumDriver.get()
            .goTo(CalendarPage.init())
            .then()
            .editVisitArrivingDateAndTime(visit.getVisitId(), parsedDate + ", " + time)
            .then()
            .pressSaveButtonFor(visit.getVisitId());
        sleepFor(1);
    }

    @When("i have {string} selected as a dog to bring")
    public void dogSelected(String dogName) {
        CalendarPage page = CalendarPage.init();
        log.info("Dogs Selected: {}", page.getSelectDogDropdown().getText());
        assertTrue(page.getSelectDogDropdown().getText().contains(dogName));
    }

    @When("^i press the add visit button$") 
    public void pressAddVisitButton() {
        FirefoxSeleniumDriver.get()
            .goTo(CalendarPage.init())
            .then()
            .clickAddVisitButton();
        sleepFor(1);
    }    

    @Then("the visit has been changed to {string} at {string}")
    public void visitHasBeenChangedTo(String date, String time) {
        String [] dateArr = date.split("-");
        String [] timeArr = time.split(":");

        LocalDate changedDate = LocalDate.of(
            Integer.parseInt(dateArr[0]), 
            Month.of(Integer.parseInt(dateArr[1])), 
            Integer.parseInt(dateArr[2])
        );
        
        LocalTime changedTime = LocalTime.of(
            Integer.parseInt(timeArr[0]), 
            Integer.parseInt(timeArr[1])
        );

        Visit fromRepo = parkRepo.retrieveVisit(visit.getVisitId());
        assertEquals(changedDate, fromRepo.getDate());
        assertEquals(changedTime, fromRepo.getTime());
    }

    @When("^i delete the visit$")
    public void deleteVisit() {
        FirefoxSeleniumDriver.get()
            .goTo(CalendarPage.init())
            .then()
            .pressDeleteButtonFor(visit.getVisitId());
    }

    @Then("the visit is still {string} at {string}")
    public void visitHasNotChanged(String date, String time) {  
        assertDoesNotThrow(() -> {
            this.visitHasBeenChangedTo(date, time);
        });
    }

    @Then("the visit is unscheduled")
    public void visitIsUnscheduled() {
        park = parkRepo.retrieve(park.getId());
        schedulingService.unscheduleVisits(park, user, visit);
        assertThrows(VisitNotFoundException.class, () -> {
            schedulingService.findVisit(visit.getVisitId());
        });
    }

    @Then("my visit is not listed on the profile of {string}")
    public void visitNotListedOnProfile(String username) {
        assertThrows(VisitNotFoundException.class, () -> {
            this.visitListedOnProfile(username);
        });
    }

    @Then("my visit is not scheduled in the park")
    public void visitNotScheduledToPark() {
        assertThrows(VisitNotFoundException.class, () -> {
            this.visitScheduledToPark();
        });
    }

    /**
     * 
     * Then my visit is not listed on the profile of "jonsnow" And my visit is not
     * scheduled in the park
     * 
     * @throws VisitNotFoundException
     */
    @Then("my visit is scheduled in the park")
    public void visitScheduledToPark() throws VisitNotFoundException {
        Visit repoVisit = parkRepo.retrieveVisit(visit.getVisitId());
        if (repoVisit == null) {
            throw new VisitNotFoundException(visit.getVisitId());
        }
        assertEquals(visit, repoVisit);
    }

    @Then("my visit is listed on the profile of {string}")
    public void visitListedOnProfile(String username) throws VisitNotFoundException {
        BasicUser basicUser = userRepo.retrieve(username);
        
        Set<Visit> userVisits = new HashSet<>();
        Set<String> userVisitIds = basicUser.getUserProfile().getVisitIds();

        for (String visitId : userVisitIds) {
            userVisits.add(parkRepo.retrieveVisit(visitId));
        }
        boolean visitFound = false;
        for (Visit userVisit : userVisits) {
            if (visit.equals(userVisit)) {
                visitFound = true;
                visit = userVisit;
            }
        }
        if (!visitFound) {
            throw new VisitNotFoundException(visit.getVisitId());
        }
        assertTrue(visitFound);
    }

    @Then("^i see my visit$") 
    public void seeVisit() {
        // If the visit can be edited, then it's definitely there
        assertDoesNotThrow(() -> {
            this.editVisit();
        }); 
    }

    @Then("^i do not see my visit$") 
    public void cannotSeeVisit() {
        // If the visit can be edited, then it's definitely there
        assertThrows(WebDriverException.class, () -> {
            this.editVisit();
        }); 
    }


    @After(value = "@visitscheduling")
    public void shutdown() {
        schedulingService.unscheduleVisits(park, user, visit);
        firefox.closeWindow();
    }
}
