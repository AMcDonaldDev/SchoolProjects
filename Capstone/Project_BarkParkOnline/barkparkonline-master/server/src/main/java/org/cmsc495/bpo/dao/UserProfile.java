package org.cmsc495.bpo.dao;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.validator.constraints.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * State Object for a User's Profile
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfile {
    protected static final Logger log = LoggerFactory.getLogger(UserProfile.class);
    
    @NotBlank
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String lastName;

    /**
     * Phone number must not be blank
     */
    @Pattern(regexp = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$")
    private String phoneNumber;

    /**
     * Each Owner should have at least 1 dog
     */
    @Size(min = 1)
    private Set<DogProfile> dogs;

    /**
     * Profile Photo must be a valid URL
     */
    @URL
    private String profilePhotoUrl;

    private Set<String> visitIds;

    public UserProfile() {}

    public UserProfile(String firstName, String lastName, String phoneNumber, Set<DogProfile> dogs, String profilePhotoUrl, Set<String> visitIds) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dogs = dogs;
        this.profilePhotoUrl = profilePhotoUrl;
        this.visitIds = visitIds;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Never returns null
     */
    public Set<DogProfile> getDogs() {
        if (this.dogs == null) this.dogs = new HashSet<>();
        return this.dogs;
    }

    public void setDogs(Set<DogProfile> dogs) {
        this.dogs = dogs;
    }

    public String getProfilePhotoUrl() {
        return this.profilePhotoUrl;
    }

    public String getFullName() {
        return WordUtils.capitalize(this.firstName) + " "
            + WordUtils.capitalize(this.lastName);
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public Set<String> getVisitIds() {
        if (this.visitIds == null) this.visitIds = new HashSet<>();
        return this.visitIds;
    }

    public void setVisitIds(Set<String> visitIds) {
        this.visitIds = visitIds;
    }

    public UserProfile withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserProfile withProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
        return this;
    }

    public UserProfile withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserProfile withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserProfile withDogs(Set<DogProfile> dogs) {
        this.dogs = dogs;
        return this;
    }

    /**
     * Adds a single dog to a User's Profile. Remember that dogs is a Set
     * and so no two dogs can have the same name.
     * 
     * @param dog
     * @return
     */
    public UserProfile withDog(DogProfile dog) {
        if (this.dogs == null) {
            this.dogs = new HashSet<>();
        }
        if (this.dogs.add(dog)) {
            log.info("DOG [{}] already belongs to {}", dog.getDogName(), this.getFullName());
        }
        return this;
    }

    public UserProfile withVisitIds(Set<String> visitIds) {
        this.visitIds = visitIds;
        return this;
    }
}
