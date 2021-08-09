class Object;

class Class;

class Number {
	final ThisType min, max;
}

struc I8 extends Number {I8 min = -128, max = 127;}
struc U8 extends Number {U8 min = 0, max = 255;}
struc I16 extends Number {I16 min = -32768, max = 32767;}
struc U16 extends Number {U16 min = 0, max = 65536;}
struc I32 extends Number {I32 min = -2147483648, max = 2147483647;}
struc U32 extends Number {U32 min = 0, max = 4294967295;}
struc I64 extends Number {I64 min = -9223372036854775808, max = 9223372036854775807;}
struc U64 extends Number {U64 min = 0, max = 18446744073709551615;}
struc F32 extends Number {F32 min = -3.4028234e38, max = 3.4028234e38;}
struc F64 extends Number {F64 min = -1.7976931348623157e308, max = 1.7976931348623157e308;}
alias Int I64;
alias Float F64;

enum Question {no, yes};

classlist AnyInt {I64, U64, I8, U8, I16, U16, I32, U32}
classlist AnyFloat {F64, F32}
classlist AnyNumber {AnyInt, AnyFloat}

class AChar extends U8;
class Char extends U16;
classlist AnyChar {Char, AChar}

print(String message);
Int askInteger(String message);
tell(String message);

exit();