package org.cmsc495.bpo.controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import org.cmsc495.bpo.controllers.response.ApiResponse;
import org.cmsc495.bpo.controllers.response.SchedulingMessage;
import org.cmsc495.bpo.dao.BarkPark;
import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.Credentials;
import org.cmsc495.bpo.dao.DogProfile;
import org.cmsc495.bpo.dao.UserProfile;
import org.cmsc495.bpo.dao.Visit;
import org.cmsc495.bpo.dao.DogProfile.Gender;
import org.cmsc495.bpo.dao.interfaces.Park;
import org.cmsc495.bpo.dao.interfaces.User;
import org.cmsc495.bpo.exceptions.ParkNotFoundException;
import org.cmsc495.bpo.exceptions.VisitNotFoundException;
import org.cmsc495.bpo.services.interfaces.SchedulingService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalendarControllerVisitTest {
    protected static final Logger log = LoggerFactory.getLogger(CalendarControllerVisitTest.class);

    private CalendarController controller;

    @Mock
    private SchedulingService schedulingService;

    Visit getDummyVisit(User user, Park park) {
        return getDummyVisit(user.getId(), park.getId());
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

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new CalendarController(schedulingService);
    }

    @Test
    public void test_editVisit() throws Exception {
        User user = getDummyUser();
        Park park = getDummyPark();
        Visit visit = getDummyVisit(user, park);
        Visit updatedVisit = getDummyVisit(user, park)
            .withVisitId(visit.getVisitId())
            .withDate(visit.getDate().plusWeeks(2));
        String visitId = visit.getVisitId();
        BasicUser requester = mock(BasicUser.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        SchedulingMessage message = mock(SchedulingMessage.class);

        {
            doReturn(message).when(schedulingService).editVisit(visitId, updatedVisit);
            ApiResponse response = controller.editVisit(visitId, updatedVisit, requester, request);
            assertTrue(response.getStatusCode().is2xxSuccessful());
            assertEquals(message, response.getBody().getMessage());
        }

        {
            doThrow(ValidationException.class).when(schedulingService).editVisit(visitId, updatedVisit);
            ApiResponse response = controller.editVisit(visitId, updatedVisit, requester, request);
            assertTrue(response.getStatusCode().is2xxSuccessful());
            SchedulingMessage result = (SchedulingMessage)response.getBody().getMessage();
            assertFalse(result.isAccepted());
        }

        {
            doThrow(VisitNotFoundException.class).when(schedulingService).editVisit(visitId, updatedVisit);
            ApiResponse response = controller.editVisit(visitId, updatedVisit, requester, request);
            assertTrue(response.getStatusCode().is2xxSuccessful());
            SchedulingMessage result = (SchedulingMessage)response.getBody().getMessage();
            assertFalse(result.isAccepted());
        }

        {
            doThrow(ParkNotFoundException.class).when(schedulingService).editVisit(visitId, updatedVisit);
            ApiResponse response = controller.editVisit(visitId, updatedVisit, requester, request);
            assertTrue(response.getStatusCode().is2xxSuccessful());
            SchedulingMessage result = (SchedulingMessage)response.getBody().getMessage();
            assertFalse(result.isAccepted());
        }
    }
}
