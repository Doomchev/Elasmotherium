class List<ElementType; AnyInt IndexType> {
	IndexType size();
	Question isEmpty -> size == 0;
	ElementType first();
	ElementType last();
	clear();
	
	Question contains(ElementType element) {
		forEach(ElementType e in this) if(e == element) return yes;
		return no;
	}
	
	IndexType indexOf(ElementType element);
	addFirst(ElementType element);
	addLast(ElementType element);
	removeAtIndex(IndexType index) assert(0 <= index < size);
	
	add(ElementType element) addLast(ElementType element);
	ElementType getAtIndex(IndexType index) assert(0 <= index < size);
	setAtIndex(IndexType index, ElementType element) assert(0 <= index <= size);
	remove(ElementType element);
}