/*
 * File: SequenceGUI.java
 * Author: Allison McDonald
 * Date: April 28, 2019
 * Purpose: CMIS 242 7983 Project 3 - a program that calculates the terms of the
 * following sequence: 0, 1, 2, 5, 12, 29, ..., this is the GUI class - A class
 * that defines the GUI and contains the main method
 */
package project3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class SequenceGUI extends javax.swing.JFrame {

    /**
     * Creates new form SequenceGUI
     */
    public SequenceGUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        radioButtonGroup = new javax.swing.ButtonGroup();
        iterativeRadioButton = new javax.swing.JRadioButton();
        recursiveRadioButton = new javax.swing.JRadioButton();
        enterNLabel = new javax.swing.JLabel();
        enterNField = new javax.swing.JFormattedTextField();
        computeButton = new javax.swing.JButton();
        resultLabel = new javax.swing.JLabel();
        resultField = new javax.swing.JFormattedTextField();
        efficiencyLabel = new javax.swing.JLabel();
        efficiencyField = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();

        // To add radio buttons to button group
        radioButtonGroup.add(iterativeRadioButton);
        radioButtonGroup.add(recursiveRadioButton);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CMIS 242 Project 3");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        iterativeRadioButton.setSelected(true);
        iterativeRadioButton.setText("Iterative");

        recursiveRadioButton.setText("Recursive");

        enterNLabel.setText("Enter n:");

        computeButton.setText("Compute");
        computeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                computeButtonActionPerformed(evt);
            }
        });

        resultLabel.setText("Result:");

        resultField.setEditable(false);

        efficiencyLabel.setText("Efficiency:");

        efficiencyField.setEditable(false);

        jLabel4.setText("Select your calculation method");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(iterativeRadioButton)
                            .addComponent(enterNLabel))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(efficiencyField, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                .addComponent(resultField)
                                .addComponent(enterNField))
                            .addComponent(recursiveRadioButton))
                        .addGap(18, 18, 18)
                        .addComponent(computeButton))
                    .addComponent(jLabel4)
                    .addComponent(resultLabel)
                    .addComponent(efficiencyLabel))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(iterativeRadioButton)
                    .addComponent(recursiveRadioButton))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enterNLabel)
                    .addComponent(enterNField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(computeButton))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resultLabel)
                    .addComponent(resultField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(efficiencyLabel)
                    .addComponent(efficiencyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void computeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_computeButtonActionPerformed
        // When compute button is clicked, a check to verify the number entered is
        // numeric and positive, then the calculation is performed based on which 
        // calculation method was chosen.  An exception is thrown and message
        // shown if number is not numeric or positive
        try {
            int number = checksNIsInteger(enterNField.getText());
            if (iterativeRadioButton.isSelected()) {
                efficiencyField.setText("");
                resultField.setText(Integer.toString(Sequence.computeIterative(number)));
                efficiencyField.setText(Integer.toString(Sequence.getEfficiency()));
            } else {
                efficiencyField.setText("");
                resultField.setText(Integer.toString(Sequence.computeRecursive(number)));
                efficiencyField.setText(Integer.toString(Sequence.getEfficiency()));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "N must be zero or a positive integer");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }
    }//GEN-LAST:event_computeButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // Writes efficiency values on n from 0 to 10 when window is closing
        try {
            file = new File("SequenceOutput.txt");
            output = new FileWriter(file.getAbsoluteFile());
            outputStream = new BufferedWriter(output);
            int i = 0;
            while (i <= 10) {
                String number = Integer.toString(i);
                Sequence.computeIterative(i);
                String iterative = Integer.toString(Sequence.getEfficiency());
                Sequence.computeRecursive(i);
                String recursive = Integer.toString(Sequence.getEfficiency());
                outputStream.write(number + "," + iterative + "," + recursive + "\r\n");
                i++;
            }
            outputStream.close();
        } catch (IOException io) {
            JOptionPane.showMessageDialog(frame, io.getMessage());
        }
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SequenceGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SequenceGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SequenceGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SequenceGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SequenceGUI().setVisible(true);
            }
        });
    }
    
    // Checks that n is an integer, parses and returns number in integer format
    private int checksNIsInteger(String n) throws NumberFormatException, Exception {
        int number = Integer.parseInt(n);
        if (number >= 0) {
            return number;
        } else {
            throw new Exception("N must be zero or a positive integer");
        }
    }
    
    // JOptionPane declaration
    private JOptionPane frame = new JOptionPane();
    
    // Variables for writing to a file declaration
    private File file = null;
    private FileWriter output = null;
    private BufferedWriter outputStream = null;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton computeButton;
    private javax.swing.JFormattedTextField efficiencyField;
    private javax.swing.JLabel efficiencyLabel;
    private javax.swing.JFormattedTextField enterNField;
    private javax.swing.JLabel enterNLabel;
    private javax.swing.JRadioButton iterativeRadioButton;
    private javax.swing.JLabel jLabel4;
    private javax.swing.ButtonGroup radioButtonGroup;
    private javax.swing.JRadioButton recursiveRadioButton;
    private javax.swing.JFormattedTextField resultField;
    private javax.swing.JLabel resultLabel;
    // End of variables declaration//GEN-END:variables
}
