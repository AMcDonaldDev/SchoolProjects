/*
 * File: Operator.java
 * Author: Allison McDonald, modified from Module 2 Expression Trees in week 4 content
 * Date: June 30, 2019
 * Purpose: CMSC 350 Project 2 - a program that evaluates postfix expressions,
 * this is the SubOperator class - This class implements the Operator abstract class
 */

public class SubOperator extends Operator {
    @Override
    public String getOperatorName() {
        return "Sub";
    }
    
    @Override
    public String toString() {
        return "-";
    }
    
}
