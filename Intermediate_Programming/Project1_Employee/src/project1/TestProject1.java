/*
 * File: TestProject1.java
 * Author: Allison McDonald
 * Date: March 30, 2019
 * Purpose:
 */
package project1;

import java.text.NumberFormat;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;

public class TestProject1 {
    public static void main(String[] args) throws IOException {
        // Check for command line argument and exit if null
        if (args.length == 0) {
            System.out.println("Please enter file name as a command line argument.");
            System.exit(0);
        }
        ArrayList<Employee> employee2014 = new ArrayList<>();
        ArrayList<Employee> employee2015 = new ArrayList<>();

        BufferedReader inputStream = null;
        String fileLine;
        
        // Read file
        try {
            inputStream = new BufferedReader(new FileReader(args[0]));
            
            // Read one line using BufferedReader
            while ((fileLine = inputStream.readLine()) != null) {
                String[] lineData = fileLine.split(" ");
                Employee employee = null;

                // Figure out what type of object to make and make it
                if (lineData[1].equals("Employee")) {
                    employee = new Employee(lineData[2], Integer.parseInt(lineData[3]));
                } else if (lineData[1].equals("Salesman")) {
                    employee = new Salesman(lineData[2], Integer.parseInt(lineData[3]), Integer.parseInt(lineData[4]));
                } else {
                    employee = new Executive(lineData[2], Integer.parseInt(lineData[3]), Integer.parseInt(lineData[4]));
                }
                
                // Figure out which list to place object and place it
                if (lineData[0].equals("2014")) {
                    employee2014.add(employee);
                } else {
                    employee2015.add(employee);
                }
            }
        } catch (IOException io) {
            System.out.println("File IO exception" + io.getMessage());
        } finally {
            try {
                // Close the steams
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException io) {
                System.out.println("Issue closing the files " + io.getMessage());
            }
        }
        
        System.out.println("---2014 Employee Report---\n" + generateReport(employee2014));
        System.out.println("2014 Average Annual Salary: $" + calculateAvgAnnualSalary(employee2014)
                + "\n");
        System.out.println("---2015 Employee Report---\n" + generateReport(employee2015));
        System.out.println("2015 Average Annual Salary: $" + calculateAvgAnnualSalary(employee2015)
                + "\n");
        
    }
    
    // Method to return ArrayList report
    public static String generateReport(ArrayList<Employee> list) {
        NumberFormat intFormat = NumberFormat.getIntegerInstance();
        intFormat.setGroupingUsed(true);
        String report = "";
        for (Employee data : list) {
           report += data.toString() + "\n  Annual Salary - $" 
                   + intFormat.format(data.calculateAnnualSalary()) + "\n";
        }
        return report;
    }
    
    // Method to return average annual salaries
   public static String calculateAvgAnnualSalary(ArrayList<Employee> list) {
       NumberFormat intFormat = NumberFormat.getIntegerInstance();
       intFormat.setGroupingUsed(true);
       int totalSalary = 0;
       int averageSalary = 0;
       for (Employee data: list) {
           totalSalary += data.calculateAnnualSalary();
           averageSalary = totalSalary / list.size();
       }
       return intFormat.format(averageSalary);
   }
    
}
