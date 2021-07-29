// CMSC 430
// Duane J. Jarc

/* Name: Allison McDonald
   CMSC 430 Project 3
   Date: 12/4/2020
   Code Description: Evaluation functions definitions
   This file contains the functions definitions to evaluate reduction, 
   relational, arithmetic, and logical operators */

// This file contains function definitions for the evaluation functions

typedef char* CharPtr;
enum Operators {LESS, ADD, MULTIPLY, SUBTRACT, DIVIDE, EXPONENT, MODULO,
	EQUAL, NOT_EQUAL, GREATER, GREATER_EQUAL, LESS_EQUAL, OR, AND, NOT};

double evaluateReduction(Operators operator_, double head, double tail);
double evaluateRelational(double left, Operators operator_, double right);
double evaluateArithmetic(double left, Operators operator_, double right);
double evaluateLogical(double left, Operators operator_, double right);
