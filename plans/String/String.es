import Array;
import AnyChar;
import Comparsion;

struc String<AnyChar CharType, AnyInt IndexType> extends Array<CharType> {
  create.from(AnyNumber number);
  create.from(Object object) -> object.toString;
  create.from(CharType char, IndexType quantity);
	
  Comparsion compareTo(String string) {
    if(size != string.size) return Comparsion(size, string.size);
    for(i from 0 until size) {
			if(this[i] == string[i]) continue;
			return Comparsion(this[i], string[i]);
		}
    return equal;
  }
	
  ThisType lowerCase() {
		newString = ThisType(size);
		for(i from 0 until size) newString[i] = this[i].lowerCase;
		return newString;
	}
	
  ThisType upperCase() {
		newString = ThisType(size);
		for(i from 0 until size) newString[i] = this[i].upperCase;
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
		tokens = List<String>();
		IndexType start = 0;
		for(i from 0 until size)
			if(this[i] == char) {
				tokens.add(this[from start until i]);
				start = i + 1;
			}
		if(start < size - 1) tokens.add(this[from start]);
		return tokens;
	}
	
	Question startsWith(String start) {
		if(start.size > size) return no;
		for(i from 0 until start.size) if(this[i] != start[i]) return no;
		return yes;
	}
	
	Question endsWith(String end) {
		if(end.size > size) return no;
		for(i from size - end.size until size) if(this[i] != start[i]) return no;
		return yes;
	}
}

Question.from(String string) -> string.size > 0;