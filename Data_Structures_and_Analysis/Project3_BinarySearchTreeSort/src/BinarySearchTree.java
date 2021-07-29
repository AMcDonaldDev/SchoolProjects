/*
 * File: BinarySearchTree.java
 * Author: Allison McDonald & Modified code sourced from L. Daniel Liang's book:
 * Introduction to Java Programming 10th Edition (Chapter 25)
 * Date: July 13, 2019
 * Purpose: CMSC 350 Project 3 - a program that performs a sort by using a binary
 * search tree. This generic class implments the Tree interface.
 */

/**
 * @param <E>
 */

public class BinarySearchTree<E extends Comparable<E>> implements Tree<E> {
    //Declare variables
    private TreeNode<E> root;
    private String order = "";
    
    //Constructor
    public BinarySearchTree() {
    }
    
    //Overrides Tree interface method to build the tree
    //This method calls the insertRecursive method
    //Modified code from Introduction to Java Programming 10th Edition
    @Override
    public void insert(E e) {
        root = insertRecursive(root, e);
    }
    
    //Recursive method to build the tree
    //Modified code from Introduction to Java Programming 10th Edition
    private TreeNode<E> insertRecursive(TreeNode<E> root, E e) {
        if (root == null) {
            root = new TreeNode<>(e);
            return root;
        }
        if (e.compareTo(root.element) < 0) {
            root.left = insertRecursive(root.left, e);
            
        } else if (e.compareTo(root.element) >= 0) {
            root.right = insertRecursive(root.right, e);
        }
        return root;
    }
    
    //Overrides Tree interface method to return string of tree in ascending order
    //This method calls the ascendingOrderRecursive method
    //Modified code from Introduction to Java Programming 10th Edition
    @Override
    public String ascendingOrder() {
        ascendingOrderRecursive(root);
        return order;
    }
    
    //Overides Tree interface method to return string of tree in descending order
    //This method calls the decendingOrderRecursive method
    //Modified code from Introduction to Java Programming 10th Edition
    @Override
    public String descendingOrder() {
        descendingOrderRecursive(root);
        return order;
    }
    
    //Recursive method to build ascending order string
    //Modified code from Introduction to Java Programming 10th Edition
    private void ascendingOrderRecursive(TreeNode<E> root) {
        if (root != null) {
            ascendingOrderRecursive(root.left);
            order += root.element + " ";
            ascendingOrderRecursive(root.right);
        }
    }
    
    //Recursive method to build descending order string
    //Modified code from Introduction to Java Programming 10th Edition
    private void descendingOrderRecursive(TreeNode<E> root) {
        if (root != null) {
            descendingOrderRecursive(root.right);
            order += root.element + " ";
            descendingOrderRecursive(root.left);
        }
    }
    
}
