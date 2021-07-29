/*
 * File: Calculate.java
 * Author: Allison McDonald
 * Date: June 16, 2019
 * Purpose: CMSC 350 Project 1 - a program that evaluates infix expression, this
 * is the Calculate class - This class evaluates and calculates the infix expression
 */

public class Calculate {
    //Default constructor
    public Calculate() { 
    }
    
    //Method to evaluate infix expression
    public String evaluateExpression(String expression) throws StackException, ZeroDivideException {
        //Tokenize the infix expression
        String[] infixExpression = expression.split("((?<=[\\+\\-\\*\\/\\(\\)])|(?=[\\+\\-\\*\\/\\(\\)]))");
        //Declare and initialize the stacks
        Stack operand = new ArrayStack(15);
        Stack operator = new ArrayStack(15);
        //Declare and initialize infixExpression array counter
        int i = 0;
        //Evaluates tokens for stack pushes, pops and calculations
        while (i < infixExpression.length) {
           String token = infixExpression[i];
           if (token.matches("[0-9]\\d+|[0-9]")) {
               operand.push(Integer.parseInt(token));
           } else if (token.equals("(")) {
               operator.push(token);
           } else if (token.equals(")")) {
               while (!"(".equals(operator.peek().toString())) {
                   int num2 = (int) operand.pop();
                   int num1 = (int) operand.pop();
                   String op = (String) operator.pop();
                   int numNew = calculation(op,num1,num2);
                   operand.push(numNew);
               }
               operator.pop();
           } else if (token.matches("[\\+\\-\\*\\/]")) {
               if (operator.isEmpty()) {
                   operator.push(token);
               } else {
                   while (!operator.isEmpty()) {
                       Object opPeek = operator.peek();
                       int tokenOpPre = precedence(token);
                       int opPeekPre = precedence(operator.peek().toString());
                       if ("(".equals(opPeek.toString())) {
                           operator.push(token);
                           break;
                       } else if (tokenOpPre <= opPeekPre) {
                           int num2 = (int) operand.pop();
                           int num1 = (int) operand.pop();
                           String op = (String) operator.pop();
                           int numNew = calculation(op,num1,num2);
                           operand.push(numNew);
                           if (operator.isEmpty()) {
                               operator.push(token);
                               break;
                           } else {
                               opPeekPre = precedence(operator.peek().toString());
                           }
                       } else {
                           operator.push(token);
                           break;
                       }
                   }
               }
           }
           i++;
        }
        while (!operator.isEmpty()) {
            int num2 = (int) operand.pop();
            int num1 = (int) operand.pop();
            String op = (String) operator.pop();
            int numNew = calculation(op,num1,num2);
            operand.push(numNew);
        }
        return operand.pop().toString();
    }
    
    //Method to calculate popped operands and operators
    public int calculation(String op, int num1, int num2) throws ZeroDivideException {
        int result = 0;
        switch (op) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new ZeroDivideException();
                }
                result = num1 / num2;
                break;
        }
        return result;
    }
    
    //Method to assign precedence to arithmetic operators
    public int precedence(String op) {
        int precedence = 0;
        switch (op) {
            case "+":
                precedence = 1;
                break;
            case "-":
                precedence = 1;
                break;
            case "*":
                precedence = 2;
                break;
            case "/":
                precedence = 2;
                break;
        }
        return precedence;
    }
    
}
