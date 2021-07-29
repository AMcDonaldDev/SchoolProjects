/*
 * File: PostFixGUI.java
 * Author: Allison McDonald
 * Date: June 30, 2019
 * Purpose: CMSC 350 Project 2 - a program the accepts postfix expressions, 
 * returns a fully parenthesized infix expression and generates a file that contains
 * the three address format instructions.  This is the GUI class - it defines the GUI
 * and contains the main method
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PostfixGUI extends JFrame {
    //Declare variables
    private JLabel expressionLabel;
    private JTextField expressionField;
    private JButton constructButton;
    private JLabel resultLabel;
    private JTextField resultField;
    private JOptionPane frame = new JOptionPane();
    private File file = null;
    private FileWriter output = null;
    private BufferedWriter outputStream = null;
    
    
    //Constructor
    public PostfixGUI() {
        setFrame();
    }
    
    //Method to initalize frame
    private void setFrame() {
        //Initialize variables
        expressionLabel = new JLabel("Enter Postfix Expression:", JLabel.CENTER);
        expressionField = new JTextField();
        constructButton = new JButton("Construct Tree");
        resultLabel = new JLabel("Infix Expression:", JLabel.CENTER);
        resultField = new JTextField();
        
        //Set default upon closing the frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        //Set frame layout
        setTitle("Three Address Generator");
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
        JPanel constructButtonPanel = new JPanel();
        constructButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        constructButtonPanel.add(constructButton);
        add(constructButtonPanel, BorderLayout.CENTER);
        
        //Set result label and field layout
        JPanel resultOutputPanel = new JPanel();
        resultOutputPanel.setLayout(new GridLayout());
        resultField.setEditable(false);
        resultOutputPanel.add(resultLabel);
        resultOutputPanel.add(resultField);
        add(resultOutputPanel, BorderLayout.SOUTH);
        
        //Construct Tree button listener
        constructButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
               constructButtonActionPerformed(evt); 
            } catch (StackException e1) {
                JOptionPane.showMessageDialog(frame, e1);
            } catch (TokenException e2) {
                JOptionPane.showMessageDialog(frame, e2.getMessage());
            }
        }
    });
    }
    
    //Set visibility
    public void display() {
        setVisible(true);
    }
    
    //Construct Tree button action performed method
    private void constructButtonActionPerformed(ActionEvent evt) throws StackException, TokenException {
        try {
            String expression = expressionField.getText();
            Evaluator postfixExpression = new Evaluator();
            Node root = postfixExpression.buildTree(expression);
            file = new File("3AddressFormat.txt");
            output = new FileWriter(file.getAbsoluteFile());
            outputStream = new BufferedWriter(output);
            outputStream.write(root.postOrderAssemblyWalk());
            outputStream.close();
            OperatorNode.resetRegisterCounter();
            String infixExpression = root.infixWalk();
            //String assemblyOps = root.postOrderAssemblyWalk();
            resultField.setText(infixExpression);
        } catch (StackException e1) {
            JOptionPane.showMessageDialog(frame, e1);
        } catch (TokenException e2) {
            JOptionPane.showMessageDialog(frame, e2.getMessage());
        } catch (IOException io) {
            JOptionPane.showMessageDialog(frame, io.getMessage());
        }
        
    }
    
    public static void main(String[] args) throws StackException, TokenException {
        PostfixGUI window = new PostfixGUI();
        window.display();
    }
    
}
