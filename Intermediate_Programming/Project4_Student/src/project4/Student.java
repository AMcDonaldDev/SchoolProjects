/*
 * File: Student.java
 * Author: Allison McDonald
 * Date: May 11, 2019
 * Purpose: CMIS 242 7983 Project 4 - a program to manage a student database - 
 * this is the Student class that defines the student record
 */
package project4;

public class Student {
    // Instance variables
    private String studentName;
    private String studentMajor;
    private double totalCreditHours;
    private double totalQualityPoints;
    
    // Constructor
    public Student(String studentName, String studentMajor) {
        this.studentName = studentName;
        this.studentMajor = studentMajor;
        totalQualityPoints = 0.0;
        totalCreditHours = 0.0;
    }
    
    // Updates variables totalQualityPoints and totalCreditHours which are used to compute the student's GPA
    public void courseCompleted(String courseGrade, double courseCreditHours) {
        double gradeNumericValue = 0.0;
        double currentCreditHours = totalCreditHours;
        double currentQualityPoints = totalQualityPoints;
        switch (courseGrade) {
            case "A":
                gradeNumericValue = 4.0;
                break;
            case "B":
                gradeNumericValue = 3.0;
                break;
            case "C":
                gradeNumericValue = 2.0;
                break;
            case "D":
                gradeNumericValue = 1.0;
                break;
            case "F":
                gradeNumericValue = 0.0;
                break;
        }
        totalCreditHours = currentCreditHours + courseCreditHours;
        totalQualityPoints = currentQualityPoints + (gradeNumericValue * courseCreditHours);
    }
    
    // Override toString
    @Override
    public String toString() {
        double studentGPA;
        if (totalCreditHours == 0.0) {
            studentGPA = 4.0;
        } else {
            studentGPA = totalQualityPoints / totalCreditHours;
        }
        return "Name: " + studentName + "\nMajor: " + studentMajor + "\nGPA: "
                + studentGPA;
    }
    
}
