class Set<ElementType, AnyInt IndexType = Int> {
  IndexType size();
	Question isEmpty();
	clear();
	add(ElementType element);
	remove(ElementType element);
	ElementType getAtIndex(ElementType element);
}

Question.from(Set set) -> !set.isEmpty;