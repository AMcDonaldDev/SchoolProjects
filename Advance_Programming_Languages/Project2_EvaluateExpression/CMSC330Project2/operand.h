#pragma once

#include <sstream>

#include "expression.h"

class Operand : public Expression
{
public:
	static Expression* parse(stringstream& stream);
};
