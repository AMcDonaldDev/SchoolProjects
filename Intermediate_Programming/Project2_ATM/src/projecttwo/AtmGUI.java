/*
 * File: AtmGUI.java
 * Author: Allison McDonald
 * Date: April 14, 2019
 * Purpose: CMIS 242 7983 Project 2 - a program that implements an ATM, this is
 * the GUI class - A class that defines the GUI and contains the main method
 */
package projecttwo;

import javax.management.OperationsException;
import javax.swing.JOptionPane;

public class AtmGUI extends javax.swing.JFrame {

    /**
     * Creates new form AtmGUI
     */
    public AtmGUI() {
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
        amountLabel = new javax.swing.JLabel();
        amountField = new javax.swing.JFormattedTextField();
        accountLabel = new javax.swing.JLabel();
        checkingRadioButton = new javax.swing.JRadioButton();
        savingsRadioButton = new javax.swing.JRadioButton();
        transactionLabel = new javax.swing.JLabel();
        balanceButton = new javax.swing.JButton();
        depositButton = new javax.swing.JButton();
        transferToButton = new javax.swing.JButton();
        withdrawButton = new javax.swing.JButton();

        // Group radio buttons
        radioButtonGroup.add(checkingRadioButton);
        radioButtonGroup.add(savingsRadioButton);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Automated Teller Machine");
        setResizable(false);

        amountLabel.setText("Enter Amount:");

        amountField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        accountLabel.setText("Select Account:");

        checkingRadioButton.setText("Checking");

        savingsRadioButton.setText("Savings");

        transactionLabel.setText("Chose Transaction Type:");

        balanceButton.setText("Balance");
        balanceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceButtonActionPerformed(evt);
            }
        });

        depositButton.setText("Deposit");
        depositButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depositButtonActionPerformed(evt);
            }
        });

        transferToButton.setText("Transfer To");
        transferToButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transferToButtonActionPerformed(evt);
            }
        });

        withdrawButton.setText("Withdraw");
        withdrawButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                withdrawButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(amountLabel)
                            .addComponent(accountLabel))
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(checkingRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(amountField))
                        .addGap(18, 18, 18)
                        .addComponent(savingsRadioButton))
                    .addComponent(transactionLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(balanceButton)
                        .addGap(18, 18, 18)
                        .addComponent(depositButton)
                        .addGap(18, 18, 18)
                        .addComponent(transferToButton)
                        .addGap(18, 18, 18)
                        .addComponent(withdrawButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(amountLabel)
                    .addComponent(amountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accountLabel)
                    .addComponent(checkingRadioButton)
                    .addComponent(savingsRadioButton))
                .addGap(18, 18, 18)
                .addComponent(transactionLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(balanceButton)
                    .addComponent(depositButton)
                    .addComponent(transferToButton)
                    .addComponent(withdrawButton))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void balanceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceButtonActionPerformed
        // When balance button is chosen, a check is performed determine which account was selected
        // and if no account is selected, then an exception is thrown and a message is shown
        // When an account is selected, a balance message is shown
        try {
            Account accountType = checksAccountType();
            JOptionPane.showMessageDialog(frame, accountType.toString());
        } catch (NullPointerException e2) {
            JOptionPane.showMessageDialog(frame, e2.getMessage());
        }
        amountField.setText("");
    }//GEN-LAST:event_balanceButtonActionPerformed

    private void depositButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_depositButtonActionPerformed
        // When deposit button is chosen, a check to verify the amount is numeric
        // and a check to determine which account was selected is performed.  An exception is thrown
        // and a message shown if the amount is not numeric or if an account was not selected
        try {
            Double amount = checksAmountNumeric(amountField.getText());
            Account accountType = checksAccountType();
            accountType.makeDeposit(amount);
            JOptionPane.showMessageDialog(frame, "Your deposit transaction is complete.");
        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(frame, "Invalid amount entered.");
        } catch (NullPointerException e2) {
            JOptionPane.showMessageDialog(frame, e2.getMessage());
        }
        amountField.setText("");
    }//GEN-LAST:event_depositButtonActionPerformed

    private void transferToButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transferToButtonActionPerformed
        // When transfer to button is chosen, a check to verify the amount is numeric,
        // a check to determine which account was selected, and a check
        // for sufficient funds is performed.  An exception is thrown
        // and a message shown if the amount is not numeric, if an account was not selected,
        // or if there are insufficient funds.
        try {
            Double amount = checksAmountNumeric(amountField.getText());
            Account accountType = checksAccountType();
            if (accountType == accountData[0]) {
                accountData[1].outTransfer(amount);
                accountType.inTransfer(amount);
            } else if (accountType == accountData[1]) {
                accountData[0].outTransfer(amount);
                accountType.inTransfer(amount);
            }
            JOptionPane.showMessageDialog(frame, "Your transfer transaction is complete.");
        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(frame, "Invalid amount entered.");
        } catch (NullPointerException e2) {
            JOptionPane.showMessageDialog(frame, e2.getMessage());
        } catch (InsufficientFunds e3) {
            JOptionPane.showMessageDialog(frame, e3.insufficientFunds());
        }
        amountField.setText("");
    }//GEN-LAST:event_transferToButtonActionPerformed

    private void withdrawButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_withdrawButtonActionPerformed
        // When withdraw button is chosen, a check to verify the amount is numeric,
        // a check to determine which account was selected, a check
        // for sufficient funds, and a check that the amount is in increments of $20.00 is performed.
        // An exception is thrown and a message shown if the amount is not numeric,
        // if an account was not selected, if there are insufficient funds, or if the
        // amount is not in $20.00 increments.
        try {
            Double amount = checksAmountNumeric(amountField.getText());
            Account accountType = checksAccountType();
            divisibleBy20(amount);
            accountType.makeWithdraw(amount);
            JOptionPane.showMessageDialog(frame, "Your withdrawal transaction is complete.");
        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(frame, "Invalid amount entered.");
        } catch (NullPointerException e2) {
            JOptionPane.showMessageDialog(frame, e2.getMessage());
        } catch (InsufficientFunds e3) {
            JOptionPane.showMessageDialog(frame, e3.insufficientFunds());
        } catch (OperationsException e4) {
            JOptionPane.showMessageDialog(frame, e4.getMessage());
        }
        amountField.setText("");
    }//GEN-LAST:event_withdrawButtonActionPerformed

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
            java.util.logging.Logger.getLogger(AtmGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AtmGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AtmGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AtmGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        // Constructing Account objects to array
        accountData[0] = new Account("Checking", 1000.00);
        accountData[1] = new Account("Savings", 500.00);
       
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AtmGUI().setVisible(true);
            }
        });
    }
    
    // Checks that amount field is numeric, parses, and returns number in double format
    private Double checksAmountNumeric(String amount) throws NumberFormatException {
        return Double.parseDouble(amount);
    }
    
    // Checks that amount field is in an increment of $20
    private void divisibleBy20(Double amount) throws OperationsException {
        Double increment = 20.00;
        if (amount % increment != 0) {
            throw new OperationsException("Withdrawal amount must be in increments of $20.00.");
        }
    }
    
    // Checks which radio button is selected and returns correct account index
    private Account checksAccountType() throws NullPointerException {
        if (checkingRadioButton.isSelected()) {
            return accountData[0];
        } else if (savingsRadioButton.isSelected()) {
            return accountData[1];
        } else {
            throw new NullPointerException("Please select an account.");
        }
    }
    
    // Account array declaration
    private static Account[] accountData = new Account[2];
    
    // JOptionPane declaration
    private JOptionPane frame = new JOptionPane();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel accountLabel;
    private javax.swing.JFormattedTextField amountField;
    private javax.swing.JLabel amountLabel;
    private javax.swing.JButton balanceButton;
    private javax.swing.JRadioButton checkingRadioButton;
    private javax.swing.JButton depositButton;
    private javax.swing.ButtonGroup radioButtonGroup;
    private javax.swing.JRadioButton savingsRadioButton;
    private javax.swing.JLabel transactionLabel;
    private javax.swing.JButton transferToButton;
    private javax.swing.JButton withdrawButton;
    // End of variables declaration//GEN-END:variables
}
