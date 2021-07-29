/*
 * File: OperandNode.java
 * Author: Allison McDonald, modified from Module 2 Expression Trees in week 4 content
 * Date: June 30, 2016
 * Purpose: CMSC 350 Project 2 - a program that evaluates postfix expressions,
 * this is the OperandNode class - This class implements the Node interface
 */

public class OperandNode implements Node {
    //Declare variable
    private int number;

    //Constructor
    public OperandNode(int number) {
        this.number = number;
    }
    
    //Method to traverse the postfix tree and provide the infix expression
    @Override
    public String infixWalk() {
        return String.valueOf(number);
    }
    
    @Override
    public String postOrderAssemblyWalk(){
        return "";
    }
    
    @Override
    public String postOrderValueWalk() {
        return String.valueOf(number);
    }
    
}
