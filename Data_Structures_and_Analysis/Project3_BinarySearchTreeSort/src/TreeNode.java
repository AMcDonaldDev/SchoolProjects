/*
 * File: TreeNode.java
 * Sourced From: Y. Daniel Liang's book: Introduction to Java Programming 10th Edition (Chapter 25)
 * Date: July 13, 2019
 * Purpose: CMSC 350 Project 3 - a program that performs a sort by using a binary
 * search tree. This generic class creates a TreeNode object that is used to
 * build the binary search tree
 */

/**
 * @param <E>
 */

public class TreeNode<E extends Comparable<E>> {
    //Declare variables
    E element;
    TreeNode<E> right;
    TreeNode<E> left;
    
    //Constructor
    public TreeNode(E e) {
        this.element = e;
        this.right = null;
        this.left = null;
    }
    
}
