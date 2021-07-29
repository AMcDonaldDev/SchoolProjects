package org.cmsc495.bpo.services.interfaces;

import org.cmsc495.bpo.controllers.response.SchedulingMessage;
import org.cmsc495.bpo.dao.Visit;
import org.cmsc495.bpo.dao.interfaces.VisitCalendarModel;
import org.cmsc495.bpo.exceptions.ParkNotFoundException;
import org.cmsc495.bpo.exceptions.VisitNotFoundException;
import org.cmsc495.bpo.dao.interfaces.Park;
import org.cmsc495.bpo.dao.interfaces.User;

public interface SchedulingService {
    public static int MAX_YEAR = 2021;
    public static int MIN_YEAR = 2020;
    public static int MIN_MONTH = 1;
    public static int MAX_MONTH = 12;

    /**
     * Retrive a Calendar Model for the given Year and Month.
     * 
     * @param year
     * @param month
     * @return
     */
    public VisitCalendarModel getCalendar(String parkId, int year, int month);
    
    public SchedulingMessage scheduleVisits(Park park, User user, Visit... visits);

    public SchedulingMessage unscheduleVisits(Park park, User user, Visit... visits);

    public SchedulingMessage editVisit(String visitId, Visit updatedVisit) throws VisitNotFoundException, ParkNotFoundException ;

    public Park findPark(String parkId) throws ParkNotFoundException;

    public Visit findVisit(String visitId) throws VisitNotFoundException;
}
