# SDEV 300 7983 Lab 7
# File: form.py
# Author: Allison McDonald
# Date: December 8, 2019
# File generates an enhanced simple Web page using Python flask

from flask import Flask
from flask_wtf import FlaskForm
from wtforms import TextField
from wtforms import SelectField
from wtforms import SubmitField


class SurveyForm(FlaskForm):
    name = TextField("Name")
    decision = SelectField("Decided to Adopt?", choices=[('Yes','Yes'), ('No','No')])
    submit = SubmitField("Submit")

    
    
