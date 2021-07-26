struc String<AnyChar CharType, AnyInt IndexType> extends Array<CharType> {
  this(AnyNumber number);
  this(Object object) -> object.toString;
	
  This lowerCase() {
		This = This(size);
		for(Int i = 0 .. size) newString[i] = this[i].lowerCase;
		return newString;
	}
	
  This upperCase() {
		This newString = This(size);
		for(Int i = 0 .. size) newString[i] = this[i].upperCase;
		return newString;
	}
	
	This trim() {
		IndexType from = 0, to = size - 1;
		while(from < size && this[from] <= " ") from++;
		if(from == size) return "";
		while(this[to] <= " ") to++;
		return this[from ..= to];
	}

	Array<This> split(Char char);
	
	Bool startsWith(String start) {
		if(start.size > size) return no;
		for(Int i = 0 .. start.size) if(this[i] != start[i]) return no;
		return yes;
	}
}