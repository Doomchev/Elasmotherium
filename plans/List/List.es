class List<ElementType; AnyInt IndexType> {
  create() -> List.LinkedList<ElementType; IndexType>();
  from(Array<ElementType> array) {
    list = new ThisType(array.size);
    for(each element in array) list.add(element);
    return list;
  }
  
	IndexType size();
	Question isEmpty -> size == 0;
	ElementType first() assert(!isEmpty);
	ElementType last() assert(!isEmpty);
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