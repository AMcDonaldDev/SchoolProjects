/*
 * File: GrammerProject.java
 * Author: Allison McDonald
 * Date: 6/16/2020
 * Purpose: CMSC 330 Project 1 Main class and File Input
 */

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;

public class GrammerProject extends JFrame {
    private JTextField inputFileNameField;
    private String fileName;
    private JOptionPane pane = new JOptionPane();
    
    public GrammerProject() {
        setWindow();
    }
    
    private void setWindow() {
        JPanel panel = new JPanel();
        JLabel inputFileNameLabel = new JLabel("Input file name: ");
        inputFileNameField = new JTextField();
        JButton inputButton = new JButton("Enter");
        setTitle("File Input");
        setLayout(new FlowLayout());
        setSize(300, 300);
        panel.setLayout(new GridLayout(2, 2, 5, 5));
        panel.add(inputFileNameLabel);
        panel.add(inputFileNameField);
        panel.add(inputButton);
        add(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            inputButtonActionPerformed(evt);
            }
        });
    }
    
    private void inputButtonActionPerformed(ActionEvent evt) {
        fileName = inputFileNameField.getText();
        try{
            Lexer lexer = new Lexer(fileName);
            Output frame = new Output();
            Parser parser = new Parser(lexer, frame);
            parser.parseGUI();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch(FileNotFoundException e) {
            JOptionPane.showMessageDialog(pane, e.getMessage());
        } catch(IOException e) {
            JOptionPane.showMessageDialog(pane, e.getMessage());
        }
    }
    
    private void display() {
        setVisible(true);
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        GrammerProject window = new GrammerProject();
        window.display();

    }

}
