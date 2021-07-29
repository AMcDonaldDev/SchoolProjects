package org.cmsc495.bpo.exceptions;

/**
 * Custom Checked Exception for when duplicate dog found.
 */
public class DuplicateDogException extends Exception {
    private static final long serialVersionUID = 2L;
    
    public DuplicateDogException(String username)
    {
        super("The Dog entered for USER [" + username + "] already exists");
    }
}
