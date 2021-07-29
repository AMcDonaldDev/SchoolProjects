#include <cctype>
#include <iostream>
#include <sstream>
#include <list>
#include <string>

using namespace std;

#include "expression.h"
#include "subexpression.h"
#include "operand.h"
#include "variable.h"
#include "literal.h"
#include "parse.h"

Expression* Operand::parse(stringstream& stream)
{
	char paren;
	double value;

	stream >> ws;
	if (isdigit(stream.peek()))
	{
		stream >> value;
		Expression* literal = new Literal(value);
		return literal;
	}
	if (stream.peek() == '(')
	{
		stream >> paren;
		return SubExpression::parse(stream);
	}
	else
		return new Variable(parseName(stream));
	return 0;
}