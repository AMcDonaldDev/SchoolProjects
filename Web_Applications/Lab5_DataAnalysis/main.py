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
import data_analysis as da

da.main()