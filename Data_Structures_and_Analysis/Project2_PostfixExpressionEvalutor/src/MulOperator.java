/*
 * File: Operator.java
 * Author: Allison McDonald, modified from Module 2 Expression Trees in week 4 content
 * Date: June 30, 2019
 * Purpose: CMSC 350 Project 2 - a program that evaluates postfix expressions,
 * this is the MulOperator class - This class implements the Operator abstract class
 */

public class MulOperator extends Operator {
    @Override
    public String getOperatorName() {
        return "Mul";
    }
    
    @Override
    public String toString() {
        return "*";
    }
    
}
