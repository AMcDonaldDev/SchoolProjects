package org.cmsc495.bpo.dao.interfaces;

import java.util.List;

import org.cmsc495.bpo.dao.Credentials;
import org.cmsc495.bpo.dao.UserProfile;
import org.cmsc495.bpo.security.Permissions;

public interface User extends Comparable<User> {
    /**
     * Checks to see if the given User has the requested permissions.
     * 
     * @param user
     * @param permissions
     * @return
     */
    public static boolean hasPermissions(User user, Permissions... permissions) {
        List<Permissions> allowed = Permissions.getPermissions(user);

        for (Permissions desired : permissions) {
            // Return FALSE if any desired permissions are not found
            if (!allowed.contains(desired)) {
                return false;
            }
        }
        return true;
    }

    public enum Type {
        GUEST, BASIC, ADMIN;
    }

    /**
     * Returns a unique Username for a User
     * 
     * @return
     */
    public String getUsername();

    /**
     * Returns the credentials of a User (if they have any)
     */
    public Credentials getCredentials();

    public UserProfile getUserProfile();
    
    public Type getType();

	public String getId();
}
