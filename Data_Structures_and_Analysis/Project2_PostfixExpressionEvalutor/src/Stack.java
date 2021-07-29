/*
 * File: Stack.java
 * Author: Pulled from Stacks and Linked Data Structures PDF in week 2 content
 * Date: June 30, 2019
 * Purpose: CMSC 350 Project 2 - a program that evaluates postfix expressions,
 * this is the Stack interface - This is a user defined interface used with the
 * Array Stack class to define the methods that can be performed on a stack
 */

public interface Stack <Node> {
    public void push(Node item) throws StackException;
    public Node pop() throws StackException;
    
}
