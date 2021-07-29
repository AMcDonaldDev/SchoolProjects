package org.cmsc495.bpo.services;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.mongodb.client.result.DeleteResult;

import org.cmsc495.bpo.dao.Credentials;
import org.cmsc495.bpo.dao.GuestUser;
import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.UserProfile;
import org.cmsc495.bpo.dao.interfaces.User;
import org.cmsc495.bpo.exceptions.DuplicateUserException;
import org.cmsc495.bpo.exceptions.UserNotFoundException;
import org.cmsc495.bpo.repositories.BasicUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StandardUserServiceTest {
    private StandardUserService service;

    @Mock
    private BasicUserRepository repo;

    @Mock
    private PasswordEncoder encoder;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = spy(new StandardUserService(repo, encoder));
        doNothing().when(service).validateUser(any());
    }

    @Test
    public void testGetProfile() throws Exception {
        BasicUser user = mock(BasicUser.class);
        UserProfile profile = mock(UserProfile.class);
        final String USERNAME = "MR-monkeyz";

        doReturn(user).when(repo).retrieve(USERNAME);
        doReturn(profile).when(user).getUserProfile();

        assertEquals(profile, service.getProfile(USERNAME));
    }

    @Test
    public void testGetProfile_butNoUser() throws Exception {
        final String USERNAME = "monkeyz";

        doReturn(null).when(repo).retrieve(USERNAME);

        assertThrows(UserNotFoundException.class, () -> {
            service.getProfile(USERNAME);
        });
    }

    @Test
    public void testGetProfile_butUserIsNotFound() throws Exception {
        String username = "alextrebek";
        BasicUser alex = mock(BasicUser.class);

        doReturn(alex).when(repo).retrieve(username);
        doThrow(NullPointerException.class).when(alex).getUserProfile();
        assertThrows(UserNotFoundException.class, () -> {
            service.getProfile(username);
        });
    }

    @Test
    public void testGetUser() throws Exception {
        BasicUser jimmyBuffet = mock(BasicUser.class);
        String username = "jimmybuffet";
        
        doReturn(jimmyBuffet).when(repo).retrieve(username);

        User returnedUser = service.getUser(username);
        assertEquals(jimmyBuffet, returnedUser);
    }

    @Test
    public void testGetUser_butUserIsNotFound() throws Exception {
        String username = "jimmybuffet";
        BasicUser jimmy = null;

        doReturn(jimmy).when(repo).retrieve(username);

        assertThrows(UserNotFoundException.class, () -> {
            service.getUser(username);
        });
    }

    @Test
    public void testAddUser_basicUser() throws Exception {
        BasicUser billyJoe = mock(BasicUser.class);
        String username = "greenday4ever";
        User.Type type = User.Type.BASIC;

        doReturn(type).when(billyJoe).getType();
        doReturn(billyJoe).when(repo).create(billyJoe);
        doReturn(username).when(billyJoe).getUsername();
        service.addUser(billyJoe);

        verify(repo).create(billyJoe);
    }

    @Test
    public void testAddUser_basicUser_butDupe() throws Exception {
        BasicUser billyJoe = mock(BasicUser.class);
        String username = "greenday4ever";
        User.Type type = User.Type.BASIC;

        doReturn(type).when(billyJoe).getType();
        doThrow(IllegalArgumentException.class).when(repo).create(billyJoe);
        doReturn(username).when(billyJoe).getUsername();

        assertThrows(DuplicateUserException.class, () -> {
            service.addUser(billyJoe);
        });
    }

    @Test
    public void testAddUser_guestUser() throws Exception {
        GuestUser guest = mock(GuestUser.class);
        String username = "guesty";
        User.Type type = User.Type.GUEST;

        doReturn(type).when(guest).getType();
        doReturn(username).when(guest).getUsername();

        assertDoesNotThrow(() -> {
            service.addUser(guest);
        });
    }

    @Test
    public void testRemoveUser() throws Exception {
        BasicUser buzzLightyear = mock(BasicUser.class);
        String username ="2infinity";
        Credentials cred = mock(Credentials.class);
        DeleteResult result = mock(DeleteResult.class);
        long deleted = 1;

        doReturn(username).when(cred).getUsername();
        doReturn(cred).when(buzzLightyear).getCredentials();
        doReturn(result).when(repo).delete(buzzLightyear);
        doReturn(deleted).when(result).getDeletedCount();

        assertTrue(service.removeUser(buzzLightyear));
    }

    @Test
    public void testRemoveUser_whoIsAGuest() throws Exception {
        User woody = mock(GuestUser.class);
        
        assertTrue(service.removeUser(woody));
        verify(service).logOffUser(woody);
    }
}
