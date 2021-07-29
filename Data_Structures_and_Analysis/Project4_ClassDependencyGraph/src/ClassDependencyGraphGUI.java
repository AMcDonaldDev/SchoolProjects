/*
 * File: ClassDependencyGraphGUI.java
 * Author: Allison McDonald
 * Date: July 28, 2019
 * Purpose: CMSC 350 Project 4 - a program that behaves like the Java command
 * line compiler.  This is the GUI class - it defines the GUI and contains the
 * main method.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class ClassDependencyGraphGUI extends JFrame {
    // Declare variables
    private JLabel inputFileNameLabel;
    private JTextField inputFileNameField;
    private JButton buildDirectedGraphButton;
    private JLabel classToRecompileLabel;
    private JTextField classToRecompileField;
    private JButton topologicalOrderButton;
    private JTextField recompilationOrderField;
    private JOptionPane frame = new JOptionPane();
    private DirectedGraph graph;
    
    // Constructor
    public ClassDependencyGraphGUI() {
        setFrame();
    }
    
    // Method to initalize frame
    private void setFrame() {
        // Initialize variables
        inputFileNameLabel = new JLabel("Input file name:", JLabel.CENTER);
        inputFileNameField = new JTextField();
        buildDirectedGraphButton = new JButton("Build Directed Graph");
        classToRecompileLabel = new JLabel("Class to recompile:", JLabel.CENTER);
        classToRecompileField = new JTextField();
        topologicalOrderButton = new JButton("Topological Order");
        recompilationOrderField = new JTextField();
        
        // Set default upon closing the frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // Set frame layout
        setTitle("Class Dependency Graph");
        setLayout(new BorderLayout(15, 15));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setResizable(false);
        setSize(580, 360);
        setLocationRelativeTo(null);
        
        // Set input file and recompile layout
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 3, 15, 15));
        inputPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(), 
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        inputPanel.add(inputFileNameLabel);
        inputPanel.add(inputFileNameField);
        inputPanel.add(buildDirectedGraphButton);
        inputPanel.add(classToRecompileLabel);
        inputPanel.add(classToRecompileField);
        inputPanel.add(topologicalOrderButton);
        add(inputPanel, BorderLayout.NORTH);
        
        // Set recompilation layout
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new BorderLayout());
        orderPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Recompilation Order"),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        recompilationOrderField.setEditable(false);
        recompilationOrderField.setBackground(Color.WHITE);
        recompilationOrderField.setHorizontalAlignment(JTextField.CENTER);
        orderPanel.add(recompilationOrderField);
        add(orderPanel, BorderLayout.CENTER);
        
        // Build Directed Graph button listener
        buildDirectedGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                buildDirectedGraphButtonActionPerformed(evt);
            }
        });
        
        // Topological Order button listener
        topologicalOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                topologicalOrderButtonActionPerformed(evt);
            }
        });
    }
    
    // Set visibility
    public void display() {
        setVisible(true);
    }
    
    // Build Directed Graph button action performed method
    private void buildDirectedGraphButtonActionPerformed(ActionEvent evt) {
        String fileName = inputFileNameField.getText();
        
        try {
            graph = new DirectedGraph<>(readFile(fileName), String.class);
            JOptionPane.showMessageDialog(frame, "Graph Built Successfully");
        } catch(IOException e1) {
            JOptionPane.showMessageDialog(frame, "File Did Not Open");
        }
    }
    
    // Topological Order button action performed method
    private void topologicalOrderButtonActionPerformed(ActionEvent evt) {
        String className = classToRecompileField.getText();
        
        try {
            recompilationOrderField.setText(graph.createTopologicalOrder(className));
        } catch(CycleException e1) {
            JOptionPane.showMessageDialog(frame, e1.getMessage());
        } catch(InvalidClassNameException e2) {
            JOptionPane.showMessageDialog(frame, e2.getMessage());
        }
    }
    
    // Read file and create array list
    private ArrayList<String[]> readFile(String fileName) throws IOException {
       ArrayList<String[]> fileList = new ArrayList<>();
       BufferedReader inputStream = null;
       String fileLine;
       
       // Read file
       inputStream = new BufferedReader(new FileReader(fileName));
       while ((fileLine = inputStream.readLine()) != null) {
           String[] lineData = fileLine.split(" ");
           fileList.add(lineData);
        }
       return fileList;
    }
    
    public static void main(String[] args) {
        ClassDependencyGraphGUI window = new ClassDependencyGraphGUI();
        window.display();
    }
    
}
