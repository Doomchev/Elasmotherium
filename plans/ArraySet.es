class ArraySet<ElementType; AnyInt IndexType> {
	Array<ElementType; IndexType> _items;
	IndexType size = 0;
	
	create(IndexType initialSize) _items = Array(initialSize);
	
	add(ElementType element) {
		size++;
		if(size >= _items.size) _items = _items.expand(size * 2);
		_items[size] = element;
	}
	
	IndexType indexOf(ElementType element) {
		for(IndexType i from 0 until size) if(_items[i] == element) return i;
		return notFound;
	}
	
	Question getAtIndex(ElementType element) -> indexOf(element) == notFound ? no : yes;
	
	remove(ElementType element) {
		IndexType i = 0;
		while(i < size) {
			if(_items[size] == element) {
				size--;
				_items[i] = _items[size];
				continue;
			}
			i++;
		}
	}
}