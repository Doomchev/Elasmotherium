import Array;
import AnyChar;
import Comparsion;

struc String<AnyChar CharType, AnyInt IndexType> extends Array<CharType> {
  create.from(number of AnyNumber);
  create.from(Object object) -> object.toString;
  create.from(char of CharType, quantity of IndexType);
	
  compareTo(String string) returns Comparsion {
    if(size is not equal to string.size) return new Comparsion(size, size of string);
    for(i from 0 until size) {
			if(this[i] is equal to string[i]) continue;
			return new Comparsion(this[i], string[i]);
		}
    return equal;
  }
	
  lowerCase returns ThisType {
		equate newString to new ThisType(size);
		for(i from 0 until size) equate newString[i] to lowerCase of this[i];
		return newString;
	}
	
  upperCase returns ThisType {
		equate newString to new ThisType(size);
		for(i from 0 until size) equate newString[i] to upperCase of this[i];
		return newString;
	}
	
	trim returns ThisType {
		equate start of IndexType to 0, end to size - 1;
		while(start is less than size and this[start] is not more than " ") increment start;
		if(start is equal to size) return "";
		while(this[end] is not more than " ") decrement end;
		return this[from start to end];
	}

	split(char of Char) returns Array<of ThisType> {
		equate tokens to new List<of String>();
		equate IndexType start to 0;
		for(i from 0 until size)
			if(this[i] is equal to char) {
				add this[from start until i] to tokens;
				equate start to i + 1;
			}
		if(start is less than size - 1) add this[from start] to tokens;
		return tokens;
	}
	
	Question startsWith(start of String) {
		if(size of start is more than size) return no;
		for(i from 0 until size of start) if(this[i] is not equal to start[i]) return no;
		return yes;
	}
	
	 Question endsWith(String end) {
		if(size of end is more than size) return no;
		for(i from size - size of end until size) if(this[i] != start[i]) return no;
		return yes;
	}
}

Question.from(String string) -> size of string is more than 0;