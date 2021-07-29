package org.cmsc495.bpo.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;


public class BasicUserTest {
    private BasicUser basicUser;

    private String username = "EMMA STONE";
    private String email = "emmastone@easy-a.com";
    private String profileId = "4c27c0ef-1a92-443c-be28-62d164652b4a";
    private String id = "1";

    private Credentials credentials = new Credentials(id, username, email, profileId);

    String firstName = "EMMA";
    String lastName = "STONE";
    String phoneNumber = "9090898912";
    String profilePhotoUrl = "https://en.wiktionary.org/wiki/profile#/media/File:Souvenir_silhouette_post_card._Toledo's_greatest_store;_Tiedtke's._The_store_for_all_the_people_-_DPLA_-_f00a78fe61c216236a13cdebf588d3c3_(page_1).jpg";

    
    HashSet<DogProfile> dogs = new HashSet<>();
    HashSet<String> visitIds = new HashSet<>();
    private UserProfile userProfile = new UserProfile(firstName, lastName, phoneNumber, dogs, profilePhotoUrl, visitIds);


    @Before
    public void init() throws Exception {
        basicUser = new BasicUser();
        basicUser.setCredentials(credentials);
    }

    public void testGetters() throws Exception {
        assertEquals(credentials, basicUser.getCredentials());
        assertEquals(userProfile, basicUser.getUserProfile());
        assertEquals(username, basicUser.getUsername());
    }

    @Test
    public void testAllArgsConstructor() throws Exception {
        basicUser = new BasicUser(credentials, userProfile);
        testGetters();
    }

    @Test
    public void testWithBuilders() throws Exception {
        basicUser = new BasicUser()
                .withUserProfile(userProfile)
                .withCredentials(credentials);
        testGetters();
    }

    @Test
    public void testSetters() throws Exception {
        basicUser.setCredentials(credentials);
        basicUser.setUserProfile(userProfile);
        testGetters();
    }

    @Test
    public void testEquals() throws Exception {
        BasicUser otherBasicUser = new BasicUser(new Credentials(), new UserProfile());
        basicUser.getCredentials().setUsername("EMMA STONE");
        otherBasicUser.getCredentials().setUsername("WILKINS");
        assertFalse(basicUser.equals(new Object()));
        assertFalse(otherBasicUser.equals(basicUser));
        assertTrue(basicUser.equals(basicUser));
    }

    @Test
    @SuppressWarnings("all")
    public void testHashCode() throws Exception {
        assertTrue(new Integer(basicUser.hashCode()) != null);
    }
}
