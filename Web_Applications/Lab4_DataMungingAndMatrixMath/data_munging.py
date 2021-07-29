# SDEV 300 7983 Lab 4
# File: data_munging.py
# Author: Allison McDonald
# Date: November 17, 2019
# File appropriately formats existing address records and eliminate records
# with missing critical fields.

import sys
import pandas as pd
import re

# Customfunction to format first and last name
def format_name(name):
    result = re.fullmatch(r'([a-zA-Z][a-z]+)', name)
    return ''.join(result.groups()).title() if result else ""

# Custom function to format zip code
def format_zip(zip):
    result = re.fullmatch(r'(\d{5})(|-\d{4})', zip)
    return ''.join(result.groups()) if result else ""

# Custom function to format phone number
def format_phone(phone):
    result = re.fullmatch(r'(\d{3})(\d{3})(\d{4})', phone)
    return '-'.join(result.groups()) if result else ""

# Create array
contacts = [["keith", "Dublin", "28078", "704555189"],
            ["Allison", "mcdonald", "2973", "8035550147"],
            ["patrick", "Brown", "90210-1245", "83055563"],
            ["Penny", "Cann", "29745", "9195554780"],
            ["Faye", "Preston", "118", "6515556235"],
            ["crystal", "johnson", "89745", "68455522"],
            ["Louis", "Jackson", "66589", "41755"],
            ["Julian", "lankford", "78421", "5645551865"],
            ["Andy", "Long", "1124", "3215558914"],
            ["katie", "King", "89654", "1405558746"],
            ["john", "smith", "a8754", "22255589741"],
            ["ken", "Miles", "7000", "2125559635"],
            ["Henry", "ford", "54879", "2125559854"],
            ["Carroll", "shelby", "70000", "2125557841"],
            ["enzo", "ferrari", "875", "31555587"],
            ["a", "Ball", "89745", "6255556658"],
            ["Robert", "frost", "4785554182", "67854"],
            ["bebe", "Lark", "8", "47855568"],
            ["barbara", "Little", "54897", "4125554563"],
            ["bill", "carver", "28745", "5685552134"],
            ["Daniel", "Frank", "14895", "6515558942"],
            ["James", "Sanders", "781", "919555813"],
            ["Matt", "keller", "44444", "2305559520"],
            ["christian", "Thomas", "7897", "874551423"],
            ["Michael", "freeman", "56587", "2805553271"]]
            
# Create dataframe
contactsdf = pd.DataFrame(contacts, 
                         columns=["First Name", "Last Name", "Zip Code", "Phone"])

# Print data before munging and formatting
print("Contact list before reformatting")
print()
print(contactsdf)

# Mung data
formatted_first = contactsdf["First Name"].map(format_name)
contactsdf["First Name"] = formatted_first
formatted_last = contactsdf["Last Name"].map(format_name)
contactsdf["Last Name"] = formatted_last
formatted_zip = contactsdf["Zip Code"].map(format_zip)
contactsdf["Zip Code"] = formatted_zip
formatted_phone = contactsdf["Phone"].map(format_phone)
contactsdf["Phone"] = formatted_phone

# Print data after nunging formatting
print()
print("Contact list after reformatting")
print()
print(contactsdf)