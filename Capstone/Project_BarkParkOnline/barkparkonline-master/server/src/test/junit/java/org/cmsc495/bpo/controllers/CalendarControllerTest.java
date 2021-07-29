package org.cmsc495.bpo.controllers;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.cmsc495.bpo.controllers.response.ApiResponse;
import org.cmsc495.bpo.controllers.response.SchedulingMessage;
import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.GuestUser;
import org.cmsc495.bpo.dao.UserProfile;
import org.cmsc495.bpo.dao.Visit;
import org.cmsc495.bpo.dao.interfaces.Park;
import org.cmsc495.bpo.dao.interfaces.VisitCalendarModel;
import org.cmsc495.bpo.services.interfaces.SchedulingService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalendarControllerTest {
    protected static final Logger log = LoggerFactory.getLogger(CalendarControllerTest.class);

    private CalendarController controller;

    @Mock
    private SchedulingService schedulingService;

    @Mock
    private GuestUser guest;

    @Mock
    private SchedulingMessage SchedulingMessage;

    @Mock 
    private SchedulingMessage message;

    @Mock
    private Park park;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = spy(new CalendarController(schedulingService));
        doReturn(guest).when(controller).newGuest(any());
    }

    @Test
    public void testGetCalendarModel() throws Exception {
        final String PARK_ID = "kamdkn01293-ojnaksdna";
        int year = 2020;
        int month = 4;
        VisitCalendarModel model = mock(VisitCalendarModel.class);

        doReturn(model).when(schedulingService).getCalendar(PARK_ID, year, month);

        ApiResponse response = controller.getCalendarModel(year, month, PARK_ID);
        assertEquals(model, response.getBody().getMessage());
    }

    @Test
    public void testGetCalendarModel_butModelIsNull() throws Exception {
        final String PARK_ID = "kamdkn01293-ojnaksdna";

        doReturn(null).when(schedulingService).getCalendar(PARK_ID, 1, 2);
        ApiResponse response = controller.getCalendarModel(1, 2, PARK_ID);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(404, response.getBody().getStatusCode());
    }

    @Test
    public void testCreateVisit() throws Exception {
        Visit visit = mock(Visit.class);
        BasicUser chrisTucker = mock(BasicUser.class);
        UserProfile profile = mock(UserProfile.class);
        final String USERNAME = "Chris Tucker";
        final String PARK_ID = "Rush Hour";
        HttpServletRequest request = mock(HttpServletRequest.class);
        SchedulingMessage message = mock(SchedulingMessage.class);

        doReturn("192.168.0.1").when(request).getRemoteAddr();
        doReturn(message).when(schedulingService).scheduleVisits(any(), any(), any());
        doReturn(PARK_ID).when(visit).getParkId();
        doReturn(park).when(schedulingService).findPark(PARK_ID);
        doReturn(profile).when(chrisTucker).getUserProfile();
        doReturn(USERNAME).when(profile).getFullName();
        
        ApiResponse response = controller.createVisit(visit, chrisTucker, request);
        assertTrue(response.getBody().getMessage() instanceof SchedulingMessage); 
        verify(schedulingService, times(1)).scheduleVisits(any(), any(), any());
    }

    @Test
    public void testEditVisit() throws Exception {
        String visitId = "Kung Fu";
        Visit visit = mock(Visit.class);
        Principal principal = mock(Principal.class);
        BasicUser jackieChan = mock(BasicUser.class);
        String username = "Jackie Chan";
        HttpServletRequest request = mock(HttpServletRequest.class);
        SchedulingMessage message = mock(SchedulingMessage.class);

        doReturn("192.168.0.1").when(request).getRemoteAddr();
        doReturn(username).when(principal).getName();
        doReturn(message).when(schedulingService).editVisit(visitId, visit);
        
        ApiResponse response = controller.editVisit(visitId, visit, jackieChan, request);
        assertTrue(response.getBody().getMessage() instanceof SchedulingMessage);
        verify(schedulingService, times(1)).editVisit(visitId, visit);
    }

    @Test
    public void testRemoveVisit() throws Exception {
        Visit visit = mock(Visit.class);
        BasicUser tomCruise = mock(BasicUser.class);
        final String VISIT_ID = "Mission Not Impossible";
        final String USERNAME = "Tom Cruise";
        final String PARK_ID = "sdjoad012b0-asfjnasf-dasda";
        HttpServletRequest request = mock(HttpServletRequest.class);
        SchedulingMessage message = mock(SchedulingMessage.class);

        doReturn("192.168.0.1").when(request).getRemoteAddr();
        doReturn(USERNAME).when(tomCruise).getUsername();
        doReturn(visit).when(schedulingService).findVisit(VISIT_ID);
        doReturn(PARK_ID).when(visit).getParkId();
        doReturn(park).when(schedulingService).findPark(PARK_ID);
        doReturn(message).when(schedulingService).unscheduleVisits(any(), any(), any());
        
        ApiResponse response = controller.removeVisit(null, VISIT_ID, tomCruise, request);
        assertTrue(response.getBody().getMessage() instanceof SchedulingMessage);
        verify(schedulingService, times(1)).unscheduleVisits(any(), any(), any());
    }

    @Test
    public void testGetVisit() throws Exception {
        Visit visit = mock(Visit.class);
        String visitId = "Mission Not Impossible";
        
        doReturn(visit).when(schedulingService).findVisit(visitId);
        ApiResponse response = controller.getVisit(visitId);
        assertEquals(visit, response.getBody().getMessage());
        verify(schedulingService, times(1)).findVisit(visitId);
    }
}
