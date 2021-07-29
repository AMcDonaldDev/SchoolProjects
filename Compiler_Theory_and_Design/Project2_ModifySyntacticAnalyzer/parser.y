/* Compiler Theory and Design
   Dr. Duane J. Jarc */

/* Name: Allison McDonald
   CMSC 430 Project 2
   Date: 11/30/2020
   Code Description: Syntactic analyzer -
   Code was modified to include the full grammar of the language and
   to detect and recover for additional syntax while not producting 
   any shift/reduce or reduce/reduce errors.
   Original code included the bison deprecated directive %error-verbose.
   The directive has been updated to %define parse.error verbose */

%{

#include <string>

using namespace std;

#include "listing.h"

int yylex();
void yyerror(const char* message);

%}

%define parse.error verbose

%token IDENTIFIER
%token INT_LITERAL BOOL_LITERAL REAL_LITERAL

%token EXPOP ADDOP MULOP RELOP ANDOP

%token BEGIN_ BOOLEAN END ENDREDUCE FUNCTION INTEGER IS REDUCE RETURNS
%token ARROW CASE ELSE ENDCASE ENDIF IF OTHERS REAL THEN WHEN OROP NOTOP REMOP

%%

function:	
	function_header optional_variable body ;
	
function_header:
	FUNCTION IDENTIFIER parameters RETURNS type ';' |	
	FUNCTION IDENTIFIER RETURNS type ';' |
	error ';' ;

optional_variable:
	optional_variable variable |
	error ';' |
	;

variable:
	IDENTIFIER ':' type IS statement_ ;

parameters:
	parameter optional_parameter ;

optional_parameter:
	optional_parameter ',' parameter |
	;	

parameter:
	IDENTIFIER ':' type ;

type:
	INTEGER |
	REAL |
	BOOLEAN ;

body:
	BEGIN_ statement_ END ';' ;
    
statement_:
	statement ';' |
	error ';' ;
	
statement:
	expression |
	REDUCE operator reductions ENDREDUCE ; |
	IF expression THEN statement_ ELSE statement_ ENDIF ; |
	CASE expression IS case OTHERS ARROW statement_ ENDCASE ;

case:
	case WHEN INT_LITERAL ARROW statement_ |
	error ';' |
	;

operator:
	ADDOP |
	MULOP ;

reductions:
	reductions statement_ |
	;
		    
expression:
	expression OROP binary |
	binary ;

binary:
	binary ANDOP relation |
	relation ;

relation:
	relation RELOP term |
	term ;

term:
	term ADDOP factor |
	factor ;
      
factor:
	factor MULOP exponent |
	factor REMOP exponent |
	exponent ;

exponent:
	unary |
	unary EXPOP exponent ;

unary:
	NOTOP unary |
	primary ;

primary:
	'(' expression ')' |
	INT_LITERAL |
	REAL_LITERAL |
	BOOL_LITERAL | 
	IDENTIFIER ;
    
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
