class Map<KeyType, ValueType> {
	Int size();
	Question isEmpty -> size == 0;
	clear();
	
	Question containsKey(KeyType key) {
		for(each entry in this) if(entry.key == key) return yes;
		return no;
	}
	
	Question containsValue(ValueType value) {
		for(each entry in this) if(entry.value == value) return yes;
		return no;
	}
	
	ValueType getAtIndex(KeyType key);
	setAtIndex(KeyType key, ValueType value);
	remove(KeyType key);
}