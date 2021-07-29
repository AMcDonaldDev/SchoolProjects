# SDEV 300 7983 Lab 4
# File: matrix_math.py
# Author: Allison McDonald
# Date: November 17, 2019
# File allows a user to enter the values of two, 3x3 matrices and then
# select from options including addition, subtraction, matrix multiplication,
# and element by element multiplication.

import sys
import numpy as np

# Custom function to create number list
def create_list():
    numbers = []
    number_counter = 1
    while number_counter == 1 or number_counter <=9:
        number = input("Enter number: ")
        if number.isnumeric():
            numbers.append(int(number))
            number_counter += 1
        else:
            print("Invalid number entered.")
            print()
    return numbers

# Custom function to create an array
def create_array(numbers):
    matrix = np.array(numbers)
    return matrix.reshape(3, 3)
    
# Custom function to receive user operator input
def operator_input():
    operation = 0
    while operation != 5:
        print("Which operation would you like to perform: \
        \n \
        \n\t1 - Addition \
        \n\t2 - Subtraction \
        \n\t3 - Matrix Multiplication \
        \n\t4 - Element by Element Multiplication")
        print()
        operation = int(input("Enter your selection: "))
        print()
        if operation == 1:
            print("Your addition results are: ")
            break
        elif operation == 2:
            print("Your subtraction results are: ")
            break
        elif operation == 3:
            print("Your matrix multiplication results are: ")
            break
        elif operation == 4:
            print("Your element by element multiplication results are: ")
            break
        else:
            print("Invalid selection entered.")
            operation = 5
    return operation
        
# Custom function to calculate a results array
def calculate_result(selection, first_array, second_array):
    if selection == 1:
        return np.add(first_array, second_array)
    elif selection == 2:
        return np.subtract(first_array, second_array)
    elif selection == 3:
        return np.matmul(first_array, second_array)
    else:
        return np.multiply(first_array, second_array)

# Custom function to print results
def print_result(result_array):
    print("Results:\n", result_array)
    print()
    print("Transpose Results: \n", result_array.T)
    print()
    print("Row Mean Results: \n", result_array.mean(axis=0))
    print()
    print("Column Mean Results: \n", result_array.mean(axis=1))
    print()

# Printing introduction
# Initializing action variable.
# Reading and assigning user input.
# Using print() function without parameters to insert a blank line.
print("Let's have fun with matrices!")
print()
action = 0
while action != 2:
    action = int(input("To Start enter 1, to Exit enter 2: "))
    if action == 1:
        print()
        print("Create your first 3x3 matrix by entering nine numbers.")
        numbers_1 = create_list()
        first_matrix = create_array(numbers_1)
        print()
        print("Create your second 3x3 matrix by entering nine numbers.")
        numbers_2 = create_list()
        second_matrix = create_array(numbers_2)
        print()
        selection = operator_input()
        print_result(calculate_result(selection, first_matrix, second_matrix))
    elif action == 2:
        print()
        print("You have chosen to exit the program.")
        print("Goodbye.")
        print()
    else:
        print()
        print("Invalid input.")
        print()
