package org.cmsc495.bpo.security;

import java.util.ArrayList;
import java.util.List;

import org.cmsc495.bpo.dao.Administrator;
import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.GuestUser;
import org.cmsc495.bpo.dao.interfaces.User;

/**
 * Permissions for Service functions
 */
public enum Permissions {
    CREATE, EDIT, REMOVE, VIEW, ADMIN;

    /**
     * Returns NO Permissions allowed
     */
    public static List<Permissions> noPermissions() {
        return List.of();
    }

    /**
     * Returns the Permissions allowed for an Admin User
     */
    public static List<Permissions> adminPermissinos() {
        List<Permissions> perm = new ArrayList<>(basicUserPermissions());
        perm.addAll(List.of(
            Permissions.ADMIN
        ));
        return perm;
    }

    /**
     * Returns the Permissions allowed for a Basic User
     */
    public static List<Permissions> basicUserPermissions() {
        List<Permissions> perm = new ArrayList<>(guestPermissions());
        perm.addAll(List.of(
            Permissions.CREATE, 
            Permissions.EDIT, 
            Permissions.REMOVE
        ));
        return perm;
    }

    /**
     * Returns the Permissions allowed for a Guest User
     */
    public static List<Permissions> guestPermissions() {
        return List.of(Permissions.VIEW);
    }

    /**
     * Gets the Permissions for the given User
     */
    public static List<Permissions> getPermissions(User user) {
        return getPermissions(user.getClass());
    }

    /**
     * Gets the Permissions for the given User Class Type.
     */
    public static List<Permissions> getPermissions(Class<?> clazz) {
        if (clazz.equals(BasicUser.class)) {
            return basicUserPermissions();

        } else if (clazz.equals(GuestUser.class)) {
            return guestPermissions();

        } else if (clazz.equals(Administrator.class)) {
            return adminPermissinos();
        } 
        return noPermissions();
    }
}
