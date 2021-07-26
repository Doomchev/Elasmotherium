include Math;

class Object;
class Class;
class Int;
class Float;
class Bool;
Bool yes;
Bool no;

class Array<ElementType> {
	Int size();
	ElementType at(Int index);
	at(Int index, ElementType value);
}

class List<ElementType> {
	Int size();
	addLast(ElementType element);
}

class Map<KeyType, ValueType> {
	Int size();
	ValueType at(KeyType key);
	at(KeyType key, ValueType value);
}

class Char;
class String {
	Int size();
	Char at(Int index);
}

print(String message);
println(String message);
Int askInteger(String message);
tell(String message);

exit() {}