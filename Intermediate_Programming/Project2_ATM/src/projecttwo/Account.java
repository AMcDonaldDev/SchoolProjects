/*
 * File: Account.java
 * Author: Allison McDonald
 * Date: April 13, 2019
 * Purpose: CMIS 242 7983 Project 2 - a program that implements an ATM, this is
 * the Account class - A class that contains an account constructor and provides
 * the account logic
 */
package projecttwo;

import java.text.NumberFormat;

public class Account {
    // Class fields
    private static int numberOfWithdrawals = 0;
    // Instance variables
    private Double accountBalance;
    private final Double serviceCharge = 1.50;
    private String accountType;
    
    // Constructor
    public Account(String accountType, Double accountBalance) {
        this.accountType = accountType;
        this.accountBalance = accountBalance;
    }
    
    // Make Withdraw - Checks for sufficient funds and throws 
    // InsufficientFunds exception if funds are insufficient
    // Service charges begin after 4 withdrawals
    public void makeWithdraw(Double withdraw) throws InsufficientFunds {
        if (numberOfWithdrawals < 4 && accountBalance > withdraw) {
            ++numberOfWithdrawals;
            accountBalance = accountBalance - withdraw;
        } else if (numberOfWithdrawals >= 4 && accountBalance > (withdraw + serviceCharge)) {
            ++numberOfWithdrawals;
            accountBalance = accountBalance - withdraw - serviceCharge;
        } else {
            throw new InsufficientFunds();
        }
    }
    
    // Make Deposit
    public void makeDeposit(Double deposit) {
        accountBalance = accountBalance + deposit;
    }
    
    // Transfer in to account - Must use outTransfer before inTransfer due to 
    // outTransfer checks for sufficient funds
    public void inTransfer(Double transfer) {
        accountBalance = accountBalance + transfer;
    }
    
    // Transfer out of account - Checks for sufficient funds and throws 
    // InsufficientFunds exception if funds are insufficient
    public void outTransfer(Double transfer) throws InsufficientFunds {
        if (transfer <= accountBalance) {
            accountBalance = accountBalance - transfer;
        } else {
            throw new InsufficientFunds();
        }
    }
    
    // Override toString
    @Override
    public String toString() {
        // Formatting double to default currency format on local JVM
        NumberFormat doubleFormat = NumberFormat.getCurrencyInstance();
        return "The current balance in your " + accountType + " account is: "
                + doubleFormat.format(accountBalance) + ".";
    }
}
