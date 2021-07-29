package org.cmsc495.bpo.dao;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.cmsc495.bpo.dao.interfaces.VisitCalendarModel;

/**
 * Represents a Month with the Start Date being the first of the month,
 * and an End Date being the last day of the month. 
 */
public class CalendarMonth implements VisitCalendarModel {

    public Set<Visit> visits;

    public LocalDate startDate;

    public LocalDate endDate;

    public CalendarMonth() {
        this.visits = new HashSet<>();
    }

    @Override
    public void addVisits(Visit... visits) {
        for (Visit v : visits) {
            this.visits.add(v);
        }
    }

    @Override
    public Collection<Visit> getVisits() {
        return this.visits;
    }

    @Override
    public LocalDate getStartDate() {
        return this.startDate;
    }

    @Override
    public LocalDate getEndDate() {
        return this.endDate;
    }

    public CalendarMonth withMonth(int month, int year) {
        this.startDate = LocalDate.of(year, month, 1);
        this.endDate = LocalDate.of(year, month, 1).plusMonths(1).minusDays(1);
        return this;
    }

    public CalendarMonth withVisits(Visit... visits) {
        this.addVisits(visits);
        return this;
    }
}
