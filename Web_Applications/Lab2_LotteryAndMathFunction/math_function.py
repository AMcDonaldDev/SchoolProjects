# SDEV 300 7983 Lab 2
# File: math_function.py
# Author: Allison McDonald
# Date: November 2, 2019
# File produces x,y values using Python math functions.

import sys
import math
import random

# Generate random x between -2pi to 2pi and calculate sin(x)
# Using print() function without parameters to insert a blank line
print("Randomly generated x; y = sin(x)")
ran_number = random.uniform(-128.0, 128.1)
increment = math.pi / 64
x = ran_number * increment
y = math.sin(x)
print(f"x = {x:.2f}")
print(f"y = sin(x) = {y:.2f}")
print()

# Generate random x between -2pi to 2pi and calculate cos(x)
# Using print() function without parameters to insert a blank line
print("Randomly generated x; y = cos(x)")
ran_number = random.uniform(-128.0, 128.1)
increment = math.pi / 64
x = ran_number * increment
y = math.cos(x)
print(f"x = {x:.2f}")
print(f"y = cos(x) = {y:.2f}")
print()

# Generate random x between 0 to 200 and calculate sqrt(x)
# Using print() function without parameters to insert a blank line
print("Randomly generated x; y = sqrt(x)")
ran_number = random.randrange(1, 400)
increment = 0.5
x = ran_number * increment
y = math.sqrt(x)
print(f"x = {x:.1f}")
print(f"y = sqrt(x) = {y:.1f}")
print()

# Generate random x between 0 to 200 and calculate log10(x)
# Using print() funciton without parameters to insert a blank line
print("Randomly generated x; y = log10(x)")
ran_number = random.randrange(1, 400)
increment = 0.5
x = ran_number * increment
y = math.log10(x)
print(f"x = {x:.1f}")
print(f"y = log10(x) = {y:.1f}")
print()