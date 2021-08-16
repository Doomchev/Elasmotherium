struc String<AnyChar CharType, AnyInt IndexType> extends Array<CharType> {
  create.from(AnyNumber number);
  create.from(Object object) -> object.toString;
	
  Int compareTo(String string) {
    if(size != string.size) return size - string.size;
    for(Int i from 0 until size) {
			if(this[i] == string[i]) continue;
			return this[i] - string[i];
		}
    return 0;
  }
	
  ThisType lowerCase() {
		ThisType newString = ThisType(size);
		for(IndexType i from 0 until size) newString[i] = this[i].lowerCase;
		return newString;
	}
	
  ThisType upperCase() {
		ThisType newString = ThisType(size);
		for(IndexType i from 0 until size) newString[i] = this[i].upperCase;
		return newString;
	}
	
	ThisType trim() {
		IndexType start = 0, end = size - 1;
		while(start < size && this[start] <= " ") start++;
		if(start == size) return "";
		while(this[end] <= " ") end--;
		return this[from start to end];
	}

	Array<ThisType> split(Char char) {
		List<String> tokens = List();
		IndexType start = 0;
		for(IndexType i from 0 until size)
			if(this[i] == char) {
				tokens += this[from start until i];
				start = i + 1;
			}
		if(start < size - 1) tokens += this[from start];
		return tokens;
	}
	
	Question startsWith(String start) {
		if(start.size > size) return no;
		for(IndexType i from 0 until start.size) if(this[i] != start[i]) return no;
		return yes;
	}
	
	Question endsWith(String end) {
		if(end.size > size) return no;
		for(IndexType i from size - end.size until size) if(this[i] != start[i]) return no;
		return yes;
	}
}