/*
 * File: Evaluator.java
 * Author: Allison McDonald
 * Date: June 30, 2019
 * Purpose: CMSC 350 Project 2 - a program that evaluates postfix expressions, this is the
 * Evaluator class - This class builds the arithmetic expression tree
 */

public class Evaluator {
    //Declare variables
    private Stack<Node> stack;
    private Node root;
    
    //Default constructor
    public Evaluator() {
    }
    
    //Method to build tree from postfix notation and returns infix expression
    public Node buildTree(String expression) throws StackException, TokenException {
        //Tokenize the postfix expression
        String[] postfixExpression = expression.split("((?<=[\\+\\-\\*\\/\\ ])|(?=[\\+\\-\\*\\/\\ ]))");
        //Initialize tree stack
        stack = new ArrayStack<>(15);
        //Declare and initialize postfixExpression array counter
        int i = 0;
        //Evaluates tokens for stack pushes and pops to create tree
        while (i < postfixExpression.length) {
            String token = postfixExpression[i];
            if (token.matches("[0-9]\\d+|[0-9]")) {
                stack.push(new OperandNode(Integer.parseInt(token)));
            } else if (token.matches(" ")) {
                i++;
                continue;
            } else if (token.matches("[\\+\\-\\*\\/]")) {
                Node right = (Node) stack.pop();
                Node left = (Node) stack.pop();
                Operator op = getOperator(token);
                stack.push(new OperatorNode(op, left, right));
            } else {
                throw new TokenException(token);
            }
            i++;
        }
        root = stack.pop();
        return root;
    }
    
    //Method to determine Operator class
    public Operator getOperator(String token) {
        Operator operator = null;
        switch (token) {
            case "+":
                operator = new AddOperator();
                break;
            case "-":
                operator = new SubOperator();
                break;
            case "*":
                operator = new MulOperator();
                break;
            case "/":
                operator = new DivOperator();
                break;
        }
        return operator;
    }
    
}
