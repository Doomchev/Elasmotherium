import Math;

/*class Float;
class Question;
Question yes;
Question no;

class Map<KeyType, ValueType> {
	Int size();
	ValueType at(KeyType key);
	at(KeyType key, ValueType value);
}

class Char;
class String {
	Int size();
	Char at(Int index);
}*/

Int screenWidth();
Int screenHeight();

class Array<ElementType> {
  create(Int size);
	Int size();
}

class List<ElementType> {
	Int size();
	add(ElementType element);
}

println(String message);
Int askInt(String message);
say(String message);
Int randomInt(Int value);
Int randomInt(Int start, Int end);

exit() {}