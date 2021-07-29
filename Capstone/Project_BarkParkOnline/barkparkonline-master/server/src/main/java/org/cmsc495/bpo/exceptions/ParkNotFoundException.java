package org.cmsc495.bpo.exceptions;

public class ParkNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public ParkNotFoundException(String id)
    {
        super("Park " + id + " not found");
    }
}