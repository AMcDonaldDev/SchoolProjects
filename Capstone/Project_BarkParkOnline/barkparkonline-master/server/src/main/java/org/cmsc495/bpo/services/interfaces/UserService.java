package org.cmsc495.bpo.services.interfaces;

import java.util.Set;

import javax.validation.ValidationException;

import org.cmsc495.bpo.dao.Credentials;
import org.cmsc495.bpo.dao.UserProfile;
import org.cmsc495.bpo.dao.interfaces.User;
import org.cmsc495.bpo.exceptions.DuplicateUserException;
import org.cmsc495.bpo.exceptions.UserNotFoundException;

/**
 * A UserService that performs basic tasks related to User Management or the like.
 */
public interface UserService {
    /**
     * Retrieve a User's Profile based on the given username. This method
     * should not return null. Instead a UserNotFoundException should be 
     * thrown to ensure that this method is thread safe.
     * 
     * @param username
     * @return
     * @throws UserNotFoundException
     */
    public UserProfile getProfile(String username) throws UserNotFoundException;
    
    /**
     * Get a BasicUser based on the give username. Be aware that retrieving
     * a BasicUser means you are also retrieving their credentials. Treat
     * this method with respect and use it only when you need to.
     * 
     * @param username
     * @return
     * @throws UserNotFoundException
     */
    public User getUser(String username) throws UserNotFoundException;

    /**
     * Add a User (sign them up)
     * 
     * @param user
     */
    public void addUser(User user) throws DuplicateUserException;

    /**
     * Remove a User (remove them from the Data Base)
     * 
     * @param user
     * @return
     */
    public boolean removeUser(User user);
    
    /**
     * Notify the User Service that a User has logged in
     * 
     * @param user
     */
    public void login(User user);

   /**
     * Notify the User Service that a User has logged out
     * 
     * @param user
     */
    public void logout(User user);

    /**
     * Returns the Current Users logged into the User Service
     * 
     * @return
     */
    public Set<User> getCurrentUsers();

    /**
     * Copy fields from the updatedUser to the user.
     * 
     * @param username Username of the user to update.
     * @param srcUser The User Object with new fields to update the User with matching `username`.
     * @return The updated User object.
     */
    public User updateUserProfile(User user, UserProfile profile) throws UserNotFoundException, ValidationException;

    public User updateUserCredentials(User user, Credentials credentials);
}
