#include <cctype>
#include <iostream>
#include <sstream>
#include <string>
using namespace std;

#include "parse.h"

string parseName(stringstream& stream)
{
	char alnum;
	string name = "";

	stream >> ws;
	while (isalnum(stream.peek()))
	{
		stream >> alnum;
		name += alnum;
	}
	return name;
}