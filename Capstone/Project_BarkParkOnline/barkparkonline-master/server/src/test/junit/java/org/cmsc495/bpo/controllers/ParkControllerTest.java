package org.cmsc495.bpo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.Collection;

import org.cmsc495.bpo.dao.BarkPark;
import org.cmsc495.bpo.repositories.BarkParkRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

public class ParkControllerTest {
    private ParkController controller;

    @Mock
    private BarkParkRepository repo;

    @Mock
    private BarkPark park;

    @Mock
    private Collection<BarkPark> parks;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new ParkController(repo);
    }

    @Test
    public void testCreatePark() throws Exception {
        controller.addNewPark(park);
        verify(repo).create(park);
    }

    @Test
    public void testGetAll() throws Exception {
        final int OFFSET = 0;
        final int LIMIT = 10;
        final String SORT_FIELD = "name";

        doReturn(parks).when(repo).getAll(OFFSET, LIMIT, SORT_FIELD);
        ResponseEntity<?> response = controller.getAllParks(OFFSET, LIMIT, SORT_FIELD);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(parks, response.getBody());
        verify(repo).getAll(OFFSET, LIMIT, SORT_FIELD);
    }
}
