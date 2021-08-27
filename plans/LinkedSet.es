class LinkedSet<ElementType; Question noRepeats = yes> extends Set<ElementType> {
	final LinkedList<ElementType> _elements = LinkedList();
	
	Int size -> _elements.size;
	
	clear() _elements.clear();
	
	Question contains(ElementType element) -> _elements.contains(element);
	
	add(ElementType element) {
		if(noRepeats) remove(element);
		_elements.addFirst(element);
	}
	
	remove(ElementType element) {
		forEach(each e in _elements)
			if(e == element) {
				removeItem;
				if(noRepeats) return;
			}
	}
  
  Iterator<ElementType> iterator -> _entries.iterator;
}