class LinkedMap<KeyType, ValueType, Question trackSize = no; AnyInt IndexType = Int, Question noRepeats = yes> extends Map<KeyType, ValueType; IndexType> {
	class Entry {
		KeyType key;
		ValueType value;
		create(field.key, field.value);
	}

	final LinkedList<Entry; IndexType, trackSize> _entries = LinkedList();
	
	IndexType size -> _entries.size;
	
	Question isEmpty -> _entries.isEmpty;
	
	clear() _entries.clear();

  Question containsKey(KeyType key) {
		for(each entry in _entries) if(entry.key == key) return yes;
		return no;
	}
  
	Question containsValue(ValueType value) {
		for(each entry in _entries) if(entry.value == value) return yes;
		return no;
	}
	
	ValueType getAtIndex(KeyType key) {
		for(each entry in _entries) if(entry.key == key) return entry.value;
		return null;
	}
	
	setAtIndex(KeyType key, ValueType value) {
		if(noRepeats) remove(key);
		_entries.addFirst(Entry(key, value));
	}
	
	remove(KeyType key) {
		for(each entry in _entries)
			if(entry.key == key) {
				iterator.remove();
				if(noRepeats) return;
			}
	}
  
  Iterator<ElementType> iterator -> _entries.iterator;
}