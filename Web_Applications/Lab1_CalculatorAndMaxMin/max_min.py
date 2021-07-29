# SDEV 300 7983 Lab 1
# File: max_min.py
# Author: Allison McDonald
# Date: October 26, 2019
# File calculates the minimum and maximum of five numbers inputted by a user.

import sys

# Printing introduction and instructions.
# Using print() function without parameters to insert a blank line.
print("Let's determine the minimum and maximum of five integers! \
\nAfter each prompt for an integer, press Enter.")
print()

# Reading and assigning user inputs.
number1 = int(input("Enter first integer: "))
number2 = int(input("Enter second integer: "))
number3 = int(input("Enter third integer: "))
number4 = int(input("Enter forth integer: "))
number5 = int(input("Enter fifth integer: "))

# Printing minimum and maximum values.
# Using print() function without parameters to insert a blank line.
print()
print("Minimum value entered:", min(number1, number2, number3, number4, number5))
print("Maximum value entered:", max(number1, number2, number3, number4, number5))