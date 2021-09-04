class Map<KeyType, ValueType; AnyInt IndexType = Int> {
  create() -> Map.LinkedMap<ElementType, IndexType>();
  
	IndexType size();
	Question isEmpty -> size == 0;
	clear();
	Question containsKey(KeyType key);
	Question containsValue(ValueType value);
	ValueType getAtIndex(KeyType key);
	setAtIndex(KeyType key, ValueType value);
	remove(KeyType key);
}