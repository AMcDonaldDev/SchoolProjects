# SDEV 300 7983 Lab 2
# File: lottery.py
# Author: Allison McDonald
# Date: November 2, 2019
# File allows the user to use a menu to select and then generate pick-3 or
# pick-4 lottery number generator.

import sys
import random

# Custom function to pick and print lottery numbers.
def pick(number):
    print("Your numbers are: ", end=" ")
    for p in range(number):
        print(random.randrange(0, 10), end=" ")

# Printing introduction and instruction.
# Initializing selection variable.
# Reading and assigning user input of selected menu item.
# Using print() function without parameters to insert a blank line.
print("Thanks for choosing SDEV LOTO for your lottery number generating needs!")
print()
selection = 0
while selection != 3:
    print("Please select from the following menu: \
    \n \
    \n\t1 - Generate a Pick-3 Lottery Number \
    \n\t2 - Generate a Pick-4 Lottery Number \
    \n\t3 - Exit")
    print()
    selection = int(input("Enter your selection: "))
    
    # Using if...elif...else statement to print and calculate value.
    # Using print() function without parameters to insert a blank line.
    if selection == 1:
        print("You have selected to generate a Pick-3 Lottery Number.")
        pick(3)
        print("\n")
    elif selection == 2:
        print("You have selected to generate a Pick-4 Lottery Number.")
        pick(4)
        print("\n")
    elif selection == 3:
        print("You have selected to end the program.")
    else:
        print("Invalid selection entered.")
print()
print("Good Luck!")