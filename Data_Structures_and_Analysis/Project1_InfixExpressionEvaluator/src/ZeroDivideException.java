/*
 * File: ZeroDivideException.java
 * Author: Allison McDonald
 * Date: June 16, 2019
 * Purpose: CMSC 350 Project 1 - a program that evaluates infix expressions, this
 * is the ZeroDivideExpception class - This is a user defined exception used when
 * attempting to divide by zero
 */

public class ZeroDivideException extends Exception {
    //Constructor
    public ZeroDivideException() {
    }
    
    //Method to return error message
    public String getMessage() {
        return "Cannot divide by zero";
    }
    
}
