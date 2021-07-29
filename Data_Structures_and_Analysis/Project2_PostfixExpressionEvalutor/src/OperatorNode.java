/*
 * File: OperatorNode.java
 * Author: Allison McDonald, modified from Module 2 Expression Trees in week 4 content
 * Date: June 30, 2019
 * Purpose: CMSC 350 Project 2 - a program that evaluates postfix expressions,
 * this is the OperatorNode class - This class implements the Node interface
 */

public class OperatorNode implements Node {
    //Declare variables
    private Node right;
    private Node left;
    private Operator operator;
    
    private static int registerCount = 0;
    private String register = "";
    
    //Constructor
    public OperatorNode(Operator operator, Node left, Node right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
    
    //Method to reset register counter
    public static void resetRegisterCounter() {
        registerCount = 0;
    }
    
    //Method to traverse the postfix tree and provide the infix expression
    @Override
    public String infixWalk() {
        String rightNumber = right.infixWalk();
        String leftNumber = left.infixWalk();
        return "(" + leftNumber + " " + operator + " " + rightNumber + ")";
    }
    
    @Override
    public String postOrderAssemblyWalk() {
        String leftOp = left.postOrderAssemblyWalk();
        String rightOp = right.postOrderAssemblyWalk();

        
        String value = postOrderValueWalk();
        
        String op = operator.getOperatorName() + " " + value + " " +
                left.postOrderValueWalk() + " " + right.postOrderValueWalk() + " ";
        
        return leftOp + rightOp + op + "\r\n";
    }
    
    @Override
    public String postOrderValueWalk() {
        if ("".equals(register)) {
            register = "R" + registerCount++;
        }
        return register;
    }
    
}
