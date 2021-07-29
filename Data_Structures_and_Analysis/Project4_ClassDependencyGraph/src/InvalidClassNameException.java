/*
 * File: InvalidClassNameException.java
 * Author: Allison McDonald
 * Date: July 28, 2019
 * Purpose: CMSC 350 Project 4 - a program that behaves like the Java command
 * line compiler.  This is a user defined exception used when an invalid class
 * name is entered in the Class to recompile field.
 */

public class InvalidClassNameException extends Exception {
    // Constructor
    public InvalidClassNameException() {
    }
    
    // Method to return error message
    @Override
    public String getMessage() {
        return "Invalid Class Name Entered";
    }
    
}
