package org.cmsc495.bpo.dao;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.cmsc495.bpo.dao.interfaces.User;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * State Object for a User. Validation constraints are added to User in order
 * for Spring to ensure that recieved User objects contain the required
 * information.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicUser implements User, UserDetails {
    private static final long serialVersionUID = -6300746649837335754L;

    @Id
    private String id;

    @NotNull
    @Valid
    private Credentials credentials;

    /**
     * User must have a valid profile
     */
    @NotNull
    @Valid
    private UserProfile userProfile;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    private Boolean enabled;


    public BasicUser() {
    }

    public BasicUser(Credentials credentials, UserProfile userProfile) {
        this.credentials = credentials;
        this.userProfile = userProfile;
    }

    public BasicUser(
        String username, 
        String password, 
        boolean enabled, 
        boolean accountNonExpired,
        boolean credentialsNonExpired,
        boolean accountNonLocked,
        Collection<? extends GrantedAuthority> authorities
    ) {
        this.credentials.setUsername(username);
        this.credentials.setPassword(password);
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
    }

    public Credentials getCredentials() {
        return this.credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public UserProfile getUserProfile() {
        return this.userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public BasicUser withCredentials(Credentials credentials) {
        this.credentials = credentials;
        return this;
    }

    public BasicUser withUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        return this;
    }

    @Override
    public String toString() {
        return "Basic User [" + credentials.getUsername() + "]";
    }

    /**
     * For Sorting Users
     */
    @Override
    public int compareTo(User o) {
        if (o instanceof GuestUser)
            return 1;
        if (o instanceof Administrator)
            return -1;
        return this.getUsername().compareTo(o.getUsername());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BasicUser))
            return false;
        BasicUser other = (BasicUser) o;
        return this.getUsername().equals(other.getUsername());
    }

    @Override
    public int hashCode() {
        if (this.getUsername() == null) return super.hashCode();
        return this.getUsername().hashCode();
    }

    @Override
    public String getUsername() {
        return this.credentials.getUsername();
    }

    @Override
    public Type getType() {
        return User.Type.BASIC;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.credentials.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public BasicUser copy() {
        BasicUser copy = new BasicUser()
            .withCredentials(
                new Credentials()
                    .withEmail(this.credentials.getEmail())
                    .withPassword(this.credentials.getPassword())
                    .withProfileId(this.credentials.getProfileId())
                    .withUsername(this.credentials.getUsername())
            )
            .withUserProfile(
                new UserProfile()
                    .withDogs(this.userProfile.getDogs())
                    .withFirstName(this.userProfile.getFirstName())
                    .withLastName(this.userProfile.getLastName())
                    .withPhoneNumber(this.userProfile.getPhoneNumber())
                    .withProfilePhotoUrl(this.userProfile.getProfilePhotoUrl())
                    .withVisitIds(this.userProfile.getVisitIds())
            );
        return copy;
    }
}
