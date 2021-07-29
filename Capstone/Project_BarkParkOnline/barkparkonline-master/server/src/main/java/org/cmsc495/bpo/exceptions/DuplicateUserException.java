package org.cmsc495.bpo.exceptions;

/**
 * Custom Checked Exception for when a User cannot be found.
 */
public class DuplicateUserException extends Exception {
    private static final long serialVersionUID = 2L;
    
    public DuplicateUserException(String msg)
    {
        super("USER [" + msg + "] already exists");
    }
}
