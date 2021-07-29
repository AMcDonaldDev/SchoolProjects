/*
 * File: Node.java
 * Author: Allison McDonald, modified from Module 2 Expression Trees in week 4 content
 * Date: June 30, 2019
 * Purpose: CMSC 350 Project 2 - a program that evaluates postfix expressions, this
 * is the Node interface - This is a user defined interface used with the Operand Node class
 * and OperatorNode class to define the methods that can be performed on a node
 */

public interface Node {
    public String infixWalk();
    public String postOrderAssemblyWalk();
    public String postOrderValueWalk();
}
