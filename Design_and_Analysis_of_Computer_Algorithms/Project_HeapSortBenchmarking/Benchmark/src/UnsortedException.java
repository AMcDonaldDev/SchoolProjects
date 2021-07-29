/*
 * File: UnsortedException.java
 * Author: Allison McDonald
 * Date: 11/25/2020
 * Purpose: CMSC 451 Project 1 - UnsortedException thrown when array is not correctly
 * sorted after running through either recursive or iterative algorithms
 */

public class UnsortedException extends Exception {
    public String message;
    
    public UnsortedException(String message) {
        this.message = message;
    }
}
