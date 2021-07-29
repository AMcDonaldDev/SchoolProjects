/*
 * File: Operator.java
 * Author: Allison McDonald, modified from Module 2 Expression Trees in week 4 content
 * Date: June 30, 2019
 * Purpose: CMSC 350 Project 2 - a program that evaluates postfix expressions,
 * this is the Operator class - This class is a user defined abstract class
 * used with AddOperator, DivOperator, MulOperator, and SubOperator classes 
 * to define the methods that can be performed on an operator
 */

public abstract class Operator {
    abstract public String getOperatorName();
}
