#pragma once

#include "subexpression.h"

class LogicalAnd : public SubExpression
{
public:
	LogicalAnd(Expression* left, Expression* right) :
		SubExpression(left, right)
	{
	}
	int evaluate()
	{
		return left->evaluate() && right->evaluate();
	}
};
