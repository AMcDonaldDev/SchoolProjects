package org.cmsc495.bpo.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;



public class VisitTest {
    private Visit visit;

    private String id = "1234";

    @Before
    public void init() throws Exception {
        visit = new Visit();
        visit.setDate(LocalDate.of(2020, 1, 1));
        visit.setTime(LocalTime.of(23, 11));
    }

    public void testGetters() throws Exception {
        assertEquals(id, visit.getVisitId());
    }


    @Test
    public void testWithBuilders() throws Exception {
        visit = new Visit()
                .withVisitId(id);
        testGetters();
    }

    @Test
    public void testSetters() throws Exception {
        visit.setVisitId(id);
        testGetters();
    }

    @Test
    public void testEquals() throws Exception {
        Visit otherVisit = new Visit();
        otherVisit.setVisitorId("123");
        otherVisit.setDate(LocalDate.of(2020, 6, 1));
        otherVisit.setTime(LocalTime.of(4, 19));
        assertFalse(visit.equals(new Object()));
        assertFalse(otherVisit.equals(visit));
        assertTrue(visit.equals(visit));
    }

    @Test
    @SuppressWarnings("all")
    public void testHashCode() throws Exception {
        assertTrue(new Integer(visit.hashCode()) != null);
    }
}
