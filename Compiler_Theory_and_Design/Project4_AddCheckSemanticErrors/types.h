// Compiler Theory and Design
// Duane J. Jarc

/* Name: Allison McDonald
   CMSC 430 Project 4
   Date: 12/14/2020
   Code Description: Type Checking Prototypes
   This file contains type definitions and the function prototypes for the type checking functions */

typedef char* CharPtr;

enum Types {MISMATCH, INT_TYPE, BOOL_TYPE, REAL_TYPE, INITIAL_TYPE};

void checkAssignment(Types lValue, Types rValue, string message);
Types checkArithmetic(Types left, Types right);
Types checkLogical(Types left, Types right);
Types checkRelational(Types left, Types right);
Types checkTypeIsInt(Types left, Types right);
Types checkIfThenStmt(Types expression, Types lStatement, Types rStatement);
Types checkCaseExpression(Types expression);
Types checkCases(Types lCase, Types rCase);