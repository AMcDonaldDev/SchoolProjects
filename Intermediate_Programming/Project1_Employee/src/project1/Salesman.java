/*
 * File: Salesman.java
 * Author: Allison McDonald
 * Date: March 30, 2019
 * Purpose:
 */
package project1;

import java.text.NumberFormat;

public class Salesman extends Employee {
    // Instance variables
    private int annualSales;
    
    // Constructor
    public Salesman(String name, int monthlySalary, int annualSales) {
        super(name, monthlySalary);
        this.annualSales = annualSales;
    }
    
    // Calculate annual salary (monthly salary*12 + commission up to $20,000
    @Override
    public int calculateAnnualSalary() {
        int commission = (int) (annualSales*0.02);
        if(commission <= 20000) {
            return super.calculateAnnualSalary() + commission;
        }
        return super.calculateAnnualSalary() + 20000;
    }
    
    // Override toString
    @Override
    public String toString() {
        NumberFormat intFormat = NumberFormat.getIntegerInstance();
        intFormat.setGroupingUsed(true);
        return super.toString() + "\n  Annual Sales - $" + intFormat.format(annualSales);
    }
    
}
