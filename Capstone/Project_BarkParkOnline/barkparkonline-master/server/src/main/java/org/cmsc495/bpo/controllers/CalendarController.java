package org.cmsc495.bpo.controllers;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.cmsc495.bpo.controllers.response.ApiResponse;
import org.cmsc495.bpo.controllers.response.SchedulingMessage;
import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.GuestUser;
import org.cmsc495.bpo.dao.Visit;
import org.cmsc495.bpo.dao.interfaces.VisitCalendarModel;
import org.cmsc495.bpo.exceptions.ParkNotFoundException;
import org.cmsc495.bpo.exceptions.VisitNotFoundException;
import org.cmsc495.bpo.services.interfaces.SchedulingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Here, we define a REST Controller to handle all requests related
 * to the Calendar to include: User Visits, Calendar Data, Park Info, etc.
 * 
 * <p>Because the Calendar is open to the public, these endpoints do not require
 * authentication in order to be reached.</p>
 */
@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class CalendarController {
    protected static final Logger log = LoggerFactory.getLogger(CalendarController.class);

    private SchedulingService schedulingService;
    
    @Autowired
    public CalendarController
    ( 
        SchedulingService schedulingService
    ) {
        this.schedulingService = schedulingService;
    }

    /**
     * Fetches the Calendar Model from the CalendarService and returns it in
     * the Response Body for the requester.
     */
    @GetMapping(path = "/calendar/{year}/{month}")
    @ResponseBody
    public ApiResponse getCalendarModel
    (
        @PathVariable(value = "year") Integer year,
        @PathVariable(value = "month") Integer month,
        @RequestParam(required = true, value = "id") String parkId
    ) {
        VisitCalendarModel model = schedulingService.getCalendar(parkId, year, month);
        if (model != null) {
            return ApiResponse.ok(model);
        }
        return ApiResponse.error(404, "Calendar Not Found");
    }
    
    /**
     * (Attempt to) Create a Visit and return a Scheduling Message to the requester.
     */
    @PostMapping(path = "/calendar/scheduling/visit/create")
    @ResponseBody
    public ApiResponse createVisit
    (
        @RequestBody @Valid Visit visit,
        BasicUser user,
        HttpServletRequest request
    ) {
        String parkId = visit.getParkId();
        generateVisitId(visit);
        try {       
            visit = visit.withVisitorFullName(user.getUserProfile().getFullName());

			return ApiResponse.ok(schedulingService.scheduleVisits(
                schedulingService.findPark(parkId),
                user,
                visit
            ));
                
		} catch (ParkNotFoundException pne) {
            return ApiResponse.error(404, newSchedulingMessage(false, "Park Not Found"));
            
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(500, newSchedulingMessage(false, "Database or Server Error"));
        }
    }

    /**
     * Utility method to generate a new Visit ID for the given Visit
     * 
     * @param visit
     */
    protected void generateVisitId(Visit visit) {
        visit.setVisitId(new Visit().getVisitId()); // Generate Visit ID
    }

    /**
     * (Attempt to) Edit a Visit specified by the visitId, then return a Scheduling
     * Message back to the requester.
     */
    @PutMapping(path = "/calendar/scheduling/visit/edit/{visitId}")
    @ResponseBody
    public ApiResponse editVisit
    (
        @PathVariable(value = "visitId") String visitId,
        @RequestBody Visit visit,
        BasicUser requester, 
        HttpServletRequest request
    ) {
        try {
            return ApiResponse.ok(schedulingService.editVisit(visitId, visit));

        } catch(ValidationException ex) {
            log.error("While updating visit:"+visitId, ex);
            return ApiResponse.error(400, newSchedulingMessage(false, ex.getMessage()));

        } catch(VisitNotFoundException ex) {
            log.error("While updating visit:"+visitId, ex);
            return ApiResponse.error(404, newSchedulingMessage(false, ex.getMessage()));

        } catch(ParkNotFoundException ex) {
            log.error("While updating visit:"+visitId, ex);
            return ApiResponse.error(404, newSchedulingMessage(false, ex.getMessage()));
        }
    }

    /**
     * (Attempt to) Remove a Visit specified by the given Visit ID,
     * then return a Scheduling Message to the requester.
     */
    @DeleteMapping(path = "/calendar/scheduling/visit/remove/{visitId}")
    @ResponseBody
    public ApiResponse removeVisit
    (
        @RequestParam(value = "parkId", required = false) String parkId,
        @PathVariable(value = "visitId") String visitId,
        BasicUser user,
        HttpServletRequest request
    ) {
        try {
            Visit v = schedulingService.findVisit(visitId);
            if (parkId == null) parkId = v.getParkId();
            return ApiResponse.ok(schedulingService.unscheduleVisits(
                schedulingService.findPark(parkId), 
                user,
                v
            ));
        } catch (ParkNotFoundException e) {
            return ApiResponse.error(404, newSchedulingMessage(
                false, 
                "Park [" + visitId + "] not found"
            ));

		} catch (VisitNotFoundException e) {
            return ApiResponse.error(404, newSchedulingMessage(
                false, 
                "Visit [" + visitId + "] not found"
            ));
        }
    }

    /**
     * (Attempt to) Retrieve a Visit specified by the given Visit ID
     * if the Visit exists.
     */
    @GetMapping(path = "/calendar/scheduling/visit/{visitId}")
    @ResponseBody
    public ApiResponse getVisit(@PathVariable(value = "visitId") String visitId) {
        try {
            return ApiResponse.ok(schedulingService.findVisit(visitId));
            
		} catch (VisitNotFoundException e) {
			log.warn("Visit {} not found", visitId);
		}
        return ApiResponse.error(404, newSchedulingMessage(false, "Visit [" + visitId + "] was not found"));
    }

    /**
     * Attempt to retrieve multiple visits by the given Visit ID's
     */
    @PostMapping(path = "/calendar/scheduling/visit/multiple")
    @ResponseBody
    public ApiResponse getVisits(@RequestBody(required = true) String [] visitIds) {
        Set<Visit> visits = new HashSet<>();
        for (String visitId : visitIds) {
            try {
                log.info("Looking for Visit {}", visitId);
                visits.add(schedulingService.findVisit(visitId));
            }
            catch (Exception e) {
                // Do nothing
                log.error("Visit {} not found. Reason: {}", visitId, e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        if (visits.isEmpty()) {
            return ApiResponse.error(404, "No Visits were found");
        }
        return ApiResponse.ok(visits);
    }

    /** Stubbing */
    protected SchedulingMessage newSchedulingMessage(boolean accepted, Object object) {
        return new SchedulingMessage(accepted, object);
    }
    protected GuestUser newGuest(String ip) { return new GuestUser(ip); }
}
