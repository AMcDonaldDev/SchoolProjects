package org.cmsc495.bpo.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ValidationException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


import org.bson.Document;
import org.cmsc495.bpo.controllers.response.SchedulingMessage;
import org.cmsc495.bpo.dao.BarkPark;
import org.cmsc495.bpo.dao.CalendarMonth;
import org.cmsc495.bpo.dao.Visit;
import org.cmsc495.bpo.dao.interfaces.VisitCalendarModel;
import org.cmsc495.bpo.exceptions.ParkNotFoundException;
import org.cmsc495.bpo.exceptions.UserNotFoundException;
import org.cmsc495.bpo.exceptions.VisitNotFoundException;
import org.cmsc495.bpo.dao.interfaces.Park;
import org.cmsc495.bpo.dao.interfaces.User;
import org.cmsc495.bpo.repositories.BarkParkRepository;
import org.cmsc495.bpo.services.interfaces.SchedulingService;
import org.cmsc495.bpo.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkSchedulingService implements SchedulingService {
    protected static final Logger log = LoggerFactory.getLogger(ParkSchedulingService.class);

    private BarkParkRepository repo;
    private UserService userService;

    @Autowired
    public ParkSchedulingService(BarkParkRepository repo, UserService userService) {
        this.repo = repo;
        this.userService = userService;
    }

    /**
     * Schedules a Visit(s) within the given Park and User. Returns a Scheduling Message
     * 
     */
    @Override
    public SchedulingMessage scheduleVisits(Park park, User user, Visit... visits) {
        for (Visit v : visits) v.setVisitorId(user.getId());
        boolean accepted = true;
        Set<Visit> newVisits = repo.addVisits((BarkPark) park, visits);
        if (newVisits.size() == 0) {
            log.warn("No new visits were scheduled for USER [{}]", user.getUsername());
            accepted = false;
        }
        for (Visit v : newVisits) {
            user.getUserProfile().getVisitIds().add(v.getVisitId());
        }

        try {
            userService.updateUserProfile(user, user.getUserProfile());
            
            Document scheduledMessage = new Document();
            List<Document> scheduledVisits = new ArrayList<>();
            for (Visit v : visits) {
                Document scheduledVisit = new Document();
                    scheduledVisit.put("_id", v.getVisitId());
                    scheduledVisit.put("info", v.toString());
                    scheduledVisit.put("scheduled", newVisits.contains(v));
                scheduledVisits.add(scheduledVisit);
            }
            scheduledMessage.put("visits", scheduledVisits);

            for (Visit v : newVisits) {
                log.info("Park [{}] has a new visit scheduled: {}", 
                        park.getName(), v.toString());
            }
            return new SchedulingMessage(accepted, scheduledVisits);

        } catch (ValidationException e) {
            return new SchedulingMessage(false, "User Profile corrupted for User [" + user.getUsername() + "]");

        } catch (UserNotFoundException e) {
            return new SchedulingMessage(false, e.getLocalizedMessage());
        }
    }

    /**
     * Unschedules a given Visit from the given Park and User. Returns a Scheduling Message
     * reporting the success or failure of this operation.
     */
    @Override
    public SchedulingMessage unscheduleVisits(Park park, User user, Visit... visits) {
        Set<Visit> removedVisits = repo.removeVisits((BarkPark) park, visits);
        boolean accepted = true;
        if (removedVisits.size() == 0) {
            log.warn("No visits were removed for USER [{}]", user.getUsername());
            accepted = false;
        }
        for (Visit v : removedVisits) {
            user.getUserProfile().getVisitIds().remove(v.getVisitId());
        }

        try {
            userService.updateUserProfile(user, user.getUserProfile());

            Document unscheduledMessage = new Document();
            List<Document> unscheduledVisits = new ArrayList<>();
            for (Visit v : visits) {
                Document scheduledVisit = new Document();
                    scheduledVisit.put("_id", v.getVisitId());
                    scheduledVisit.put("info", v.toString());
                    scheduledVisit.put("unscheduled", removedVisits.contains(v));
                unscheduledVisits.add(scheduledVisit);
            }
            unscheduledMessage.put("visits", unscheduledVisits);

            for (Visit v : removedVisits) {
                log.info("Park [{}] visit was unscheduled: {}", 
                        park.getName(), v.toString());
            }
            return new SchedulingMessage(accepted, unscheduledMessage);

        } catch (ValidationException e) {
            return new SchedulingMessage(false, "User Profile corrupted for User [" + user.getUsername() + "]");

        } catch (UserNotFoundException e) {
            return new SchedulingMessage(false, e.getLocalizedMessage());
        }
    }

    /**
     * Attempts to find a Park from the Data Base. If no Park is found, a
     * ParkNotFoundException will be thrown. This method never returns null.
     */
    @Override
    public Park findPark(String parkId) throws ParkNotFoundException {
        Park park = null;
        try {
            park = repo.retrieve(parkId);

        } catch (Exception e) {
            log.error("Park [{}] could not be retrieved from the Data Base. Reason: {}", parkId,
                    e.getLocalizedMessage());
            throw new ParkNotFoundException(parkId);
        }
        if (park == null) {
            log.error("Park [{}] could not be retrieved from the Data Base. {}", parkId, "Reason: Park doesn't exist");
            throw new ParkNotFoundException(parkId);
        }
        return park;
    }

    public BarkParkRepository getRepo() {
        return this.repo;
    }

    @Override
    public SchedulingMessage editVisit(String visitId, Visit updatedVisit) throws VisitNotFoundException, ParkNotFoundException {
        Visit visit = findVisit(visitId);
        BarkPark parkToUpdate = repo.retrieve(visit.getParkId());
        if (updatedVisit.getDate() != null)
            visit.setDate(updatedVisit.getDate());
        if (updatedVisit.getParkId() != null)
            visit.setParkId(updatedVisit.getParkId());
        if (updatedVisit.getTime() != null)
            visit.setTime(updatedVisit.getTime());
        if (updatedVisit.getVisitorDogNames() != null)
            visit.setVisitorDogNames(updatedVisit.getVisitorDogNames());
        if (updatedVisit.getVisitorFullName() != null)
            visit.setVisitorFullName(updatedVisit.getVisitorFullName());

        VisitValidator.validate(visit);

        getRepo().updateVisit(parkToUpdate, visit);

        return new SchedulingMessage(true, "Visit " + visitId + " updated.");
    }

    @Override
    public Visit findVisit(String visitId) throws VisitNotFoundException {
        Visit v = repo.retrieveVisit(visitId);
        if (v == null) {
            throw new VisitNotFoundException(visitId);
        }
        return v;
    }

    /**
     * Validator for validating a BasicUser
     */
    protected static class VisitValidator {
        protected static void validate(Visit visit) throws ValidationException {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Visit>> violations = validator.validate(visit);
            for (ConstraintViolation<Visit> violation : violations) {
                log.warn("{} VISIT [{}] : {}", visit.getVisitId(), violation.getMessage(), violation.getPropertyPath());
            }
            if (violations.size() > 0)
                throw new ValidationException("VISIT [" + visit.getVisitId() + "] had " +
                    violations.size() + " invalid fields");
        }
    }

    /**
     * Get a Calendar Model for the given Park, Month, and Year.
     */
    @Override
    public VisitCalendarModel getCalendar(String parkId, int year, int month) {
        Set<Visit> visits = this.repo.retrieve(parkId).getVisits();

        Iterator<Visit> iterator = visits.iterator();
        while(iterator.hasNext()) {
            Visit v = iterator.next();
            LocalDate date = v.getDate();

            if (date.getMonth().getValue() != month && date.getYear() != year) {
                iterator.remove();
            }
        }
        return createModel(year, month, visits);
    }

    /**
     * Stubbing for creating a Calendar Model for a Month
     * 
     * @param year
     * @param month
     * @param visits
     * @return
     */
    protected CalendarMonth createModel(int year, int month, Set<Visit> visits) {
        return new CalendarMonth().withMonth(month, year).withVisits(visits.toArray(new Visit[0]));
    }
}
