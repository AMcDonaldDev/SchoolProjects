package org.cmsc495.bpo.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ValidationException;

import org.cmsc495.bpo.controllers.response.SchedulingMessage;
import org.cmsc495.bpo.dao.BarkPark;
import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.CalendarMonth;
import org.cmsc495.bpo.dao.Credentials;
import org.cmsc495.bpo.dao.DogProfile;
import org.cmsc495.bpo.dao.UserProfile;
import org.cmsc495.bpo.dao.Visit;
import org.cmsc495.bpo.dao.DogProfile.Gender;
import org.cmsc495.bpo.dao.interfaces.Park;
import org.cmsc495.bpo.dao.interfaces.User;
import org.cmsc495.bpo.exceptions.ParkNotFoundException;
import org.cmsc495.bpo.exceptions.VisitNotFoundException;
import org.cmsc495.bpo.repositories.BarkParkRepository;
import org.cmsc495.bpo.services.interfaces.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ParkSchedulingServiceTest {
    
    private ParkSchedulingService service;

    @Mock
    private BarkParkRepository repo;

    @Mock
    private UserService userService;

    @Mock
    private BarkPark park;

    @Mock
    private Set<Visit> visits;

    @Mock
    private Visit VISIT_1;

    @Mock
    private Visit VISIT_2;

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
            .withId("1234567890")
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
        service = spy(new ParkSchedulingService(repo, userService));
    }

    @Test
    @SuppressWarnings("all")
    public void testGetCalendar() throws Exception {
        final String PARK_ID = "ijand0832hbn-3h2";
        final int MONTH = 1;
        final int YEAR = 2012;
        final LocalDate VISIT_1_DATE = LocalDate.of(YEAR-1, MONTH+2, 1);
        final LocalDate VISIT_2_DATE = LocalDate.of(YEAR, MONTH, 1);

        Iterator<Visit> iterator = mock(Iterator.class);
        CalendarMonth model = mock(CalendarMonth.class);

        doReturn(visits).when(park).getVisits();
        doReturn(park).when(repo).retrieve(PARK_ID);
        doReturn(iterator).when(visits).iterator();
        doReturn(true, true, false).when(iterator).hasNext();
        doReturn(VISIT_1, VISIT_2).when(iterator).next();

        doReturn(VISIT_1_DATE).when(VISIT_1).getDate();
        doReturn(VISIT_2_DATE).when(VISIT_2).getDate();
        doReturn(model).when(service).createModel(YEAR, MONTH, visits);

        service.getCalendar(PARK_ID, YEAR, MONTH);
        verify(iterator, times(1)).remove();
        service = new ParkSchedulingService(repo, userService);
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
        String parkId = visit.getParkId();

        { // Case 1 - Everything works
            doReturn(visit).when(repo).retrieveVisit(visitId);
            doReturn(park).when(repo).retrieve(parkId);
            SchedulingMessage message = service.editVisit(visitId, updatedVisit);
            assertTrue(message.isAccepted());
        }

        { // Case 2 - No visit for visitId throw VisitNotFoundException
            doReturn(null).when(repo).retrieveVisit(visitId);
            doReturn(park).when(repo).retrieve(parkId);
            assertThrows(VisitNotFoundException.class, new ThrowingRunnable(){
				@Override
				public void run() throws Throwable {
                    service.editVisit(visitId, updatedVisit);
				}
            });
        }

        { // Case 3 - Invalid Visit fields throws ValidationException
            doReturn(visit).when(repo).retrieveVisit(visitId);
            doReturn(park).when(repo).retrieve(parkId);
            updatedVisit.withVisitorDogNames(Set.of());
            assertThrows(ValidationException.class, new ThrowingRunnable(){
				@Override
				public void run() throws Throwable {
                    service.editVisit(visitId, updatedVisit);
				}
            });
        }
    }

    @Test
    @SuppressWarnings("all")
    public void testScheduleVisits() throws Exception {
        final String VISITOR_ID = "Boba Fett";
        final String VISIT_ID = "sl@v31";
        final String VISIT_ID_NEW = "s@rl@c";
        User boba = mock(User.class);
        UserProfile profile = mock(UserProfile.class);
        Set<String> visitIds = new HashSet<String>();
        Iterator<Visit> newVisitsIter = mock(Iterator.class);
        visitIds.add(VISIT_ID);

        doReturn(visits).when(repo).addVisits(eq(park), any());
        doReturn(newVisitsIter).when(visits).iterator();
        doReturn(true, false, true, false).when(newVisitsIter).hasNext();
        doReturn(VISIT_1).when(newVisitsIter).next();
        doReturn(1).when(visits).size();
        doReturn(profile).when(boba).getUserProfile();
        doReturn(visitIds).when(profile).getVisitIds();
        doReturn(VISIT_ID_NEW).when(VISIT_1).getVisitId();
        doReturn(VISITOR_ID).when(boba).getId();

        SchedulingMessage schedulingMessage = service.scheduleVisits(park, boba, VISIT_1);

        assertTrue(schedulingMessage.getAccepted());
        verify(VISIT_1).setVisitorId(VISITOR_ID);
        assertTrue(visitIds.contains(VISIT_ID_NEW));
    }

    @Test
    @SuppressWarnings("all")
    public void testUnscheduleVisit() throws Exception {
        final String VISITOR_ID = "Boba Fett";
        final String VISIT_ID = "sl@v31";
        User boba = mock(User.class);
        UserProfile profile = mock(UserProfile.class);
        Set<String> visitIds = new HashSet<String>();
        Iterator<Visit> removedVisitsIter = mock(Iterator.class);
        visitIds.add(VISIT_ID);

        doReturn(visits).when(repo).removeVisits(eq(park), any());
        doReturn(removedVisitsIter).when(visits).iterator();
        doReturn(true, false, true, false).when(removedVisitsIter).hasNext();
        doReturn(VISIT_1).when(removedVisitsIter).next();
        doReturn(1).when(visits).size();
        doReturn(profile).when(boba).getUserProfile();
        doReturn(visitIds).when(profile).getVisitIds();
        doReturn(VISIT_ID).when(VISIT_1).getVisitId();
        doReturn(VISITOR_ID).when(boba).getId();

        SchedulingMessage unschedulingMessage = service.unscheduleVisits(park, boba, VISIT_1);

        assertTrue(unschedulingMessage.getAccepted());
        assertFalse(visitIds.contains(VISIT_ID));
    }

    @Test
    public void testFindPark() throws Exception {
        final String PARK_ID = "9210je09jad";
        doReturn(park).when(repo).retrieve(PARK_ID);

        Park returned = service.findPark(PARK_ID);
        assertEquals(park, returned);
    }

    @Test
    public void testFindPark_butNotFound() throws Exception {
        final String PARK_ID = "9210je09jad";
        doReturn(null).when(repo).retrieve(PARK_ID);
        assertThrows(ParkNotFoundException.class, () -> {
            service.findPark(PARK_ID);
        });
    }

    @Test
    public void testFindPark_butException() throws Exception {
        final String PARK_ID = "9210je09jad";
        doThrow(NullPointerException.class).when(repo).retrieve(PARK_ID);
        assertThrows(ParkNotFoundException.class, () -> {
            service.findPark(PARK_ID);
        });
    }
}
