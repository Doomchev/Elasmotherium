class ArraySet<ElementType; AnyInt IndexType = Int, Question noRepeats = yes> extends Set<ElementType; IndexType> {
  class Iterator {
    final ArraySet<ElementType; IndexType> _set;
    IndexType _index = -1;
    
    create(field._set);
    
    Question hasNext -> _index < _set.size - 1;
    
    ElementType next() {
      assert(_index >= 0);
      _index++;
      return _set._items[_index];
    }
    
    remove() {
      assert(0 <= _index < _set.size);
      size--;
      _items[_index] = _items[size];
    }
  }

	Array<ElementType; IndexType> _items;
	readonly IndexType size = 0;
	
	create(IndexType initialSize) _items = Array(initialSize);
	
  Question isEmpty() -> size == 0;
  
	clear() size = 0;
  
	add(ElementType element) {
    if(noRepeats && this[element]) return;
		size++;
		if(size >= _items.size) _items = _items.expand(size * 2);
		_items[size] = element;
	}
	
	Question getAtIndex(ElementType element) {
    for(i from 0 until size) if(_items[i] = element) return yes;
    return no;
  }
	
	remove(ElementType element) {
		i = 0;
		while(i < size) {
			if(_items[i] == element) {
				size--;
				_items[i] = _items[size];
        if(noRepeats) return;
				continue;
			}
			i++;
		}
	}
}