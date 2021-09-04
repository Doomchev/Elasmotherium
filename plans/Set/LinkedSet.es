class LinkedSet<ElementType, Question trackSize = no; AnyInt IndexType = Int, Question noRepeats = yes> extends Set<ElementType; IndexType> {
	final LinkedList<ElementType, trackSize; IndexType> _elements = LinkedList();
	
	IndexType size -> _elements.size;
	
	clear() _elements.clear();
	
	Question contains(ElementType element) -> _elements.contains(element);
	
	add(ElementType element) {
		if(noRepeats) remove(element);
		_elements.addFirst(element);
	}
	
	remove(ElementType element) {
		for(each e in _elements)
			if(e == element) {
				iterator.remove();
				if(noRepeats) return;
			}
	}
  
  Iterator<ElementType> iterator -> _entries.iterator;
}