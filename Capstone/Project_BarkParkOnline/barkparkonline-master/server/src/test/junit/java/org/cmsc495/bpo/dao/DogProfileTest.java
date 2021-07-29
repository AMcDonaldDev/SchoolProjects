package org.cmsc495.bpo.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class DogProfileTest {
    private DogProfile dogProfile;
    String dogName = "Albert";
    String dogBreed = "Maltese";
    LocalDate dogDob = LocalDate.of(2019, 9, 9);
    DogProfile.Gender dogGender = DogProfile.Gender.MALE;

    @Before
    public void init() throws Exception {
        dogProfile = new DogProfile(dogName, dogBreed, dogDob, dogGender);
    }

    public void testGetters() throws Exception {
        assertEquals(dogName, dogProfile.getDogName());
        assertEquals(dogBreed, dogProfile.getDogBreed());
        assertEquals(dogDob, dogProfile.getDogDob());
        assertEquals(dogGender, dogProfile.getDogGender());
    }

    @Test
    public void testWithBuilders() throws Exception {
        dogProfile = new DogProfile().withDogName(dogName).withDogBreed(dogBreed).withDogDob(dogDob);
        dogProfile.setDogGender(dogGender);
        testGetters();
    }

    @Test
    public void testSetters() throws Exception {
        dogProfile.setDogName(dogName);
        dogProfile.setDogBreed(dogBreed);
        dogProfile.setDogDob(dogDob);
        dogProfile.setDogGender(dogGender);
        testGetters();
    }

    @Test
    public void testEquals() throws Exception {
        DogProfile otherDogProfile = new DogProfile("Daisy", "Poodle", LocalDate.of(2018, 5, 6),
                DogProfile.Gender.FEMALE);

        assertFalse(dogProfile.equals(new Object()));
        assertFalse(otherDogProfile.equals(dogProfile));
        assertTrue(dogProfile.equals(dogProfile));
    }
}
