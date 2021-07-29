#pragma once

#include "subexpression.h"

class LogicalNegation : public SubExpression
{
public:
	LogicalNegation(Expression* left) :
		SubExpression(left)
	{
	}
	int evaluate()
	{
		return !left->evaluate();
	}
};
