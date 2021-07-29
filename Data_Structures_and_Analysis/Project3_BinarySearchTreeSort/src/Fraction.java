/*
 * File: Fraction.java
 * Author: Allison McDonald
 * Date: July 13, 2019
 * Purpose: CMSC 350 Project 3 - a program that performs a sort by using a binary
 * search tree. This class defines a fraction and implements the Comparable interface.
 */

import java.util.regex.Pattern;

public class Fraction implements Comparable<Fraction> {
    //Declare variables
    private String fraction;
    private int numerator;
    private int denominator;
    private Pattern p;
    
    //Constructor
    public Fraction(String fraction) {
        p = Pattern.compile("\\d+/\\d+");
        if (p.matcher(fraction).matches()) {
            String[] parseFraction = fraction.split("/");
            if (Integer.parseInt(parseFraction[1]) == 0) {
                throw new NumberFormatException();
            } else {
                this.fraction = fraction;
                this.numerator = Integer.parseInt(parseFraction[0]);
                this.denominator = Integer.parseInt(parseFraction[1]);
            }
        } else {
            throw new NumberFormatException();
        }
    }
    
    //Overrides Comparable interface method to compare the value of between Fraction objects
    @Override
    public int compareTo(Fraction e) {
        if (this.numerator * e.denominator == this.denominator * e.numerator) {
            return 0;
        } else if (this.numerator * e.denominator < this.denominator * e.numerator) {
            return -1;
        } else
            return 1;
    }
    
    //Overrides the toString method to return the fraction string
    @Override
    public String toString() {
        return fraction;
    }
    
}
