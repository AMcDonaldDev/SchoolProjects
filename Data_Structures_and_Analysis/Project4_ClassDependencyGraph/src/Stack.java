/*
 * File: Stack.java
 * Author: Pulled from Stacks and Linked Data Structures PDF in weekly content
 * Date: July 28, 2019
 * Purpose: CMSC 350 Project 4 - a program that behaves like the Java command
 * line compiler.  This is the user defined interface used with the Array Stack
 * class to define operations that can be performed on a stack
 */

public interface Stack<V> {
    public boolean isEmpty();
    public void push(V item);
    public V pop();
}
