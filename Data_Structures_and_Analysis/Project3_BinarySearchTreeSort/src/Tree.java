/*
 * File: Tree.java
 * Author: Allison McDonald
 * Date: July 13, 2019
 * Purpose: CMSC 350 Project 3 - a program that performs a sort by using a binary
 * search tree. This is a user defined generic interface used with the BinarySearchTree class
 * to define the methods that can be performed on a tree
 */

/**
 * @param <E>
 */

public interface Tree<E> {
    public void insert(E e);
    public String ascendingOrder();
    public String descendingOrder();
    
}
