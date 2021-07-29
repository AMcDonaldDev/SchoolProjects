# SDEV 300 7983 Lab 5
# File: data_analysis.py
# Author: Allison McDonald
# Date: November 24, 2019
# File allows a user to load one of two CSV files and then perform histogram
# analysis and plots for select variables on the datasets.

import sys
import csv
import pandas as pd
from io import StringIO
import os
import parser
import io
import numpy as np
import matplotlib.pyplot as plt

def main():
# Introduction and instructions.
# Initializing action variable.
# Reading and assigning user input.
# Using print() function without parameters to insert a blank line.
    print("Let's perform some Data Analysis!")
    print()
    file_action = 0
    while file_action != 3:
        print("Which data would you like to analyze? \
        \n \
        \n\t1 - Population Data \
        \n\t2 - Housing Data \
        \n\t3 - Exit Program")
        print()
        file_action = int(input("Enter your selection: "))
        print()
        if file_action == 1:
            print("You have selected to analyze Population Data.")
            # Instruction to select column.
            # Initializing column action variable.
            pop_action = 0
            while pop_action != 4:
                print()
                print("Which column of data would you like to analyze? \
                \n \
                \n\t1 - Population April 1st \
                \n\t2 - Population July 1st \
                \n\t3 - Change of Population \
                \n\t4 - Exit Column Selection")
                pop_action = int(input("Enter your selection: "))
                if pop_action == 1:
                    print("You have selected: Population April 1st.")
                    print()
                    print_pop("Pop Apr 1")
                elif pop_action == 2:
                    print("You have selected: Population July 1st.")
                    print()
                    print_pop("Pop Jul 1")
                elif pop_action == 3:
                    print("You have selected: Change of Population.")
                    print()
                    print_pop("Change Population")
                elif pop_action == 4:
                    print("You have selected: Exit Column Selection.")
                    print()
                else:
                    print("Invalid selection entered.")
                    print()
        
        elif file_action == 2:
            print("You have selected to analyse Housing Data.")
            # Instuction to select column.
            # Initializing column action variable
            house_action = 0
            while house_action != 6:
                print()
                print("Which column of data would you like to analyz? \
                \n \
                \n\t1 - Age of House \
                \n\t2 - Number of Bedrooms \
                \n\t3 - Year Built \
                \n\t4 - Number of Rooms \
                \n\t5 - Utility \
                \n\t6 - Exit Column Selection")
                house_action = int(input("Enter your selection: "))
                if house_action == 1:
                    print("You have selected: Age of House.")
                    print()
                    print_house("Age")
                elif house_action == 2:
                    print("You have selected: Number of Bedrooms.")
                    print()
                    print_house("Bedrooms")
                elif house_action == 3:
                    print("You have selected: Year Built.")
                    print()
                    print_house("Built")
                elif house_action == 4:
                    print("You have selected: Number of Rooms.")
                    print()
                    print_house("# Rooms")
                elif house_action == 5:
                    print("You have selected: Utility.")
                    print()
                    print_house("Utility")
                elif house_action == 6:
                    print("You have selected: Exit Column Selection.")
                    print()
                else:
                    print("Invalid selection entered.")
                    print()
        
        elif file_action == 3:
            print("You have selected to end the program.")
        
        else:
            print("Invalid selection entered.")
            print()

def population():
    popdf = pd.read_csv("/home/ec2-user/environment/PopChange.csv") 
    names=["Id", "Geography",
            "Target Geo Id 1", "Target Geo Id 2", "Pop Apr 1", "Pop Jul 1",
            "Change Population"]
    return popdf

def housing():
    housedf = pd.read_csv("/home/ec2-user/environment/Housing.csv") 
    names=["Age", "Bedrooms", "Built", "# Units", "# Rooms", "Weight",
            "Utility"]
    return housedf

def print_pop(field):
    popdf = population()
    #hist = histogram(popdf.loc[[field]])
    print("Count: ")
    print(popdf.loc[:,[field]].count())
    print("Mean: ")
    print(popdf.loc[:,[field]].mean())
    print("Standard Deviation: ")
    print(popdf.loc[:,[field]].std())
    print("Min: ")
    print(popdf.loc[:,[field]].min())
    print("Max: ")
    print(popdf.loc[:,[field]].max())
    print("The Histogram of this column can be downloaded now.")
    
def print_house(field):
    housedf = housing()
    #hist = histogram(housedf.loc[[field]])
    print("Count: ")
    print(housedf.loc[:,[field]].count())
    print("Mean: ")
    print(housedf.loc[:,[field]].mean())
    print("Standard Deviation: ")
    print(housedf.loc[:,[field]].std())
    print("Min: ")
    print(housedf.loc[:,[field]].min())
    print("Max: ")
    print(housedf.loc[:,[field]].max())
    print("The Histogram of this column can be downloaded now.")

def histogram(x):
    np.random.seed(214801)
    mu, sigma = 100, 15
    x = mu + sigma * np.random.randn(10000)
    n, bins, patches = plt.hist(x, 20, density=True, facescolor='b', alpha=0.75)
    plt.grid(True)
    fig1=plt
    return fig1.savefig('display1.svg')
