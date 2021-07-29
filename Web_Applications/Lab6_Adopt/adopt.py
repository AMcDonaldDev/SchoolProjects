# SDEV 300 7983 Lab 6
# File: adopt.py
# Author: Allison McDonald
# Date: December 1, 2019
# File generates a simple Web page using Python flask

from flask import Flask
import datetime

app = Flask(__name__)

@app.route('/adopt')

def helloIndex():
    myReply = pop_start();
    myReply += "<h1>Welcome to Pet Adoption 101</h1>"
    myReply += pop_header("Are you ready?")
    myReply += pop_ready_list()
    myReply += pop_header("Which type of pet?")
    myReply += pop_type_list()
    myReply += pop_header("Where to find?")
    myReply += pop_where_list()
    myReply += pop_header("Where to shop?")
    myReply += pop_shop_list()
    myReply += pop_date_time()
    myReply += pop_end()
    return myReply

def pop_start():
    start_data = "<!DOCTYPE html>"
    start_data += "<head>"
    start_data += "<title>Pet Adoption</title>"
    start_data += "</head>"
    start_data += "<body>"
    return start_data

def pop_header(header):
    new_header = "<h2>" + header + "</h2>"
    return new_header

def pop_ready_list():
    ready_questions = "<p>Thinking about adopting an animal? If so, are you\
    ready? How do you know if you are ready? Below are questions to ask\
    yourself which can help you determine if you are truly ready</p>"
    ready_questions += "<ol>"
    ready_questions += "<li>Are you financial able to care for a pet?</li>"
    ready_questions += "<li>Are you physically able to care for a pet?</li>"
    ready_questions += "<li>Are you emotionally able to care for a pet?</li>"
    ready_questions += "<li>Will your lifestyle accommodate a pet?</li>"
    ready_questions += "</ol>"
    return ready_questions

def pop_type_list():
    type_questions = "<p>Once you know you are ready, now you need to determine\
    the type of pet you want.  Below are questions to ask yourself\
    which can help you determine the type of pet best for you.</p>"
    type_questions += "<ul>"
    type_questions += "<li>What type of pet will best suit your lifestyle?</li>"
    type_questions += "<li>How much time and engergy can you spend with your new pet?</li>"
    type_questions += "<li>What is the purpose of your new pet?</li>"
    type_questions += "<li>What temperament should your new pet have?</li>"
    type_questions += "<li>Are allergies a concern?</li>"
    type_questions += "</ul>"
    return type_questions

def pop_where_list():
    where_data = "<p>Now you know the type of pet you want, but where can you\
    find that pet?  Below are suggestions on where to start your search.</p>"
    where_data += "<ul>"
    where_data += "<li>Local Animal Control Shelter</li>"
    where_data += "<li>Local Humane Society</li>"
    where_data += "<li><a href=''https://petfinder.com/''>PetFinder.com</a></li>"
    where_data += "</ul>"
    return where_data
    
def pop_shop_list():
    shop_data = "<p>Congratulations on your new pet! Now it is time to shop\
    for your new addition to the family.  Below are suggestions on where\
    to shop</p>"
    shop_data += "<ul>"
    shop_data += "<li><a href=''https://www.petsmart.com/''>PetSmart</a></li>"
    shop_data += "<li><a href=''https://www.petco.com/''>PetCo</a></li>"
    shop_data += "<li><a href=''https://www.petsupermarket.com/''>Pet Supermarket</a></li>"
    shop_data += "</ul>"
    return shop_data

def pop_date_time():
    dt = datetime.datetime.now()
    date_time = "<p>" + dt.strftime("%B %d, %Y %l:%M %p") + "</p>"
    return date_time

def pop_end():
    end_data = "</body>"
    end_data += "</html>"
    return end_data
    
app.run(host='0.0.0.0', port= 8080)