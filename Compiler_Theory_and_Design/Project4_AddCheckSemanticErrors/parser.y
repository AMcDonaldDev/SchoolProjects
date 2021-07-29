/* Compiler Theory and Design
   Dr. Duane J. Jarc */

/* Name: Allison McDonald
   CMSC 430 Project 4
   Date: 12/14/2020
   Code Description: Syntactic analyzer -
   Code was modified to to add checks for semantic errors.
   The checks include using Boolean expressions with arithmetic and relational operator,
   using arithmetic expressions with logical operator, reductions containing nonnumeric types,
   remainder operator requires integer operands, narrowing variable initialization,
   variable initialization mismatch, undeclared variable, duplicate variable, narrowing function return,
   if condition not boolean, if-then type mismatch, case expression not integer, and case type mismatch.
   Original code included the bison deprecated directive %error-verbose.
   The directive has been updated to %define parse.error verbose */

%{

#include <iostream>
#include <string>
#include <vector>
#include <map>
#include <cmath>

using namespace std;

#include "types.h"
#include "listing.h"
#include "symbols.h"

int yylex();
void yyerror(const char* message);

Symbols<Types> symbols;

%}

%define parse.error verbose

%union
{
	CharPtr iden;
	Types type;
}

%token <iden> IDENTIFIER
%token <type> INT_LITERAL BOOL_LITERAL REAL_LITERAL

%token EXPOP ADDOP MULOP RELOP REMOP
%token ANDOP OROP NOTOP

%token BEGIN_ BOOLEAN END ENDREDUCE FUNCTION INTEGER IS REDUCE RETURNS
%token ARROW CASE ELSE ENDCASE ENDIF IF OTHERS REAL THEN WHEN

%type <type> type function_header body statement statement_ reductions expression binary relation term factor exponent unary primary cases case

%%

function:	
	function_header optional_variable body {checkAssignment($1, $3, "Function Return");} ;
	
function_header:
	FUNCTION IDENTIFIER parameters RETURNS type ';' {$$ = $5;} |	
	FUNCTION IDENTIFIER RETURNS type ';' {$$ = $4;} |
	error ';' {$$ = MISMATCH;} ;

optional_variable:
	optional_variable variable |
	error ';' |
	;

variable:
	IDENTIFIER ':' type IS statement_ 
	{checkAssignment($3, $5, "Variable Initialization");
	{if (symbols.find($1, $3)) appendError(DUPLICATE_IDENTIFIER, $1); symbols.insert($1, $3);}} ;

parameters:
	parameter optional_parameter ;

optional_parameter:
	optional_parameter ',' parameter |
	;	

parameter:
	IDENTIFIER ':' type ;

type:
	INTEGER {$$ = INT_TYPE;} |
	REAL {$$ = REAL_TYPE;} |
	BOOLEAN {$$ = BOOL_TYPE;} ;

body:
	BEGIN_ statement_ END ';' {$$ = $2;} ;
    
statement_:
	statement ';' {$$ = $1;} |
	error ';' {$$ = MISMATCH;} ;
	
statement:
	expression |
	REDUCE operator reductions ENDREDUCE {$$ = $3;} ; |
	IF expression THEN statement_ ELSE statement_ ENDIF 
	{$$ = checkIfThenStmt($2, $4, $6);} ; |
	CASE expression IS cases OTHERS ARROW statement_ ENDCASE
	{checkCaseExpression($2);
		if ($4 != INITIAL_TYPE) {
			$$ = $4;
		} else {
			$$ = $7;
		}
		;
	} ;

cases:
	cases case
	{
		if ($1 != INITIAL_TYPE) {
			$$ = $1;
		} else {
			$$ = $2;
		}
		;

		if ($1 != INITIAL_TYPE && $2 != INITIAL_TYPE) {
			$$ = checkCases($1, $2);
		}
	} ; |
	{$$ = INITIAL_TYPE;} ;

case:
	WHEN INT_LITERAL ARROW statement_
	{
		if ($<type>-2 == $2) {
			$$ = $4;
		} else {
			$$ = INITIAL_TYPE;
		}
		;
	} ; |
	error ';' {$$ = MISMATCH;} ;

operator:
	RELOP |
	ADDOP |
	MULOP |
	REMOP |
	EXPOP ;

reductions:
	reductions statement_ {$$ = checkArithmetic($1, $2);} |
	{$$ = INT_TYPE;} ;
		    
expression:
	expression OROP binary {$$ = checkLogical($1, $3);} |
	binary ;

binary:
	binary ANDOP relation {$$ = checkLogical($1, $3);} |
	relation ;

relation:
	relation RELOP term {$$ = checkRelational($1, $3);} |
	term ;

term:
	term ADDOP factor {$$ = checkArithmetic($1, $3);} |
	factor ;
      
factor:
	factor MULOP exponent {$$ = checkArithmetic($1, $3);} |
	factor REMOP exponent {$$ = checkTypeIsInt($1, $3);} |
	exponent ;

exponent:
	unary |
	unary EXPOP exponent {$$ = checkArithmetic($1, $3);} ;

unary:
	NOTOP unary {$$ = checkLogical($2, $2);} |
	primary ;

primary:
	'(' expression ')' {$$ = $2;} |
	INT_LITERAL |
	REAL_LITERAL |
	BOOL_LITERAL | 
	IDENTIFIER {if (!symbols.find($1, $$)) appendError(UNDECLARED, $1);} ;
    
%%

void yyerror(const char* message)
{
	appendError(SYNTAX, message);
}

int main(int argc, char *argv[])    
{
	firstLine();
	yyparse();
	lastLine();
	return 0;
} 
