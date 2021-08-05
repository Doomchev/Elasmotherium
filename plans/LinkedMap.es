class LinkedMap<KeyType, ValueType; Question unique> extends Map<KeyType, ValueType> {
	class Entry {
		KeyType key;
		ValueType value;
		create(field.key, field.value);
	}

	final LinkedList<Entry> _entries = LinkedList();
	
	Int size -> _entries.size;
	
	Question isEmpty -> _entries.isEmpty;
	
	clear() _entries.clear();
	
	ValueType getAtIndex(KeyType key) {
		forEach(Entry entry in _entries) if(entry.key == key) return entry.value;
		return null;
	}
	
	setAtIndex(KeyType key, ValueType value) {
		if(unique) remove(key);
		_entries.addFirst(Entry(key, value));
	}
	
	remove(KeyType key) {
		forEach(Entry entry in _entries)
			if(entry.key == key) {
				removeItem;
				if(unique) return;
			}
	}
}