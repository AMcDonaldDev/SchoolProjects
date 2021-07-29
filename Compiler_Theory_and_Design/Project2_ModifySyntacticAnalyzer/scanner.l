/* Compiler Theory and Design
   Dr. Duane J. Jarc */

/* Name: Allison McDonald
   CMSC 430 Project 2
   Date: 11/30/2020
   Code Description: Lexical analyzer -
   Code was taken from CMSC 430 Project 1 with the main method removed */

/* This file contains flex input file */

%{
#include <cstdio>
#include <string>

using namespace std;

#include "listing.h"
#include "tokens.h"

%}

%option noyywrap

ws		[ \t\r]+
comment		\-\-.*\n|\/\/.*\n
line		[\n]
id		[A-Za-z][A-Za-z0-9]*([_]?[A-Za-z0-9]*[A-Za-z0-9])*
digit		[0-9]
int		{digit}+
real		{int}([.]{int}*[eE]?[+-]?{int}*)?
punc		[\(\),:;]
%%

{ws}		{ ECHO; }
{comment}	{ ECHO; nextLine();}
{line}		{ ECHO; nextLine();}
"=>"		{ ECHO; return(ARROW); }
"<"		{ ECHO; return(RELOP); }
"="		{ ECHO;	return(RELOP); }
"/="		{ ECHO; return(RELOP); }
">"		{ ECHO; return(RELOP); }
">="		{ ECHO;	return(RELOP); }
"<="		{ ECHO; return(RELOP); }
"+"		{ ECHO; return(ADDOP); }
"-"		{ ECHO; return(ADDOP); }
"*"		{ ECHO; return(MULOP); }
"/"		{ ECHO; return(MULOP); }
"**"		{ ECHO; return(EXPOP); }
begin		{ ECHO; return(BEGIN_); }
boolean		{ ECHO; return(BOOLEAN); }
end		{ ECHO; return(END); }
endreduce	{ ECHO; return(ENDREDUCE); }
function	{ ECHO; return(FUNCTION); }
integer		{ ECHO; return(INTEGER); }
is		{ ECHO; return(IS); }
reduce		{ ECHO; return REDUCE; }
returns		{ ECHO; return(RETURNS); }
and		{ ECHO; return(ANDOP); }
case		{ ECHO; return(CASE);	}
else		{ ECHO; return(ELSE);	}
endcase		{ ECHO; return(ENDCASE);}
endif		{ ECHO; return(ENDIF);	}
if		{ ECHO;	return(IF);	}
others		{ ECHO; return(OTHERS);	}
real		{ ECHO; return(REAL);	}
then		{ ECHO; return(THEN);	}
when		{ ECHO; return(WHEN);	}
or		{ ECHO; return(OROP);	}
not		{ ECHO; return(NOTOP);	}
rem		{ ECHO; return(REMOP);	}
true		{ ECHO; return(BOOL_LITERAL);}
false		{ ECHO; return(BOOL_LITERAL);}
{id}		{ ECHO; return(IDENTIFIER);}
{int}		{ ECHO; return(INT_LITERAL); }
{real}		{ ECHO; return(REAL_LITERAL);}
{punc}		{ ECHO; return(yytext[0]); }
.		{ ECHO; appendError(LEXICAL, yytext); }
%%
