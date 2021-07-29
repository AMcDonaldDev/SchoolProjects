// CMSC 430
// Dr. Duane J. Jarc

/* Name: Allison McDonald
   CMSC 430 Project 2
   Date: 11/30/2020
   Code Description: Compilation Listing Prototypes
   This file contains the function prototypes for the functions that produce the compilation listing */

enum ErrorCategories {LEXICAL, SYNTAX, GENERAL_SEMANTIC, DUPLICATE_IDENTIFIER,
	UNDECLARED};

void firstLine();
void nextLine();
int lastLine();
void appendError(ErrorCategories errorCategory, string message);

