#pragma once

#include "expression.h"

class SubExpression : public Expression
{
public:
	SubExpression(Expression* left, Expression* right);
	SubExpression(Expression* left, Expression* right, Expression* condition);
	SubExpression(Expression* left);
	static Expression* parse(stringstream& stream);
protected:
	Expression* left;
	Expression* right;
	Expression* condition;
};
