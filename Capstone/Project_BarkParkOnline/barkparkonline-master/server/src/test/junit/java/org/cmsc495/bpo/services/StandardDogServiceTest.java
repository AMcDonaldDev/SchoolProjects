package org.cmsc495.bpo.services;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;


import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.UserProfile;
import org.cmsc495.bpo.dao.DogProfile;
import org.cmsc495.bpo.repositories.BasicUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StandardDogServiceTest {
    @Mock
    private StandardDogService dogService;
    private StandardUserService service;

    @Mock
    private BasicUserRepository repo;

    @Mock
    private PasswordEncoder encoder;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = spy(new StandardUserService(repo, encoder));
        dogService = spy(new StandardDogService(repo));
        doNothing().when(service).validateUser(any());
        doNothing().when(dogService).validateDog(any());
    }

    @Test
    @SuppressWarnings("all")
    public void testAddDogs() throws Exception {
        BasicUser joe = mock(BasicUser.class);
        UserProfile joeProfile = mock(UserProfile.class);
        Set<DogProfile> dogs = mock(HashSet.class);
        DogProfile penny = mock(DogProfile.class);
        dogs.add(penny);
        DogProfile woof = mock(DogProfile.class);
        String username = "joe";
        
        when(joe.getUserProfile()).thenReturn(joeProfile);
        when(joeProfile.getDogs()).thenReturn(dogs);
        when(dogs.contains(woof)).thenReturn(false);
        doReturn(username).when(joe).getUsername();
        dogService.addDogs(joe, woof);

        verify(repo).update(joe);
    }

    @Test
    @SuppressWarnings("all")
    public void testRemoveDog() throws Exception {
        BasicUser joe = mock(BasicUser.class);
        UserProfile joeProfile = mock(UserProfile.class);
        Set<DogProfile> dogs = mock(HashSet.class);
        DogProfile penny = mock(DogProfile.class);
        dogs.add(penny);
        penny.setDogName("penny");
        Iterator<DogProfile> iterator = mock(Iterator.class);

        when(joe.getUserProfile()).thenReturn(joeProfile);
        when(joeProfile.getDogs()).thenReturn(dogs);
        when(dogs.iterator()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true);
        doReturn(penny).when(iterator).next();
        when(penny.getDogName()).thenReturn("penny");
        dogService.removeDog(joe, "penny");

        verify(repo).update(joe);
    }

    @Test
    @SuppressWarnings("all")
    public void testGetDogProfiles() {
        String username = "joe";
        List<DogProfile> dogList = mock(ArrayList.class);
        Set<DogProfile> dogs = new HashSet<>();
        DogProfile penny = mock(DogProfile.class);
        dogs.add(penny);

        doReturn(dogs).when(dogService).getDogs(username);
        when(dogList.addAll(dogs)).thenReturn(true);
        doReturn(true).when(dogList).addAll(dogs);
        dogList.addAll(dogs);
        Collection<DogProfile> returnedDogs = dogService.getDogProfiles(username);

        assertIterableEquals(dogs, returnedDogs);
    }

    @Test
    @SuppressWarnings("all")
    public void testGetDogs() {
        String username = "joe";
        BasicUser joe = mock(BasicUser.class);
        UserProfile joeProfile = mock(UserProfile.class);
        Set<DogProfile> dogs = mock(HashSet.class);

        doReturn(joe).when(repo).retrieve(username);
        when(joe.getUserProfile()).thenReturn(joeProfile);
        when(joeProfile.getDogs()).thenReturn(dogs);

        assertIterableEquals(dogs, dogService.getDogs(username));
    }

    @Test
    @SuppressWarnings("all")
    public void testUpdateDogProfile() throws Exception {
        BasicUser joe = mock(BasicUser.class);
        UserProfile joeProfile = mock(UserProfile.class);
        Set<DogProfile> dogs = mock(HashSet.class);
        DogProfile penny = mock(DogProfile.class);
        dogs.add(penny);
        penny.setDogName("penny");
        DogProfile newPenny = mock(DogProfile.class);
        newPenny.setDogName("newPenny");
        Iterator<DogProfile> iterator = mock(Iterator.class);

        when(joe.getUserProfile()).thenReturn(joeProfile);
        when(joeProfile.getDogs()).thenReturn(dogs);
        when(dogs.iterator()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true);
        doReturn(penny).when(iterator).next();
        when(penny.getDogName()).thenReturn("penny");
        dogService.updateDogProfile(joe, "penny", newPenny);

        verify(repo).update(joe);
    }
}
