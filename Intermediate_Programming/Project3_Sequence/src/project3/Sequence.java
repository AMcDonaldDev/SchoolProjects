/*
 * File: Sequence.java
 * Author: Allison McDonald
 * Date: April 26, 2019
 * Purpose: CMIS 242 7983 Project 3 - a program that calculates the terms of the
 * following sequence: 0, 1, 2, 5, 12, 29, ..., this is the Sequence class - A
 * utility class
 */
package project3;

public class Sequence {
    // class variables to return number of calls to iterative or recursive methods
    private static int efficiency = 0;
    private static int count = 0;
    
    // Iterative method
    public static int computeIterative(int number) {
        efficiency = 0;
        int result = 0;
        switch (number) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                int previousTerm = 1;
                int secondPreviousTerm = 0;
                for (int i = 2; i <= number; i++) {
                    count++;
                    result = (2 * previousTerm) + secondPreviousTerm;
                    secondPreviousTerm = previousTerm;
                    previousTerm = result;
                }
                efficiency = count;
        }
        return result;
    }
    
    // Recursive method
    public static int computeRecursive(int number) {
        efficiency = 0;
        count ++;
        int result = 0;
        switch (number) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                result = (2 * computeRecursive(number - 1)) + computeRecursive(number - 2);
        }
        efficiency = count;
        return result;
    }
    
    // Efficiency method - returns the number of calls
    public static int getEfficiency() {
        count = 0;
        return efficiency;
    }
    
}
