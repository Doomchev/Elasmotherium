class LinkedSet<ElementType; Question unique = yes> extends Set<ElementType> {
	final LinkedList<ElementType> _elements = LinkedList();
	
	Int size -> _elements.size;
	
	clear() _elements.clear();
	
	Question contains(ElementType element) -> _elements.contains(element);
	
	add(ElementType element) {
		if(unique) remove(element);
		_elements.addFirst(element);
	}
	
	remove(ElementType element) {
		forEach(ElementType e in _elements)
			if(e == element) {
				removeItem;
				if(unique) return;
			}
	}
}