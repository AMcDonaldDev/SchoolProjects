/*
 * File: BSTGUI.java
 * Author: Allison McDonald
 * Date: July 13, 2019
 * Purpose: CMSC 350 Project 3 - a program that performs a sort by using a binary
 * search tree. This is the GUI class - it defines the GUI and contains the
 * main method.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BSTGUI extends JFrame {
    //Declare variables
    private JLabel originalListLabel;
    private JTextField originalListField;
    private JLabel sortedListLabel;
    private JTextField sortedListField;
    private JButton sortButton;
    private JLabel sortOrderLabel;
    private JRadioButton ascendingRadioButton;
    private JRadioButton descendingRadioButton;
    private ButtonGroup sortRadioButtonGroup;
    private JLabel numericTypeLabel;
    private JRadioButton integerRadioButton;
    private JRadioButton fractionRadioButton;
    private ButtonGroup numericRadioButtonGroup;
    private JOptionPane frame = new JOptionPane();
    
    //Constructor
    public BSTGUI() {
        setFrame();
    }
    
    //Method to initalize frame
    private void setFrame() {
        //Initialize variables
        originalListLabel = new JLabel("Original List", JLabel.CENTER);
        originalListField = new JTextField();
        sortedListLabel = new JLabel("Sorted List", JLabel.CENTER);
        sortedListField = new JTextField();
        sortButton = new JButton("Perform Sort");
        sortOrderLabel = new JLabel("Sort Order");
        ascendingRadioButton = new JRadioButton("Ascending");
        descendingRadioButton = new JRadioButton("Descending");
        sortRadioButtonGroup = new ButtonGroup();
        numericTypeLabel = new JLabel("Numeric Type");
        integerRadioButton = new JRadioButton("Integer");
        fractionRadioButton = new JRadioButton("Fraction");
        numericRadioButtonGroup = new ButtonGroup();
        
        //Group radio buttons
        sortRadioButtonGroup.add(ascendingRadioButton);
        sortRadioButtonGroup.add(descendingRadioButton);
        numericRadioButtonGroup.add(integerRadioButton);
        numericRadioButtonGroup.add(fractionRadioButton);
        
        //Pre-set selection of radio buttons
        ascendingRadioButton.setSelected(true);
        integerRadioButton.setSelected(true);
        
        //Set default upon closing the frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        //Set frame layout
        setTitle("Binary Search Tree Sort");
        setLayout(new BorderLayout(10, 10));
        setResizable(false);
        setSize(460,240);
        setLocationRelativeTo(null);
        
        //Set Original and Sorted List layout
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(2,2,10,10));
        listPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        sortedListField.setEditable(false);       
        listPanel.add(originalListLabel);
        listPanel.add(originalListField);
        listPanel.add(sortedListLabel);
        listPanel.add(sortedListField);
        add(listPanel, BorderLayout.NORTH);
        
        //Set Button layout
        JPanel sortButtonPanel = new JPanel();
        sortButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        sortButtonPanel.add(sortButton);
        add(sortButtonPanel, BorderLayout.CENTER);
        
        //Set Sort Order radio buttons layout
        JPanel sortOrderPanel = new JPanel();
        sortOrderPanel.setLayout(new BoxLayout(sortOrderPanel, BoxLayout.Y_AXIS));
        sortOrderPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
        sortOrderPanel.add(sortOrderLabel);
        sortOrderPanel.add(ascendingRadioButton);
        sortOrderPanel.add(descendingRadioButton);        

        //Set Numeric Type radio buttons layout
        JPanel numericTypePanel = new JPanel();
        numericTypePanel.setLayout(new BoxLayout(numericTypePanel, BoxLayout.Y_AXIS));
        numericTypePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
        numericTypePanel.add(numericTypeLabel);
        numericTypePanel.add(integerRadioButton);
        numericTypePanel.add(fractionRadioButton);
        
        //Set combined radio button layout
        JPanel radioButtonPanel = new JPanel();
        radioButtonPanel.setLayout(new GridLayout(1,1,10,10));
        radioButtonPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        radioButtonPanel.add(sortOrderPanel);
        radioButtonPanel.add(numericTypePanel);
        add(radioButtonPanel, BorderLayout.SOUTH);
        
        //Perform Sort button listener
        sortButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            sortButtonActionPerformed(evt);
        }
        });
    }
    
    //Set visibility
    public void display() {
        setVisible(true);
    }
    
    //Perform Sort button action performed method
    private void sortButtonActionPerformed(ActionEvent evt) {
        int i = 0;
        String token;
        Tree<Integer> integerTree;
        Tree<Fraction> fractionTree;
        try {
          String[] list = originalListField.getText().split(" ");
          if (integerRadioButton.isSelected()) {
              integerTree = new BinarySearchTree<>();
              while (i < list.length) {
                  token = list[i];
                  int number = Integer.parseInt(token);
                  integerTree.insert(number);
                  i++;
              } if (ascendingRadioButton.isSelected()) {
                  sortedListField.setText(integerTree.ascendingOrder());
              } else {
                  sortedListField.setText(integerTree.descendingOrder());
              }
          } else {
              fractionTree = new BinarySearchTree<>();
              while (i < list.length) {
                  token = list[i];
                  Fraction fraction = new Fraction(token);
                  fractionTree.insert(fraction);
                  i++;
              } if (ascendingRadioButton.isSelected()) {
                  sortedListField.setText(fractionTree.ascendingOrder());
              } else {
                  sortedListField.setText(fractionTree.descendingOrder());
              }
          }
        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(frame, "Non numeric input");
        }
    }
    
    public static void main(String[] args) {
        BSTGUI window = new BSTGUI();
        window.display();
    }
    
}
