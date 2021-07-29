/*
 * File: ArrayStack.java
 * Author: Pulled from Stacks and Linked Data Structures PDF in week 2 content
 * Date: June 30, 2019
 * Purpose: CMSC 350 Project 2 - a program that evaluates postfix expression, this 
 * is the ArrayStack class - This class implements the Stack interface
 */

/**
 * @param <Node>
 */

public class ArrayStack<Node> implements Stack<Node> {
    private static int DEFAULT_SIZE = 10;
    Node elements[];
    int numberOfElements = 0;
    
    public ArrayStack() {
        this(DEFAULT_SIZE);
    }
    
    @SuppressWarnings({"unchecked", "deprecated"})
    public ArrayStack(int size) {
        elements = (Node[]) (new Object[size]);
    }
    
    @Override
    public void push(Node item) throws StackException {
        if (numberOfElements == elements.length)
            throw new StackException("Stack is Full");
        elements[numberOfElements] = item;
        numberOfElements++;
    }
    
    @Override
    public Node pop() throws StackException {
        if (numberOfElements == 0)
            throw new StackException("Stack is Empty");
        Node elementToReturn = elements[numberOfElements - 1];
        numberOfElements--;
        return elementToReturn;
    }
    
}
