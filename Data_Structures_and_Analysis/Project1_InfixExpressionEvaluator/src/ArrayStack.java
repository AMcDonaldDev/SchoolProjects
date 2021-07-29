/*
 * File: ArrayStack.java
 * Author: Pulled from Stacks and Linked Data Structures PDF in weekly content
 * Date: June 16, 2019
 * Purpose: CMSC 350 Project 1 - a program that evaluates infix expressions, this
 * is the ArrayStack class - This class implements the Stack interface
 */

public class ArrayStack<E> implements Stack<E> {
    private static int DEFAULT_SIZE = 10;
    E elements[];
    int numberOfElements = 0;
    
    public ArrayStack() {
        this(DEFAULT_SIZE);
    }
    
    @SuppressWarnings({"unchecked", "deprecated"})
    public ArrayStack(int size) {
        elements = (E[]) (new Object[size]);
    }
    
    public boolean isFull() {
        if (numberOfElements == elements.length)
            return true;
        else
            return false;
    }
    
    public boolean isEmpty() {
        if (numberOfElements == 0)
            return true;
        else
            return false;
    }
    
    public E peek() throws StackException {
        if (numberOfElements == 0)
            throw new StackException("Stack is Empty");
        return elements[numberOfElements - 1];
    }
    
    public void push(E item) throws StackException {
        if (numberOfElements == elements.length)
            throw new StackException("Stack is Full");
        elements[numberOfElements] = item;
        numberOfElements++;
    }
    
    public E pop() throws StackException {
        if (numberOfElements == 0)
            throw new StackException("Stack is Empty");
        E elementToReturn = elements[numberOfElements - 1];
        elements[numberOfElements - 1] = null;
        numberOfElements--;
        return elementToReturn;
    }
    
}
