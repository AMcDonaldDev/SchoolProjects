/*
 * File: InsufficientFunds.java
 * Author: Allison McDonald
 * Date: April 13, 2019
 * Purpose: CMIS 242 7983 Project 2 - a program that implements an ATM, this is
 * the InsufficientFunds class - A user defined checked exception for when a 
 * withdrawal or transfer exceeds the amount available in the account
 */
package projecttwo;

public class InsufficientFunds extends Exception {

    public String insufficientFunds() {
        return "Unable to complete transaction due to insufficient funds.";
    }
}
