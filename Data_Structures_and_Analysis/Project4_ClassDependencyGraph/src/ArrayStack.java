/*
 * File: ArrayStack.java
 * Author: Pulled from Stacks and Linked Data Structures PDF in weekly content
 * Date: July 28, 2019
 * Purpose: CMSC 350 Project 4 - a program that behaves like the Java command
 * line compiler.  This class implements the Stack interface
 */

public class ArrayStack<V> implements Stack<V> {
    V elements[];
    int numberOfElements = 0;
    
    @SuppressWarnings({"unchecked", "deprecated"})
    public ArrayStack(int size){
        elements = (V[]) (new Object[size]);
    }
    
    @Override
    public boolean isEmpty() {
        if (numberOfElements == 0) {
            return true;
        } else
            return false;
    }
    
    @Override
    public void push(V item) {
        elements[numberOfElements] = item;
        numberOfElements++;
    }
    
    @Override
    public V pop() {
        V elementToReturn = elements[numberOfElements - 1];
        numberOfElements--;
        return elementToReturn;
    }
    
}
