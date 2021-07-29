/*
 * File: InfixGUI.java
 * Author: Allison McDonald
 * Date: June 16, 2019
 * Purpose: CMSC 350 Project 1 - a program that evaluates infix expressions, this
 * this is the GUI class - This class defines the GUI and contains the main method
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InfixGUI extends JFrame {
    //Declare variables
    private JLabel expressionLabel;
    private JTextField expressionField;
    private JButton evaluateButton;
    private JLabel resultLabel;
    private JTextField resultField;
    private JOptionPane frame = new JOptionPane();
    
    //Constructor
    public InfixGUI() {
        setFrame();
    }
    
    //Method to initialize frame
    private void setFrame() {
        //Initialize variables
        expressionLabel = new JLabel("Enter Infix Expression:", JLabel.CENTER);
        expressionField = new JTextField();
        evaluateButton = new JButton("Evaluate");
        resultLabel = new JLabel("Result:", JLabel.CENTER);
        resultField = new JTextField();
        
        //Set default upon closing the frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        //Set frame layout
        setTitle("Infix Expression Evaluator");
        setLayout(new BorderLayout());
        setResizable(false);
        setSize(400,110);
        setLocationRelativeTo(null);
        
        //Set expression label and field layout
        JPanel expressionInputPanel = new JPanel();
        expressionInputPanel.setLayout(new GridLayout());
        expressionInputPanel.add(expressionLabel);
        expressionInputPanel.add(expressionField);
        add(expressionInputPanel, BorderLayout.NORTH);
        
        //Set button layout
        JPanel evaluateButtonPanel = new JPanel();
        evaluateButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        evaluateButtonPanel.add(evaluateButton);
        add(evaluateButtonPanel, BorderLayout.CENTER);
        
        //Set result label and field layout
        JPanel resultOutputPanel = new JPanel();
        resultOutputPanel.setLayout(new GridLayout());
        resultField.setEditable(false);
        resultOutputPanel.add(resultLabel);
        resultOutputPanel.add(resultField);
        add(resultOutputPanel, BorderLayout.SOUTH);
        
        //Evaluate button listener
        evaluateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    evaluateButtonActionPerformed(evt);
                } catch (StackException e1) {
                    JOptionPane.showMessageDialog(frame, e1);
                } catch (ZeroDivideException e2) {
                    JOptionPane.showMessageDialog(frame, e2.getMessage());
                }
            }
        });
    }
    
    //Set visibility
    public void display() {
        setVisible(true);
    }
    
    //Evaluate button action performed method
    private void evaluateButtonActionPerformed(ActionEvent evt) throws StackException, ZeroDivideException {
        try {
            String expression = expressionField.getText().replaceAll(" ","");
            Calculate infixExpression = new Calculate();
            String result = infixExpression.evaluateExpression(expression);
            resultField.setText(result);
        } catch (StackException e1) {
            JOptionPane.showMessageDialog(frame, e1);
        } catch (ZeroDivideException e2) {
            JOptionPane.showMessageDialog(frame, e2.getMessage());
        }
    }
    
    public static void main(String args[]) throws StackException, ZeroDivideException {
        InfixGUI window = new InfixGUI();
        window.display();
    }
    
}
