/*
 * File: Stack.java
 * Author: Pulled from Stacks and Linked Data Structures PDF in weekly content
 * Date: June 16, 2019
 * Purpose: CMSC 350 Project 1 - a program that evaluates infix expressions, this
 * is the Stack interface - This is a user defined interface used with the
 * Array Stack class to define the operations that can be performed on a stack
 */

public interface Stack<E> {
    public boolean isFull();
    public boolean isEmpty();
    public E peek() throws StackException;
    public void push(E item) throws StackException;
    public E pop() throws StackException;
}
