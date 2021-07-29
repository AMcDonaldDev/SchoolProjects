# SDEV 300 7983 Lab 3
# File: state_capital_bird.py
# Author: Allison McDonald
# Date: November 14, 2019
# File allows the user to display, sort, and update as needed a list of 
# US States containing the state capital and state bird.

import sys

# Custom function to print title
def title():
    print("State\t\tCapital\t\tBird")

# Creating two-dimensional list
state_list = [
             ["Alabama", "Montgomery", "Yellowhammer"],
             ["Alaska", "Juneau", "Willow Ptarmigan"],
             ["Arizona", "Phoenix", "Cactus Wren"],
             ["Arkansas", "Little Rock", "Mockingbird"],
             ["California", "Sacramento", "California Quail"],
             ["Colorado", "Denver", "Lark Bunting"],
             ["Connecticut", "Hartford", "American Robin"],
             ["Delware", "Dover", "Blue Hen Chicken"],
             ["District of Columbia", "N/A", "Wood Thrush"],
             ["Florida", "Tallahassee", "Mockingbird"],
             ["Georgia", "Atlanta", "Brown Thrasher"],
             ["Hawaii", "Honolulu", "Nene"],
             ["Idaho", "Boise", "Mountain Bluebird"],
             ["Illinois", "Springfield", "Cardinal"],
             ["Indiana", "Indianapolis", "Cardinal"],
             ["Iowa", "Des Moines", "American Goldfinch"],
             ["Kansas", "Topeka", "Western Meadowlark"],
             ["Kentucky", "Frankfort", "Cardinal"],
             ["Louisiana", "Baton Rouge", "Brown Pelican"],
             ["Maine", "Augusta", "Black-caped Chickadee"],
             ["Maryland", "Annapolis", "Baltimore Oriole"],
             ["Massachusetts", "Boston", "Black-caped Chickadee"],
             ["Michigan", "Lansing", "American Robin"],
             ["Minnesota", "St. Paul", "Common Loon"],
             ["Mississippi", "Jackson", "Mockingbird"],
             ["Missouri", "Jefferson City", "Eastern Bluebird"],
             ["Montana", "Helena", "Western Meadowlark"],
             ["Nebraska", "Lincoln", "Western Meadowlark"],
             ["Nevada", "Carson City", "Mountain Bluebird"],
             ["New Hampshire", "Concord", "Purple Finch"],
             ["New Jersey", "Trenton", "American Goldfinch"],
             ["New Mexio", "Santa Fe", "Roadrunner"],
             ["New York", "Albany", "Eastern Bluebird"],
             ["North Carolina", "Raleigh", "Cardinal"],
             ["North Dakota", "Bismarck", "Western Meadowlark"],
             ["Ohio", "Columbus", "Cardinal"],
             ["Oklahoma", "Oklahoma City", "Sissor-tailed Flycatcher"],
             ["Oregon", "Salem", "Western Meadowlark"],
             ["Pennsylvania", "Harrisburg", "Ruffed Grouse"],
             ["Rhode Island", "Providence", "Rhode Island Red"],
             ["South Carolina", "Columbia", "Carolina Wren"],
             ["South Dakota", "Pierre", "Ring-necked Pheasant"],
             ["Tennessee", "Nashville", "Mockingbird"],
             ["Texas", "Austin", "Mockingbird"],
             ["Utah", "Salt Lake City", "California Gull"],
             ["Vermont", "Montpelier", "Hermit Thrush"],
             ["Virginia", "Richmond", "Cardinal"],
             ["Washington", "Olympia", "American Goldfinch"],
             ["West Virginia", "Charleston", "Cardinal"],
             ["Wisconsin", "Madison", "American Robin"],
             ["Wyoming", "Cheyenne", "Western Meadowlark"]
             ]

# Printing introduction and instructions
# Initializing selection variable.
# Reading and assigning user input.
# Using print() function without parameters to insert a blank line.
print("Let's learn or update the capital and bird for a US State!")
print()
action = 0
while action != 3:
    # Create state list
    states = []
    for row in range(len(state_list)):
        for column in range(len(state_list[row][0][0])):
            states.append(state_list[row][column])
            
    # User menu
    print("Please select from the menu below: \
    \n \
    \n\t1 - Learn \
    \n\t2 - Update \
    \n\t3 = Exit")
    print()
    action = int(input("Enter your selection: "))
    
    # Using if...elif...else statement to determine next operation
    if action == 1:
        lookup_state = input("To see the information for every state, enter 'All'." +
        "  To see the information for one state, enter the state's name.  ")
        print()
        # Using if...elif...else statement to print state info
        if lookup_state == "All":
            title()
            for row in state_list:
                for item in row:
                    print(item, end="\t\t")
                print()
            print()
        else:
            if lookup_state in states:
                state_index = states.index(lookup_state)
                title()
                print(state_list[state_index][0], "\t", state_list[state_index][1],
                "\t", state_list[state_index][2])
                print()
            else:
                print("State not found.")
    elif action == 2:
        print()
        update_state = input("Enter the name of the state. ")
        update_bird = input("Enter the updated bird name. ")
        if update_state in states:
            state_index = states.index(update_state)
            state_list[state_index][2] = update_bird
            print()
            print("Updated: ")
            title()
            print(state_list[state_index][0], "\t", state_list[state_index][1],
                "\t", state_list[state_index][2])
            print()
        else:
            print("State not found.")
            print()
    elif action == 3:
        print()
        print("You have selected to exit the program.")
    else:
        print()
        print("Invalid selection entered")
        print()
        
    # Clear states list
    states.clear()
print()
print("Goodbye")