#pragma once

#include "subexpression.h"

class LessThan : public SubExpression
{
public:
	LessThan(Expression* left, Expression* right) :
		SubExpression(left, right)
	{
	}
	int evaluate()
	{
		return left->evaluate() < right->evaluate();
	}
};
