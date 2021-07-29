package org.cmsc495.bpo.exceptions;

/**
 * Custom Checked Exception for when a Dog cannot be found.
 */
public class DogNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public DogNotFoundException(String username)
    {
        super("Dog(s) not found for User " + username);
    }
}
