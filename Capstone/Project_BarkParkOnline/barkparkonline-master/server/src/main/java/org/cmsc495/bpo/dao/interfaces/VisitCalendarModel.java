package org.cmsc495.bpo.dao.interfaces;

import java.time.LocalDate;
import java.util.Collection;

import org.cmsc495.bpo.dao.Visit;

/**
 * The implementing class should be a Time Range Representation
 * of a Calendar. For example, a weekly calendar would only
 * return a time span of 7 days. A month would return a month worth
 * of days.
 */
public interface VisitCalendarModel {
    
    /**
     * Adds the given visit(s) to this Calendar Model.
     * 
     * @param visit
     */
    public void addVisits(Visit... visits);
    
    /**
     * Get all of the visits within this Calendar Model.
     * 
     * @return
     */
    public Collection<Visit> getVisits();

    /**
     * Get the start date for this model.
     * 
     * @return
     */
    public LocalDate getStartDate();

    /**
     * Get the end date for this model.
     * 
     * @return
     */
    public LocalDate getEndDate();
}
