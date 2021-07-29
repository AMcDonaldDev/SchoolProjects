/*
 * File: TokenException.java
 * Author: Allison McDonald
 * Date: June 30, 2019
 * Purpose: CMSC 350 Project 2 - a program that evaluates postfix expressions,
 * this is the TokenException class - This is a user defined exception used with
 * Evaluator class
 */

public class TokenException extends RuntimeException{
    //Declare variable
    private String token;
    
    //Constructor
    public TokenException(String token) {
        this.token = token;
    }
    
    //Method to return error message
    @Override
    public String getMessage() {
        return "Invalid token of " + token + " entered";
    }
    
}
