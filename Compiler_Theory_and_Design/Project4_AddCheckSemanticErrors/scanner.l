/* Compiler Theory and Design
   Dr. Duane J. Jarc */

/* Name: Allison McDonald
   CMSC 430 Project 4
   Date: 12/14/2020
   Code Description: Lexical analyzer -
   Code was taken from CMSC 430 Project 1 with the main method removed.
   Modifications were made to generate a value type for Real, Integer & Boolean literals */

/* This file contains flex input file */

%{
#include <string>

using namespace std;

#include "types.h"
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
rem		{ ECHO; return(REMOP);	}
begin		{ ECHO; return(BEGIN_); }
boolean		{ ECHO; return(BOOLEAN); }
end		{ ECHO; return(END); }
endreduce	{ ECHO; return(ENDREDUCE); }
function	{ ECHO; return(FUNCTION); }
integer		{ ECHO; return(INTEGER); }
is		{ ECHO; return(IS); }
reduce		{ ECHO; return (REDUCE); }
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
true		{ ECHO; yylval.type = BOOL_TYPE; return(BOOL_LITERAL);}
false		{ ECHO; yylval.type = BOOL_TYPE; return(BOOL_LITERAL);}
{id}		{ ECHO; yylval.iden = (CharPtr)malloc(yyleng + 1);
			strcpy(yylval.iden, yytext); return(IDENTIFIER);}
{int}		{ ECHO; yylval.type = INT_TYPE; return(INT_LITERAL); }
{real}		{ ECHO; yylval.type = REAL_TYPE; return(REAL_LITERAL);}
{punc}		{ ECHO; return(yytext[0]); }
.		{ ECHO; appendError(LEXICAL, yytext); }
%%
