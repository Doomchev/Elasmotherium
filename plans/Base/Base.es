import Number;
import Array;
import String;
import List;
import Map;
import Set;

class Object;
class Class;

struc I8 extends Number {min = -128, max = 127;}
struc U8 extends Number {min = 0, max = 255;}
struc I16 extends Number {min = -32768, max = 32767;}
struc U16 extends Number {min = 0, max = 65536;}
struc I32 extends Number {min = -2147483648, max = 2147483647;}
struc U32 extends Number {min = 0, max = 4294967295;}
struc I64 extends Number {min = -9223372036854775808, max = 9223372036854775807;}
struc U64 extends Number {min = 0, max = 18446744073709551615;}
struc F32 extends Number {min = -3.4028234e38, max = 3.4028234e38;}
struc F64 extends Number {min = -1.7976931348623157e308, max = 1.7976931348623157e308;}

alias Int, I64;
alias Float, F64;

enum Question {no, yes};

print(String message);
println(String message) print("\(message)\n");
Int.ask(String message);
tell(String message);

exit() {}