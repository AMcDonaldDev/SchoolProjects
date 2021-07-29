package org.cmsc495.bpo.repositories;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.mongodb.client.result.DeleteResult;

import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.Credentials;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BasicUserRepositoryTest {
    private BasicUserRepository repo;

    @Mock
    private MongoTemplate mongo;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private BasicUser user;

    @Mock
    private Credentials credentials;

    private final String PASSWORD = "cheeze";

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        repo = spy(new BasicUserRepository(mongo, encoder));
        doReturn(PASSWORD).when(encoder).encode(PASSWORD);
        doReturn(PASSWORD).when(credentials).getPassword();
        doReturn(credentials).when(user).getCredentials();
    }

    @Test
    public void testRetrieve() throws Exception {
        final String USERNAME = "partyanimal";
        Query query = mock(Query.class);

        doReturn(user).when(mongo).findOne(query, BasicUser.class);
        doReturn(query).when(repo).queryByUsername(USERNAME);

        assertEquals(user, repo.retrieve(USERNAME)); 
    }

    @Test
    public void testDeleteUser() throws Exception {
        DeleteResult deleteResult = mock(DeleteResult.class);

        doReturn(deleteResult).when(mongo).remove(user);

        assertEquals(deleteResult, repo.delete(user));
        verify(mongo).remove(user);
    }

    @Test
    public void testCreate() throws Exception {
        final String USERNAME = "partyanimal";

        doReturn(null).when(repo).retrieve(USERNAME);
        doReturn(user).when(repo).finalizeAndSaveUser(user);

        assertEquals(user, repo.create(user));
        verify(repo).finalizeAndSaveUser(user);
    }
}
