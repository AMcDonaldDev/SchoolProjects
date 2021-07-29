#include <iostream>
#include <sstream>
using namespace std;

#include "expression.h"
#include "subexpression.h"
#include "operand.h"
#include "plus.h"
#include "minus.h"
#include "times.h"
#include "divide.h"
#include "greaterthan.h"
#include "lessthan.h"
#include "equalto.h"
#include "logicaland.h"
#include "logicalor.h"
#include "ternary.h"
#include "logicalnegation.h"

SubExpression::SubExpression(Expression* left, Expression* right)
{
	this->left = left;
	this->right = right;
}

SubExpression::SubExpression(Expression* left, Expression* right, Expression* condition)
{
	this->left = left;
	this->right = right;
	this->condition = condition;
}

SubExpression::SubExpression(Expression* left)
{
	this->left = left;
}

Expression* SubExpression::parse(stringstream& stream)
{
	Expression* left;
	Expression* right;
	Expression* condition;
	char operation, paren, questionMark;

	left = Operand::parse(stream);
	stream >> operation;
	switch (operation)
	{
		case '!':
			stream >> paren;
			return new LogicalNegation(left);
			break;
		case ':':
			right = Operand::parse(stream);
			stream >> questionMark;
			condition = Operand::parse(stream);
			stream >> paren;
			return new Ternary(left, right, condition);
			break;
		case '+':
			right = Operand::parse(stream);
			stream >> paren;
			return new Plus(left, right);
			break;
		case '-':
			right = Operand::parse(stream);
			stream >> paren;
			return new Minus(left, right);
			break;
		case '*':
			right = Operand::parse(stream);
			stream >> paren;
			return new Times(left, right);
			break;
		case '/':
			right = Operand::parse(stream);
			stream >> paren;
			return new Divide(left, right);
			break;
		case '>':
			right = Operand::parse(stream);
			stream >> paren;
			return new GreaterThan(left, right);
			break;
		case '<':
			right = Operand::parse(stream);
			stream >> paren;
			return new LessThan(left, right);
			break;
		case '=':
			right = Operand::parse(stream);
			stream >> paren;
			return new EqualTo(left, right);
			break;
		case '&':
			right = Operand::parse(stream);
			stream >> paren;
			return new LogicalAnd(left, right);
			break;
		case '|':
			right = Operand::parse(stream);
			stream >> paren;
			return new LogicalOr(left, right);
			break;
	}
	return 0;
}
