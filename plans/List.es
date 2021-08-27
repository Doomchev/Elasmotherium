class List<ElementType; AnyInt IndexType> {
	IndexType size();
	Question isEmpty -> size == 0;
	ElementType first();
	ElementType last();
	clear();
	Question contains(ElementType element);
	add(ElementType element) addLast(element);
	addFirst(ElementType element);
	addLast(ElementType element);
	remove(ElementType element);
  removeFirst() assert(!isEmpty);
  removeLast() assert(!isEmpty);
	IndexType indexOf(ElementType element);
	ElementType getAtIndex(IndexType index) assert(0 <= index < size);
	setAtIndex(IndexType index, ElementType element) assert(0 <= index < size);
	removeAtIndex(IndexType index) assert(0 <= index < size);
}

Question.from(List list) -> !list.isEmpty;