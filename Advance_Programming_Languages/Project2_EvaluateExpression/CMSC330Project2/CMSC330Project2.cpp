// CMSC330Project2.cpp : This file contains the 'main' function. Program execution begins and ends there.
// This code is modified from Module 3 file module3.cpp
// Author: Allison McDonald
// Date: 7/14/2020

#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
using namespace std;

#include "expression.h"
#include "subexpression.h"
#include "symboltable.h"
#include "parse.h"

SymbolTable symbolTable;

void parseAssignments(stringstream& stream);

int main()
{
	Expression* expression;
	char paren, comma;
	char text[200];
	fstream file;
	file.open("test_cases.txt", ios::in);
	if (!file)
	{
		cout << "File not found." << endl;
		return 0;
	}
	
	while (!file.eof())
	{
		symbolTable.clear();
		file.getline(text, sizeof(text));
		stringstream stream(text, ios::in);
		//cout << "Enter expression: ";
		stream >> paren;
		expression = SubExpression::parse(stream);
		stream >> comma;
		parseAssignments(stream);
		cout << "Value = " << expression->evaluate() << endl;
	}
	file.close();
	return 0;
}

void parseAssignments(stringstream& stream)
{
	char assignop, delimiter;
	string variable;
	double value;
	do
	{
		variable = parseName(stream);
		stream >> ws >> assignop >> value >> delimiter;
		symbolTable.insert(variable, value);
	} 
	while (delimiter == ',');
}
