#pragma once

#include "subexpression.h"

class Ternary : public SubExpression
{
public:
	Ternary(Expression* left, Expression* right, Expression* condition) :
		SubExpression(left, right, condition)
	{
	}
	int evaluate()
	{
		if (condition->evaluate()) {
			return left->evaluate();
		}
		return right->evaluate();
	}
};
