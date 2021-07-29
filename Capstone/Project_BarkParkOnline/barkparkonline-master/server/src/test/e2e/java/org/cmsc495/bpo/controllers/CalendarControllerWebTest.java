package org.cmsc495.bpo.controllers;

import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.result.DeleteResult;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doReturn;
import org.springframework.http.ResponseEntity;
import org.cmsc495.bpo.dao.Credentials;
import org.cmsc495.bpo.dao.BarkPark;
import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.UserProfile;
import org.cmsc495.bpo.dao.Visit;
import org.cmsc495.bpo.dao.DogProfile;
import javax.servlet.http.HttpServletRequest;
import org.cmsc495.bpo.dao.DogProfile.Gender;
import org.cmsc495.bpo.services.interfaces.UserService;
import org.json.JSONObject;
import org.mockito.Mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.cmsc495.bpo.Application;
import org.cmsc495.bpo.controllers.response.SchedulingMessage;
import org.cmsc495.bpo.controllers.response.ApiResponse.ApiResponseBody;
import org.cmsc495.bpo.dao.interfaces.Park;
import org.cmsc495.bpo.dao.interfaces.User;
import org.cmsc495.bpo.repositories.BarkParkRepository;
import org.cmsc495.bpo.repositories.BasicUserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import cucumber.deps.com.thoughtworks.xstream.mapper.Mapper.Null;



/**
 * Test with: gradle e2e --tests CalendarControllerWebTest
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@SuppressWarnings("all")
public class CalendarControllerWebTest {

    protected static final Logger log = LoggerFactory.getLogger(CalendarControllerWebTest.class);

    private UserController controller;

    @Autowired
    private BarkParkRepository parkRepo;

    String dummyParkId;

    @Autowired
    private BasicUserRepository userRepo;

    @Mock
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    StatusResultMatchers status = MockMvcResultMatchers.status();

    private ObjectWriter getWriter() {
        DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter("  ", DefaultIndenter.SYS_LF);
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
        printer.indentObjectsWith(indenter);
        printer.indentArraysWith(indenter);
        return mapper.writer(printer);
    }

    private String toJSON(Object o) throws JsonProcessingException {
        return getWriter().writeValueAsString(o);
    }

    private MockHttpServletRequestBuilder post(String url) {
        return MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON);
    }

    private MockHttpServletRequestBuilder put(String url) {
        return MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON);
    }

    private MockHttpServletRequestBuilder get(String url) {
        return MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON);
    }

    private MockHttpServletRequestBuilder withUserAuth(MockHttpServletRequestBuilder builder, User user) {
        String auth = String.format("%s:%s", user.getCredentials().getUsername(), user.getCredentials().getPassword());
        Base64.Encoder enc = Base64.getEncoder();
        String b64Auth = enc.encodeToString(auth.getBytes());
        return builder.header("Authorization", "Basic " + b64Auth);
    }

    SchedulingMessage doTransaction(MockHttpServletRequestBuilder builder, String body) throws Exception {
        User user = getDummyUser();
        String res = mvc.perform(withUserAuth(builder, user).content(body)).andExpect(status.isOk())
            .andReturn().getResponse().getContentAsString();
        return (SchedulingMessage) mapper.readValue(res, ApiResponseBody.class).getMessageAs(SchedulingMessage.class);
    }

    Visit getDummyVisit(String userId, String parkId) {
        User user = getDummyUser();
        DogProfile dog = getDummyDog();
        return new Visit()
            .withVisitorFullName(user.getUserProfile().getFullName())
            .withVisitorDogNames(Set.of(dog.getDogName()))
            .withDate(LocalDate.now())
            .withTime(LocalTime.now())
            .withParkId(parkId)
            .withVisitorId(userId);
    }

    DogProfile getDummyDog() {
        return new DogProfile()
            .withDogBreed("dog")
            .withDogGender(Gender.FEMALE)
            .withDogName("DummyDog")
            .withDogDob(LocalDate.of(2020, 1, 1));
    }

    BasicUser getDummyUser() {
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

    BarkPark getDummyPark() {
        return new BarkPark()
            .withName("DummyPark")
            .withStreetNumber("1000")
            .withStreetName("DummyStreet")
            .withCityName("Dummyville")
            .withStateName("Florida")
            .withZipCode(315);
    }

    long deleteDummyUser() {
        User dummy = getDummyUser();
        DeleteResult result = userRepo.delete(dummy.getUsername());
        if ( result != null ) return result.getDeletedCount();
        return 0;
    }

    long deleteDummyPark() {
        if ( dummyParkId != null ) {
            DeleteResult result = parkRepo.delete(dummyParkId);
            dummyParkId = null;
            if ( result != null ) return result.getDeletedCount();
        }
        return 0;
    }

    String getDummyParkId() throws Exception {
        if ( dummyParkId == null ) throw new Exception("dummyParkId not set");
        return dummyParkId;
    }

    String getDummyUserId() throws Exception {
        return userRepo.retrieve(getDummyUser().getUsername()).getId();
    }



    @Before
    public void init() throws Exception {
        deleteDummyUser();
        deleteDummyPark();
        
        BasicUser user = getDummyUser();
        BarkPark park = getDummyPark();
        String username = user.getCredentials().getUsername();

        mvc.perform(post("/user/signup").content(toJSON(user))).andExpect(status.isOk());

        mvc.perform(withUserAuth(get("/user/login"), user).param("username", username))
            .andExpect(status.isOk());

        parkRepo.create(park);
        dummyParkId = park.getId();
        log.info("BEFORE CREATED PARK {}", dummyParkId);
    }

    @Test
    public void testCalendar_editVisit() throws Exception {
        BasicUser user = getDummyUser();
        String parkId = getDummyParkId();
        Visit visit = getDummyVisit(getDummyUserId(), parkId);

        {   // Step 1 - create initial visit
            String url = "/calendar/scheduling/visit/create";
            String json = toJSON(visit);
            log.info(json);

            SchedulingMessage msg = doTransaction(post(url), json);
            ArrayList<LinkedHashMap> o = (ArrayList<LinkedHashMap>) msg.getMessage();
            visit.setVisitId((o.get(0).get("_id").toString()));
            assertTrue(msg.isAccepted(), "visit was not created");
        }
        {   // Step 2 - update visit
            String url = "/calendar/scheduling/visit/edit/" + visit.getVisitId();
            LocalDate expectedDate = visit.getDate().plusWeeks(2); 
            String json = new JSONObject()
                .put("date", expectedDate.toString())
                .put("visitId", visit.getVisitId())
                .toString();
            log.info(json);

            // Do update for date
            // Get a "clean" copy from the database
            Visit originalVisit = parkRepo.retrieveVisit(visit.getVisitId());
            SchedulingMessage msg = doTransaction(put(url), json);
            assertTrue(msg.isAccepted(), "visit update was not accepted");
            // Get a copy of the visit - this SHOULD have the new date
            Visit updatedVisit = parkRepo.retrieveVisit(visit.getVisitId());

            // Make sure we update the data to a later time
            assertEquals(expectedDate, updatedVisit.getDate(), "Update date did not match expected date");
        }

    }

    @Test
    public void testCalendar_scheduleVisit() throws Exception {
        // Stub
    }

    @Test
    public void testCalendar_unscheduleVisit() throws Exception {
        // Stub
    }

    @After
    public void cleanup() {
        log.info("AFTER DELETE {} USER", deleteDummyUser());
        log.info("AFTER DELETE {} PARK", deleteDummyPark());
    }
}
