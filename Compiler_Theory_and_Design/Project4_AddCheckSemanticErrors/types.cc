// Compiler Theory and Design
// Duane J. Jarc

/* Name: Allison McDonald
   CMSC 430 Project 4
   Date: 12/14/2020
   Code Description: Type Checking
   This file contains the functions to check type semantics for boolean expressions,
   arithmetic expressions, reductions, remainder operator, if-then statement, case statement,
   narrowing variable initialization, variable initialization, undeclared variable,
   duplicate variable, and narrowing function return. */
   
   //This file contains the bodies of the type checking functions

#include <string>
#include <vector>

using namespace std;

#include "types.h"
#include "listing.h"

void checkAssignment(Types lValue, Types rValue, string message)
{
	if (lValue == INT_TYPE && rValue == REAL_TYPE)
	{
		appendError(GENERAL_SEMANTIC, "Illegal Narrowing " + message);
	}
	else if (lValue != MISMATCH && rValue != MISMATCH && lValue != rValue)
	{
		appendError(GENERAL_SEMANTIC, "Type Mismatch on " + message);
	}

}

Types checkArithmetic(Types left, Types right)
{
	if (left == MISMATCH || right == MISMATCH)
		return MISMATCH;
	if (left == BOOL_TYPE || right == BOOL_TYPE)
	{
		appendError(GENERAL_SEMANTIC, "Numeric Type Required");
		return MISMATCH;
	}
	if (left == REAL_TYPE || right == REAL_TYPE)
	{
		return REAL_TYPE;
	}
	return INT_TYPE;
}

Types checkLogical(Types left, Types right)
{
	if (left == MISMATCH || right == MISMATCH)
		return MISMATCH;
	if (left != BOOL_TYPE || right != BOOL_TYPE)
	{
		appendError(GENERAL_SEMANTIC, "Boolean Type Required");
		return MISMATCH;
	}	
		return BOOL_TYPE;
	return MISMATCH;
}

Types checkRelational(Types left, Types right)
{
	if (checkArithmetic(left, right) == MISMATCH)
		return MISMATCH;
	return BOOL_TYPE;
}

Types checkTypeIsInt(Types left, Types right)
{
	if (checkArithmetic(left, right) == MISMATCH)
		return MISMATCH;
	if (left != INT_TYPE || right != INT_TYPE)
	{
		appendError(GENERAL_SEMANTIC, "Remainder Operator Requires Integer Operands");
		return MISMATCH;
	}
	return INT_TYPE;
}

Types checkIfThenStmt(Types expression, Types lStatement, Types rStatement)
{
	if (expression != BOOL_TYPE)
	{
		appendError(GENERAL_SEMANTIC, "If Expression Must Be Boolean");
		return MISMATCH;
	}
	if (lStatement != rStatement)
	{
		appendError(GENERAL_SEMANTIC, "If-Then Type Mismatch");
		return MISMATCH;
	}
	if (lStatement == BOOL_TYPE && rStatement == BOOL_TYPE)
	{
		return BOOL_TYPE;
	}
	
	if (lStatement == REAL_TYPE && rStatement == REAL_TYPE)
	{
		return REAL_TYPE;
	}
	return INT_TYPE;
}

Types checkCaseExpression(Types expression)
{
	if (expression != INT_TYPE)
	{
		appendError(GENERAL_SEMANTIC, "Case Expression Not Integer");
		return MISMATCH;
	}
	return INT_TYPE;
}

Types checkCases(Types lCase, Types rCase)
{
	if (lCase != rCase)
	{
		appendError(GENERAL_SEMANTIC, "Case Types Mismatch");
		return MISMATCH;
	}
	if (lCase == BOOL_TYPE && rCase == BOOL_TYPE)
	{
		return BOOL_TYPE;
	}
	if (lCase == REAL_TYPE && rCase == REAL_TYPE)
	{
		return REAL_TYPE;
	}
	return INT_TYPE;
}
