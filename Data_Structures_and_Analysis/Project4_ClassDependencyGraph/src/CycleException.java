/*
 * File: CycleException.java
 * Author: Allison McDonald
 * Date: July 28, 2019
 * Purpose: CMSC 350 Project 4 - a program that behaves like the Java command
 * line compiler.  This is a user defined exception used when a class cycle is 
 * detected.
 */

public class CycleException extends Exception {
    // Constructor
    public CycleException() {
    }
    
    // Method to return error message
    @Override
    public String getMessage() {
        return "Cycle Detected";
    }
    
}
