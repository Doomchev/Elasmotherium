import Array;
import AnyChar;
import Comparsion;

struc String<AnyChar CharType, AnyInt IndexType> extends Array<CharType> {
  create.from(number of AnyNumber);
  create.from(Object object) -> object.toString;
  create.from(char of CharType, quantity of IndexType);
	
  compareTo(String string) returns Comparsion {
    if(size is not equal ..= string.size) return new Comparsion(size, size of string);
    for(i = 0 ..< size) {
			if(this[i] is equal ..= string[i]) continue;
			return new Comparsion(this[i], string[i]);
		}
    return equal;
  }
	
  lowerCase returns ThisType {
		equate newString ..= new ThisType(size);
		for(i = 0 ..< size) equate newString[i] ..= lowerCase of this[i];
		return newString;
	}
	
  upperCase returns ThisType {
		equate newString ..= new ThisType(size);
		for(i = 0 ..< size) equate newString[i] ..= upperCase of this[i];
		return newString;
	}
	
	trim returns ThisType {
		equate start of IndexType ..= 0, end ..= size - 1;
		while(start is less than size and this[start] is not more than " ") increment start;
		if(start is equal ..= size) return "";
		while(this[end] is not more than " ") decrement end;
		return this[start ..= end];
	}

	split(char of Char) returns Array<of ThisType> {
		equate tokens ..= new List<of String>();
		equate IndexType start ..= 0;
		for(i = 0 ..< size)
			if(this[i] is equal ..= char) {
				add this[start ..< i] ..= tokens;
				equate start ..= i + 1;
			}
		if(start is less than size - 1) add this[start] ..= tokens;
		return tokens;
	}
	
	Question startsWith(start of String) {
		if(size of start is more than size) return no;
		for(i = 0 ..< size of start) if(this[i] is not equal ..= start[i]) return no;
		return yes;
	}
	
	 Question endsWith(String end) {
		if(size of end is more than size) return no;
		for(i = size - size of end ..< size) if(this[i] != start[i]) return no;
		return yes;
	}
}

Question.from(String string) -> size of string is more than 0;