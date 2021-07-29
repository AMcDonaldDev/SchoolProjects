package org.cmsc495.bpo.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import org.springframework.data.annotation.Id;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Visit {
    
    @Id
    private String visitId;

    @NotNull
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String visitorFullName;

    @NotNull
    @NotEmpty
    private Set<@Pattern(regexp = "^[\\p{L} .'-]+$") String> visitorDogNames;

    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime time; // HH:MM:SS

    @NotNull
    private String parkId;

    private String visitorId;

    public Visit() {
        this.visitId = UUID.randomUUID().toString();
    }

    public String getVisitId() {
        return this.visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getVisitorFullName() {
        return this.visitorFullName;
    }

    public void setVisitorFullName(String visitorFullName) {
        this.visitorFullName = visitorFullName;
    }

    public Set<@Pattern(regexp="^[\\p{L} .'-]+$")String> getVisitorDogNames() {
        return this.visitorDogNames;
    }

    public void setVisitorDogNames(Set<@Pattern(regexp="^[\\p{L} .'-]+$")String> visitorDogNames) {
        this.visitorDogNames = visitorDogNames;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getParkId() {
        return this.parkId;
    }

    public void setParkId(String parkId) {
        this.parkId = parkId;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public Visit withVisitId(String visitId) {
        this.visitId = visitId;
        return this;
    }

    public Visit withVisitorFullName(String visitorFullName) {
        this.visitorFullName = visitorFullName;
        return this;
    }

    public Visit withVisitorDogNames(Set<@Pattern(regexp="^[\\p{L} .'-]+$") String> visitorDogNames) {
        this.visitorDogNames = visitorDogNames;
        return this;
    }

    public Visit withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Visit withTime(LocalTime time) {
        this.time = time;
        return this;
    }

    public Visit withParkId(String parkId) {
        this.parkId = parkId;
        return this;
    }

    public Visit withVisitorId(String visitorId) {
        this.visitorId = visitorId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Visit))
            return false;
        Visit other = (Visit) o;
        boolean equals = this.visitId.equals(other.getVisitId()) ||
            (
                this.date.equals(other.getDate()) &&
                this.time.equals(other.getTime()) &&
                this.visitorId.equals(other.getVisitorId())
            );
        return equals;
    }

    @Override
    public int hashCode() {
        return 0; // force equals() check
    }

    @Override
    public String toString() {
        return this.visitorFullName + " on " + this.date + " at " + this.time;
    }
}
