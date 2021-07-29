package org.cmsc495.bpo.exceptions;

public class VisitNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public VisitNotFoundException(String id)
    {
        super("Visit " + id + " not found");
    }
}