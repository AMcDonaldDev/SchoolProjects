/*
 * File: Employee.java
 * Author: Allison McDonald
 * Date: March 30, 2019
 * Purpose:
 */
package project1;

import java.text.NumberFormat;

public class Employee {
    // Instance variables
    private String name;
    private int monthlySalary;
    NumberFormat intFormat = NumberFormat.getIntegerInstance();
    
    // Constructor
    public Employee(String name, int monthlySalary) {
        this.name = name;
        this.monthlySalary = monthlySalary;
    }
    
    // Calculate annual salary (monthly salary multiplied by 12)
    public int calculateAnnualSalary() {
        return monthlySalary*12;
    }
    
    // Override toString
    @Override
    public String toString() {
        intFormat.setGroupingUsed(true);
        String className = this.getClass().getSimpleName();
        return " " + className + "\n  Name - " + name + "\n  Monthly Salary - $"
                + intFormat.format(monthlySalary);
    }
    
}
