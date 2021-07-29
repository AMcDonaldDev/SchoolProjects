package org.cmsc495.bpo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.cmsc495.bpo.dao.DogProfile.Gender;
import org.cmsc495.bpo.dao.Credentials;
import org.cmsc495.bpo.dao.interfaces.User.Type;
import org.cmsc495.bpo.controllers.response.ApiResponse;
import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.UserProfile;
import org.cmsc495.bpo.dao.DogProfile; 
import org.cmsc495.bpo.exceptions.DuplicateDogException; 
import org.cmsc495.bpo.services.interfaces.UserService;
import org.cmsc495.bpo.services.interfaces.DogService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.cmsc495.bpo.repositories.BasicUserRepository;

public class UserControllerTest {
    protected static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserController controller;

    @Mock
    private UserService userService;

    @Mock
    private DogService dogService;

    @Mock
    private BasicUserRepository repo;

    @Mock
    private BasicUser requester;

    @Mock
    private BasicUser updateDefinition;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = spy(new UserController(userService, dogService));
    }

    @Test
    public void testUserLogin() throws Exception {
        String userName = "woody"; 
        UserProfile woodysProfile = mock(UserProfile.class);
        BasicUser user = mock(BasicUser.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        Credentials credentials = mock(Credentials.class);

        doReturn(credentials).when(user).getCredentials();
        doReturn(userName).when(user).getUsername();
        doReturn(woodysProfile).when(userService).getProfile(userName);
        doReturn(user).when(user).copy();

        ResponseEntity<?> response = controller.userLogin(user, request);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(credentials).setPassword(null); 
    }

    @Test
    public void testSignUp() throws Exception {
        BasicUser austinPowers = mock(BasicUser.class); 
        ResponseEntity<?> response = controller.signUp(austinPowers);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(userService, times(1)).addUser(austinPowers);
    }

    @Test
    public void testSignUp_butUnknownExceptionWasThrown() throws Exception {
        BasicUser goldmember = mock(BasicUser.class);
        Credentials austinPowers = mock(Credentials.class);
        String username = "AustinPowWow";
        doReturn(username).when(austinPowers).getUsername();
        doReturn(austinPowers).when(goldmember).getCredentials();
        doThrow(NullPointerException.class).when(userService).addUser(goldmember);
        ResponseEntity<?> response = controller.signUp(goldmember);
        assertTrue(response.getStatusCode().is5xxServerError());
    }

    @Test
    @SuppressWarnings("all")
    public void testAddDog() throws Exception {
        String username = "woody";
        BasicUser user = mock(BasicUser.class);
        DogProfile woodysDog = mock(DogProfile.class);
        Set<DogProfile> dogs = mock(Set.class);
        UserProfile profile = mock(UserProfile.class);
        doReturn(profile).when(user).getUserProfile();
        doReturn(dogs).when(profile).getDogs();
        doReturn(username).when(user).getUsername();
        doReturn(dogs).when(dogService).addDogs(eq(user), any());

        ApiResponse response = controller.addDog(user, woodysDog);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(dogs, response.getBody().getMessage());
    }

    DogProfile getDummyDog() {
        return new DogProfile()
            .withDogBreed("dog")
            .withDogGender(Gender.FEMALE)
            .withDogName("DummyDog")
            .withDogDob(LocalDate.of(2020, 1, 1));
    }

    BasicUser getDummyUser() {
        return new BasicUser()
            .withCredentials(new Credentials()
                .withEmail("dummy@example.com")
                .withPassword("dummypassword0123")
                .withUsername("DummyUser"))
            .withUserProfile(new UserProfile()
                .withFirstName("Dummy")
                .withLastName("TheUser")
                .withPhoneNumber("123-456-7890")
                .withDog(getDummyDog()));
    }

    @Test
    public void testUser_updateUserCredentials() throws Exception {
        final String USERNAME = "foofighter";
        final Type USER_TYPE = Type.BASIC;
        Credentials credentials = mock(Credentials.class);

        doReturn(credentials).when(updateDefinition).getCredentials();
        doReturn(null).when(updateDefinition).getUserProfile();
        doReturn(USERNAME).when(requester).getUsername();
        doReturn(USER_TYPE).when(requester).getType();
        doReturn(true).when(controller).userHasPermissionsToEdit(requester);

        ResponseEntity<?> response = controller.update(USERNAME, requester, updateDefinition);
        log.info("Status Code: {}", response.getStatusCode());
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testUser_updateUserProfile() throws Exception {
        final String USERNAME = "foofighter";
        final Type USER_TYPE = Type.BASIC;
        UserProfile userProfile = mock(UserProfile.class);

        doReturn(userProfile).when(updateDefinition).getUserProfile();
        doReturn(null).when(updateDefinition).getCredentials();
        doReturn(USERNAME).when(requester).getUsername();
        doReturn(USER_TYPE).when(requester).getType();
        doReturn(true).when(controller).userHasPermissionsToEdit(requester);

        ResponseEntity<?> response = controller.update(USERNAME, requester, updateDefinition);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @SuppressWarnings("all")
    public void testAddDog_DuplicateDogExceptionWasThrown() throws Exception {
        String username = "woody";
        BasicUser user = mock(BasicUser.class);
        DogProfile woodysDog = mock(DogProfile.class);
        Set<DogProfile> dogs = mock(Set.class);
        UserProfile profile = mock(UserProfile.class);
        DuplicateDogException exception = mock(DuplicateDogException.class);
        doReturn("dog already exists").when(exception).getLocalizedMessage();
        doReturn("woody's dog").when(woodysDog).getDogName();
        doReturn(profile).when(user).getUserProfile();
        doReturn(dogs).when(profile).getDogs();
        doReturn(username).when(user).getUsername();
        doReturn(user).when(userService).getUser(username);
        doThrow(exception).when(dogService).addDogs((BasicUser)user, woodysDog);

        ApiResponse response = controller.addDog(user, woodysDog);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(response.getBody().getMessage().toString().contains("unable to add"));
    }
}
