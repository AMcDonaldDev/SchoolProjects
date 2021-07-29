package org.cmsc495.bpo.dao;

import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mongodb.annotations.NotThreadSafe;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NotThreadSafe
@JsonIgnoreProperties(ignoreUnknown = true)
public class Credentials {
    @Id
    private String id;

    /**
     * User Name must be at least 6 characters in length, but less than 20.
     */
    @NotNull
    @Size(min = 6, max = 20)
    private String username;

    @Size(min = 8, max = 60)
    private transient String password;

    /**
     * Email Address must not be null AND must be a valid email address
     */
    @NotNull
    @Email
    private String email;

    private String profileId;

    public Credentials() {
    }

    public Credentials(String id, String username, String email, String profileId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.profileId = profileId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Credentials withUsername(String username) {
        this.username = username;
        return this;
    }

    public Credentials withPassword(String password) {
        this.password = password;
        return this;
    }

    public Credentials withEmail(String email) {
        this.email = email;
        return this;
    }

    public Credentials withProfileId(String profileId) {
        this.profileId = profileId;
        return this;
    }

    public String getProfileId() {
        return this.profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Credentials)) {
            return false;
        }
        Credentials credentials = (Credentials) o;
        return Objects.equals(id, credentials.id) && Objects.equals(username, credentials.username) && Objects.equals(email, credentials.email) && Objects.equals(profileId, credentials.profileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, profileId);
    }

    @Override
    public String toString() {
        return new String("{" +
            " id='" + getId() + "'" +
            ", username='" + getUsername() + "'" +
            ", email='" + getEmail() + "'" +
            ", profileId='" + getProfileId() + "'" +
            "}");
    }
}
