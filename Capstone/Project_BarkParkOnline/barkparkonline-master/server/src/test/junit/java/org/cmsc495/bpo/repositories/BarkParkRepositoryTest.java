package org.cmsc495.bpo.repositories;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Iterator;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.DeleteResult;

import org.cmsc495.bpo.dao.BarkPark;
import org.cmsc495.bpo.dao.Visit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public class BarkParkRepositoryTest {
    private BarkParkRepository repo;
    
    @Mock
    private ObjectMapper mapper;

    @Mock
    private MongoTemplate mongo;

    @Mock
    private BarkPark GAIA_PARK;

    @Mock
    private BarkPark GAIA_PARK_SAVED;

    @Mock
    private Visit VISIT_1;

    @Mock
    private Visit VISIT_2;

    @Mock 
    private Query visitIdQuery;

    @Mock
    private Set<Visit> visits;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        repo = spy(new BarkParkRepository(mongo, mapper));
    }

    @Test
    public void testCreate() throws Exception {
        doReturn(GAIA_PARK_SAVED).when(mongo).save(GAIA_PARK);
        assertEquals(GAIA_PARK_SAVED, repo.create(GAIA_PARK));
        verify(mongo).save(GAIA_PARK);
    }

    @Test
    public void testRetrieve() throws Exception {
        final String INDEX = "2913n9vns023inf0anf";

        doReturn(GAIA_PARK).when(mongo).findById(INDEX, BarkPark.class);
        assertEquals(GAIA_PARK, repo.retrieve(INDEX));
    }

    @Test
    public void testRetrieveVisit() throws Exception {
        final String VISIT_ID = "amskdm091u32ein20ehn";
        Set<Visit> visits = Set.of(VISIT_1, VISIT_2);

        doReturn("not the right id").when(VISIT_1).getVisitId();
        doReturn(VISIT_ID).when(VISIT_2).getVisitId();

        doReturn(visits).when(GAIA_PARK_SAVED).getVisits();
        doReturn(visitIdQuery).when(repo).queryVisitId(VISIT_ID);
        doReturn(GAIA_PARK_SAVED).when(mongo).findOne(visitIdQuery, BarkPark.class);

        assertEquals(VISIT_2, repo.retrieveVisit(VISIT_ID));
    }

    @Test
    public void testRetrieveVisit_butNull() throws Exception {
        final String VISIT_ID = "amskdm091u32ein20ehn";
        doReturn(visitIdQuery).when(repo).queryVisitId(VISIT_ID);
        doReturn(null).when(mongo).findOne(visitIdQuery, BarkPark.class);
        assertNull(repo.retrieveVisit(VISIT_ID));
    }

    @Test
    public void testAddVisits() throws Exception {
        doReturn(visits).when(GAIA_PARK).getVisits();
        doReturn(true, false).when(visits).add(any(Visit.class));

        Set<Visit> added = repo.addVisits(GAIA_PARK, VISIT_1, VISIT_2);

        assertTrue(added.contains(VISIT_1));
        assertFalse(added.contains(VISIT_2));
        verify(visits, times(2)).add(any(Visit.class));
    }

    @Test
    public void testUpdateVisit() throws Exception {
        doReturn(null).when(repo).removeVisits(eq(GAIA_PARK), any(Visit.class));
        doReturn(null).when(repo).addVisits(eq(GAIA_PARK), any(Visit.class));
        repo.updateVisit(GAIA_PARK, VISIT_1);
        verify(repo).removeVisits(GAIA_PARK, VISIT_1);
        verify(repo).addVisits(GAIA_PARK, VISIT_1);
        verify(mongo).save(GAIA_PARK);
    }

    @Test
    @SuppressWarnings("all")
    public void testRemoveVisits() throws Exception {
        Iterator<Visit> iterator = mock(Iterator.class);

        doReturn(visits).when(GAIA_PARK).getVisits();
        doReturn(iterator).when(visits).iterator();
        doReturn(true, true, false).when(iterator).hasNext();
        doReturn(VISIT_1, VISIT_2).when(iterator).next();
        
        Set<Visit> removed = repo.removeVisits(GAIA_PARK, VISIT_1);
        assertTrue(removed.contains(VISIT_1));
        assertFalse(removed.contains(VISIT_2));
        verify(mongo).save(GAIA_PARK);
    }

    @Test
    public void testDelete_byDocument() throws Exception {
        DeleteResult delete = mock(DeleteResult.class);
        final String PARK_ID = "ijewdioj-31oj31e";

        doReturn(PARK_ID).when(GAIA_PARK).getId();
        doReturn(delete).when(repo).delete(PARK_ID);

        assertEquals(delete, repo.delete(GAIA_PARK));
    }

    @Test
    public void testDelete_ById() throws Exception {
        DeleteResult delete = mock(DeleteResult.class);
        final String PARK_ID = "ijewdioj-31oj31e";
        doReturn(delete).when(mongo).remove(any(), eq(BarkPark.class));
        assertEquals(delete, repo.delete(PARK_ID));
    }
}
