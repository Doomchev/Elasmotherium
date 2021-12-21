structure String<AnySymbol SymbolType, AnyInteger IndexType> extends Array<of SymbolType> {
  create(from number of AnyNumber);
  create(from object of Object) via object.toString;
	
  String.OfSameType inLowerCase() {
		newString = new String.OfSameType(of this.String's.size);
		for(i from 0 until size) equate newString's.symbol(i) to this.String's.symbol(i).inLowerCase;
		return newString;
	}
	
  String.OfSameType inUpperCase() {
		newString = new String.OfSameType(of this.String's.size);
		for(i from 0 until size) equate newString's.symbol(i) to this.String's.symbol(i).inUpperCase;
		return newString;
	}
	
	String.OfSameType trimmed() {
    IndexType start = 0, end = size - 1;
		while(start is less than size and this.String's.symbol(start) is not more than " ") increment start;
		if(start is equal to size) return "";
		while(this.String's.symbol(end) is not more than " ") increase end;
		return this.String's.part(from start to end);
	}

	split is Array.OfSameType(symbol is AnySymbol);
	
	Question startsWith(String start) {
		if(start's.size is more than size) answer no;
		for(i from 0 until start's.size) if(this.String's.symbol(i) is not equal to start's.symbol(i)) answer no;
		answer yes;
	}
}