package org.cmsc495.bpo.dao;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.cmsc495.bpo.dao.interfaces.Park;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class BarkPark implements Park {

    @Id
    private String id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String streetNumber;

    @NotNull
    @NotBlank
    private String streetName;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String cityName;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String stateName;

    @NotNull
    @Min(10000)
    @Max(99999)
    private Integer zipCode;

    @NotNull
    private Set<Visit> visits;

    public BarkPark() {
        visits = new HashSet<>();
    }

    public BarkPark(String id, String name, String streetNumber, String streetName, String cityName, String stateName,
            Integer zipCode, Set<Visit> visits) {
        this.id = id;
        this.name = name;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.cityName = cityName;
        this.stateName = stateName;
        this.zipCode = zipCode;
        this.visits = visits;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetNumber() {
        return this.streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return this.streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return this.stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public Set<Visit> getVisits() {
        return this.visits;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }

    public BarkPark withId(String id) {
        this.id = id;
        return this;
    }

    public BarkPark withName(String name) {
        this.name = name;
        return this;
    }

    public BarkPark withStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

    public BarkPark withStreetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public BarkPark withCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public BarkPark withStateName(String stateName) {
        this.stateName = stateName;
        return this;
    }

    public BarkPark withZipCode(Integer zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public BarkPark withVisits(Set<Visit> visits) {
        this.visits = visits;
        return this;
    }

    public int getVisitCount() {
        return this.visits.size();
    }

    @Override
    public String getLocation() {
        return this.streetNumber + " " + this.streetName + ", " + this.cityName + ", " + this.stateName + " "
                + this.zipCode;
    }
}