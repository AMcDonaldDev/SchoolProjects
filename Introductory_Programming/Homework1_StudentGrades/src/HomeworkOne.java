/*
* File: HomeworkOne.java
* Author: Allison McDonald
* Date: January 27, 2019
* Purpose: Class CMIS 141 Homework 1 assignment
*/

// Import each required Java class
import java.util.Scanner;

public class HomeworkOne {	
    public static void main(String[] args) { 
	// Variables to hold values
	int studentEmplId = 0;
	float quizOne = 0.0f;
	float quizTwo = 0.0f;
        float quizThree = 0.0f;
        float ageMonths = 0.0f;
        float tempCelsius = 0.0f;
		
      	// Use the Scanner class to input data
        Scanner scannerIn = new Scanner(System.in);

	// Prompt the user to enter their Student EMPL ID
	System.out.println("Please enter your Student EMPL ID:");	
        studentEmplId = scannerIn.nextInt();

	// Prompt the user to enter the percentage score of their Quiz 1
	System.out.println("Please enter your percentage score for Quiz 1:");	
        quizOne = scannerIn.nextFloat();

	// Prompt the user to enter the percentage score of their Quiz 2
	System.out.println("Please enter your percentage score for Quiz 2:");	
        quizTwo = scannerIn.nextFloat();
        
        // Prompt the user ot enter the percentage score of their Quiz 3
        System.out.println("Please enter your percentage score for Quiz 3:");
        quizThree = scannerIn.nextFloat();

	// Prompt the user to enter their age in months
	System.out.println("Please enter your age in months:");	
        ageMonths = scannerIn.nextFloat();

	// Prompt the user to enter the current temperature in Celsius degrees
	System.out.println("Please enter the current temperature in degrees Celsius:");	
        tempCelsius = scannerIn.nextFloat();

	// Calculate the user's average quiz score
        float quizAverage = (quizOne + quizTwo + quizThree)/3;
        
        // Calculate the user's age in years
        float ageYears = ageMonths/12;
        
        // Convert degrees Celsius to Fahrenheit
        float tempFahrenheit = (tempCelsius * 9) / 5 + 32;

	// Display all results
	System.out.println("Your Student Empl ID is: " + studentEmplId);
	System.out.println("Your Quiz 1 percentage score is: " + quizOne);
	System.out.println("Your Quiz 2 percentage score is: " + quizTwo);
	System.out.println("Your Quiz 3 percentage score is: " + quizThree);
        System.out.println("Your average quiz percentage score is: " + quizAverage);
	System.out.println("Your age in months is: " + ageMonths);
        System.out.println("Your age in years is: " + ageYears);
	System.out.println("The current temperature in Celsius is: " + tempCelsius);
        System.out.println("The current temperature in Fahrenheit is: " + tempFahrenheit);
	
    }
}
