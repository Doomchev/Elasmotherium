class Object;
class Structure;
class Class;
class I8 extends Structure;
class U8 extends Structure;
class I16 extends Structure;
class U16 extends Structure;
class I32 extends Structure;
class U32 extends Structure;
class I32 extends Structure;
class U32 extends Structure;
class F32 extends Structure;
class F64 extends Structure;
alias Int, I64;
alias Float, F64;
enum Bool {no, yes};

class Array<ElementType> extends Value {
	Int size();
	ElementType at(Int index);
	at(Int index, ElementType value);
}

class List<ElementType> extends Collection {
	addLast(Element element);
}

class Map<ElementType> extends Collection {
	addLast(Element element);
}

class Char extends U16;
class String extends Array<Char> {
	Int fromHex() {
		value = 0;
		for(n = 0 ..< $size) {
			digit = "0123456789ABCDEF".indexOf(this[n]);
			value = (value << 4) + (digit < 0 ? 0 : digit);
		}
		return value;
	}
}

print(String message);
String ask(String message);
show(String message);

exit();