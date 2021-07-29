# SDEV 300 7983 Lab 7
# File: adopt_enhanced.py
# Author: Allison McDonald
# Date: December 8, 2019
# File generates an enhanced simple Web page using Python flask

from flask import Flask
from form import SurveyForm
import datetime

app = Flask(__name__)
app.config['SECRET_KEY'] = 'thekey'

@app.route('/adopt_enhanced')

def welcome():
    myReply = pop_start();
    myReply += "<h1>Welcome to Pet Adoption 101<img src=''/static/Cat.jpg''></h1>"
    myReply += pop_image("/static/Dog2.jpg")
    myReply += pop_image("/static/Cat.jpg")
    myReply += pop_image("/static/Dog1.jpg")
    myReply += pop_image("/static/Cat2.jpg")
    myReply += "<p>Millions of animals are taken into sheters every year.\
    Below is the number of intakes for dogs and cats for the past three years.\
    Luckily many of these animals are adopted into loving homes.  Will you\
    be one of these loving homes?</p>"
    myReply += pop_table()
    myReply += pop_header("Are you ready?")
    myReply += pop_ready_list()
    myReply += pop_header("Which type of pet?")
    myReply += pop_type_list()
    myReply += pop_header("Where to find?")
    myReply += pop_where_list()
    myReply += pop_header("Where to shop?")
    myReply += pop_shop_list()
    myReply += pop_header("Adoption Survey:")
    myReply += pop_survey_form()
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
    
def pop_table():
    table_data = "<table border=''1''>"
    table_data += "<caption>2016-2018 Number of Intakes</caption>"
    table_data += "<thead><tr><th>Year</th><th>Dog</th><th>Cat</th><th>Total</th></tr></thead>"
    table_data += "<tbody><tr><td>2018</td><td>1,554,593</td><td>1,493,813</td><td>3,048,406</td></tr>"
    table_data += "<tr><td>2017</td><td>1,508,165</td><td>1,443,158</td><td>2,951,323</td></tr>"
    table_data += "<tr><td>2016</td><td>1,391,974</td><td>1,312,095</td><td>2,704,069</td></tr></tbody>"
    table_data += "</table>"
    return table_data
    
def pop_survey_form():
    form = SurveyForm()
    survey = "<form method=''post''>"
    survey += "<p>" + form.name.label() + "<br>" + form.name(size=30) + "</p>"
    survey += "<p>" + form.decision.label() + "<br>" + form.decision(choices=[('Yes','Yes'), ('No','No')]) + "</p>"
    survey += "<p>" + form.submit() + "</p>"
    survey += "<p><input type='reset'></p>"
    survey += "</form>"
    return survey

def pop_image(file):
    picture = "<img src=" + file + ">"
    return picture
    
def pop_end():
    end_data = "</body>"
    end_data += "</html>"
    return end_data
    
app.run(host='0.0.0.0', port= 8080)