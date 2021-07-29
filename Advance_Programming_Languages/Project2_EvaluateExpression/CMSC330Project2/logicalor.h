#pragma once

#include "subexpression.h"

class LogicalOr : public SubExpression
{
public:
	LogicalOr(Expression* left, Expression* right) :
		SubExpression(left, right)
	{
	}
	int evaluate()
	{
		return left->evaluate() || right->evaluate();
	}
};
