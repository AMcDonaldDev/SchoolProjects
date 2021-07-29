package org.cmsc495.bpo.dao;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

/**
 * State Object for a Dog's Profile
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DogProfile implements Comparable<DogProfile> {
    @NotBlank
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String dogName;

    @NotBlank
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String dogBreed;

    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dogDob;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Gender dogGender;

    public enum Gender {
        MALE, FEMALE
    }

    public DogProfile() {
    }

    public DogProfile(String dogName, String dogBreed, LocalDate dogDob, Gender dogGender) {
        this.dogName = dogName;
        this.dogBreed = dogBreed;
        this.dogDob = dogDob;
        this.dogGender = dogGender;
    }

    public String getDogName() {
        return this.dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getDogBreed() {
        return this.dogBreed;
    }

    public void setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
    }

    public LocalDate getDogDob() {
        return this.dogDob;
    }

    public void setDogDob(LocalDate dogDob) {
        this.dogDob = dogDob;
    }

    public Gender getDogGender() {
        return this.dogGender;
    }

    public void setDogGender(Gender dogGender) {
        this.dogGender = dogGender;
    }

    public DogProfile withDogName(String dogName) {
        this.dogName = dogName;
        return this;
    }

    public DogProfile withDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
        return this;
    }

    public DogProfile withDogDob(LocalDate dogDob) {
        this.dogDob = dogDob;
        return this;
    }

    public DogProfile withDogGender(Gender dogGender) {
        this.dogGender = dogGender;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DogProfile)) return false;
        DogProfile other = (DogProfile) o;
        return this.getDogName().equals(other.getDogName());
    }

    @Override
    public int hashCode() {
        return this.getDogName().hashCode();
    }

    @Override
    public int compareTo(DogProfile o) {
        return this.getDogName().toLowerCase().compareTo(o.getDogName().toLowerCase());
    }
}
