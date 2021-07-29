# SDEV 300 7983 Lab 3
# File: math_set.py
# Author: Allison McDonald
# Date: November 14, 2019
# File determines the union, intersection and difference of the square and
# cube of integers ranging from 1 to 100.

import sys
import math

# Custom function to create sets and print squared, cubed, union,
# intersection, and difference of sets
def create_sets(start, stop):
    numbers = set(range(start, stop))
    squared = {item ** 2 for item in numbers}
    cubed = {item ** 3 for item in numbers}
    union = squared | cubed
    intersection = squared & cubed
    difference = squared - cubed
    print("Square: \n", sorted(squared))
    print()
    print("Cube: \n", sorted(cubed))
    print()
    print("Union: \n", sorted(union))
    print()
    print("Intersection: \n", sorted(intersection))
    print()
    print("Difference: \n", sorted(difference))

# Printing introduction and instructions
# Initializing selection variable.
# Reading and assigning user input.
# Using print() function without parameters to insert a blank line.
print("Let's view the Square and Cube for integers ranging from 1 to 100.")
print()
action = 0
while action != 3:
    # User menu
    print()
    print("Please select from the menu below: \
    \n \
    \n\t1 - View Square and Cube for all integers from 1 to 100 \
    \n\t2 - View Square and Cube for a specific integer from 1 to 100 \
    \n\t3 - Exit")
    print()
    action = int(input("Enter your selection: "))
    
    # Using if...else statement to determine next operation
    if action == 1:
        print()
        create_sets(1, 101)
        print()
    elif action == 2:
        print()
        start = int(input("Enter an integer: "))
        if start >= 1 and start <= 100:
            stop = start + 1
            print()
            create_sets(start, stop)
            print()
        else:
            print()
            print("You have entered an invalid integer.")
    elif action == 3:
        print()
        print("You have selected to end the program.")
    else:
        print()
        print("Invalid selection entered.")
        print()
print()
print("Goodbye")