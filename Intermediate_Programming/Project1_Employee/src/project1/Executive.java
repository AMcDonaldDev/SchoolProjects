/*
 * File: Executive.java
 * Author: Allison McDonald
 * Date: March 30, 2019
 * Purpose:
 */
package project1;

import java.text.NumberFormat;

public class Executive extends Employee {
    // Instance variable
    private int stockPrice;
    
    // Constructor
    public Executive(String name, int monthlySalary, int stockPrice) {
        super(name, monthlySalary);
        this.stockPrice = stockPrice;
    }
    
    // Calculate annual salary (monthly salary*12 + bonus of $30,000 if stock > $50
    @Override
    public int calculateAnnualSalary() {
        if(stockPrice > 50) {
            return super.calculateAnnualSalary() + 30000;
        }
        return super.calculateAnnualSalary();
    }
    
    // Override toString
    @Override
    public String toString() {
        NumberFormat intFormat = NumberFormat.getIntegerInstance();
        intFormat.setGroupingUsed(true);
        return super.toString() + "\n  Current Stock Price - $"
                + intFormat.format(stockPrice);
    }
    
}
