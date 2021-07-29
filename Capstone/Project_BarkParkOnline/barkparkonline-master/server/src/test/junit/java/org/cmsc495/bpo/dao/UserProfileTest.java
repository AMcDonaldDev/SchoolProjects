package org.cmsc495.bpo.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashSet;


public class UserProfileTest {
    private UserProfile userProfile;

    String firstName = "EMMA";
    String lastName = "STONE";
    String phoneNumber = "9090898912";
    String profilePhotoUrl = "https://en.wiktionary.org/wiki/profile#/media/File:Souvenir_silhouette_post_card._Toledo's_greatest_store;_Tiedtke's._The_store_for_all_the_people_-_DPLA_-_f00a78fe61c216236a13cdebf588d3c3_(page_1).jpg";
    
    String dogName = "Albert";
    String dogBreed = "Maltese";
    LocalDate dogDob = LocalDate.of(2019, 9, 9);
    DogProfile.Gender dogGender = DogProfile.Gender.MALE;
    
    DogProfile dog = new DogProfile(dogName, dogBreed, dogDob, dogGender);
    HashSet<DogProfile> dogs = new HashSet<>();
    HashSet<String> visitIds = new HashSet<>();


    @Before
    public void init() throws Exception {
        userProfile = new UserProfile();
    }

    public void testGetters() throws Exception {
        assertEquals(dogs, userProfile.getDogs());
        assertEquals(firstName, userProfile.getFirstName());
        assertEquals(lastName, userProfile.getLastName());
        assertEquals(firstName.toUpperCase() + " " + lastName.toUpperCase(), userProfile.getFullName());
        assertEquals(phoneNumber, userProfile.getPhoneNumber());
        assertEquals(profilePhotoUrl, userProfile.getProfilePhotoUrl());
        assertEquals(dogs, userProfile.getDogs());
    }

    @Test
    public void testAllArgsConstructor() throws Exception {
        userProfile = new UserProfile(firstName, lastName, phoneNumber, dogs, profilePhotoUrl, visitIds);
        testGetters();
    }

    @Test
    public void testWithBuilders() throws Exception {
        userProfile = new UserProfile()
                .withProfilePhotoUrl(profilePhotoUrl)
                .withLastName(lastName)
                .withPhoneNumber(phoneNumber)
                .withDogs(dogs)
                .withDog(dog)
                .withFirstName(firstName);
        testGetters();
    }

    @Test
    public void testSetters() throws Exception {
        userProfile.setFirstName(firstName);
        userProfile.setLastName(lastName);
        userProfile.setDogs(dogs);
        userProfile.setPhoneNumber(phoneNumber);
        userProfile.setProfilePhotoUrl(profilePhotoUrl);
        testGetters();
    }

    @Test
    public void testEquals() throws Exception {
        UserProfile otherUserProfile = new UserProfile("Susan", "Sam",
                "909090102020", new HashSet<DogProfile>(), "url:/sample.jpg", new HashSet<String>());
        assertFalse(userProfile.equals(new Object()));
        assertFalse(otherUserProfile.equals(userProfile));
        assertTrue(userProfile.equals(userProfile));
    }

    @Test
    @SuppressWarnings("all")
    public void testHashCode() throws Exception {
        assertTrue(new Integer(userProfile.hashCode()) != null);
    }
}
