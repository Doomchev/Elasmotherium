class Map<KeyType, ValueType> {
	Int size();
	Question isEmpty -> size == 0;
	clear();
	Question containsKey(KeyType key);
	Question containsValue(ValueType value);
	ValueType getAtIndex(KeyType key);
	setAtIndex(KeyType key, ValueType value);
	remove(KeyType key);
}