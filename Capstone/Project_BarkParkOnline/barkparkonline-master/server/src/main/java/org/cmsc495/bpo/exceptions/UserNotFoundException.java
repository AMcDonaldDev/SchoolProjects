package org.cmsc495.bpo.exceptions;

/**
 * Custom Checked Exception for when a User cannot be found.
 */
public class UserNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public UserNotFoundException(String msg)
    {
        super("User " + msg + " not found");
    }
}
