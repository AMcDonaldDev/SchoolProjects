/*
 * File: StackException.java
 * Author: Pulled from Stacks and Linked Data Structures PDF in week 2 content
 * Date: June 30, 2019
 * Purpose: CMSC 350 Project 2 - a program that evaluates postfix expressions, this
 * is the StackException class - This is a user defined exception used with the
 * Array Stack class and Stack interface
 */

public class StackException extends Exception {
    //Declare variables
    private String note;
    
    //Constructor
    public StackException(String note) {
       this.note = note; 
    }
    
    //Method to return error message
    @Override
    public String getMessage() {
        return note;
    }
    
}
