// Compiler Theory and Design
// Dr. Duane J. Jarc

/* Name: Allison McDonald
   CMSC 430 Project 4
   Date: 12/14/2020
   Code Description: Compilation Listing
   Code was taken from Project 1, changes made for Project 4 were to
   include the error categories of DUPLICATE_IDENTIFIER and UNDECLARED in the
   Semantic Errors total.
   This file contains the bodies of the functions that produces the compilation listing */

#include <cstdio>
#include <string>
#include <queue>

using namespace std;

#include "listing.h"

static int lineNumber;
static string error = "";
static int totalAllErrors = 0;
static int totalLexicalErrors = 0;
static int totalSyntaxErrors = 0;
static int totalSemanticErrors = 0;
queue<string> lineErrorMessages;

static void displayErrors();

void firstLine()
{
	lineNumber = 1;
	printf("\n%4d  ",lineNumber);
}

void nextLine()
{
	displayErrors();
	lineNumber++;
	printf("%4d  ",lineNumber);
}

int lastLine()
{
	printf("\r");
	displayErrors();
	printf("     \n\n");
	if (totalAllErrors == 0) {
		printf("Compiled Successfully \n");
	}
	else {
		printf("Lexical Errors %d \n", totalLexicalErrors);
		printf("Syntax Errors %d \n", totalSyntaxErrors);
		printf("Semantic Errors %d \n", totalSemanticErrors);
		printf("Total Errors %d \n", totalAllErrors);
	}
	printf("\n");
	return totalAllErrors;
}
    
void appendError(ErrorCategories errorCategory, string message)
{
	string messages[] = { "Lexical Error, Invalid Character ", "",
		"Semantic Error, ", "Semantic Error, Duplicate Identifier: ",
		"Semantic Error, Undeclared " };

	error = messages[errorCategory] + message;
	lineErrorMessages.push(error);
	if (errorCategory == LEXICAL) {
		totalLexicalErrors++;
	}
	if (errorCategory == SYNTAX) {
		totalSyntaxErrors++;
	}
	if (errorCategory == GENERAL_SEMANTIC) {
		totalSemanticErrors++;
	}
	if (errorCategory == DUPLICATE_IDENTIFIER) {
		totalSemanticErrors++;
	}
	if (errorCategory == UNDECLARED) {
		totalSemanticErrors++;
	}
	totalAllErrors++;
}

void displayErrors()
{
	while (!lineErrorMessages.empty()) {
		printf("%s \n", lineErrorMessages.front().c_str());
		lineErrorMessages.pop();
	}
}
