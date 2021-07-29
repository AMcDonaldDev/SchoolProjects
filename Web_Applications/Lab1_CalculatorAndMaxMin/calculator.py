# SDEV 300 7983 Lab 1
# File: calculator.py
# Author: Allison McDonald
# Date: October 26, 2019
# File prompts a user to calculate the sum, difference, modulus, product, 
# or quotient of two integers inputted by a user.

import sys

# Printing introduction and instructions.
# Reading and assigning user input of selected arithmetic operators
# Using print() function without parameters to insert a blank line.
print("Let's perform a calcuation on two integers! \
\nList of available arithmetic operators to select: \
\n\t1 - Addition(+) \
\n\t2 - Subtraction(-) \
\n\t3 - Multiplication(*) \
\n\t4 - Division(/) \
\n\t5 - Modulus(%)")
selection = int(input("Enter the number that corresponds to your selection: "))
print()

# Using the if statement to print selected arithmetic operator
# Using print() function without parameters to insert a blank line.
if selection == 1:
    print("You have selected Addition(+).")
if selection == 2:
    print("You have selected Subtraction(-).")
if selection == 3:
    print("You have selected Multiplication(*).")
if selection == 4:
    print("You have selected Division(/).")
if selection == 5:
    print("You have selected Modulus(%).")
print()

# Reading and assigning user input of two integers.
number1 = int(input("Enter first integer: "))
number2 = int(input("Enter second integer: "))

# Using the if statement to print and calculate values.
# Using print() function without parameters to insert a blank line.
print()
print("Your calculation: ")
if selection == 1:
    calculation = number1 + number2
    print(number1, " + ", number2, " = ", calculation)
if selection == 2:
    calculation = number1 - number2
    print(number1, " - ", number2, " = ", calculation)
if selection == 3:
    calculation = number1 * number2
    print(number1, " * ", number2, " = ", calculation)
if selection == 4:
    calculation = number1 / number2
    print(number1, " / ", number2, " = ", calculation)
if selection == 5:
    calculation = number1 % number2
    print(number1, " % ", number2, " = ", calculation)