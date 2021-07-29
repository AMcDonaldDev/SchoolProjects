package org.cmsc495.bpo.dao;

import java.util.UUID;

import org.cmsc495.bpo.dao.interfaces.User;

public class GuestUser implements User {
    private String ip;

    private String id;

    public GuestUser() {
        id = UUID.randomUUID().toString();
    }

    public GuestUser(String ip) {
        this();
        this.ip = ip;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "Guest [" + ip + "]";
    }

    @Override
    public int compareTo(User o) {
        if (o instanceof BasicUser || o instanceof Administrator) return -1;
        return this.ip.compareTo(o.getUsername());
    }

    @Override
    public String getUsername() {
        return this.ip;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GuestUser)) return false;
        GuestUser other = (GuestUser) o;
        return this.ip.equals(other.getIp());
    }

    /**
     * Guest Users have no credentials
     */
    @Override
    public Credentials getCredentials() {
        return null;
    }

    /**
     * Guest Users have no profile
     */
    @Override
    public UserProfile getUserProfile() {
        return null;
    }

    @Override
    public Type getType() {
        return User.Type.GUEST;
    }

    @Override
    public int hashCode() {
        return this.ip.hashCode();
    }

    @Override
    public String getId() {
        return this.id;
    }
}
