class Set<ElementType; AnyInt IndexType = Int> {
  create() -> Set.LinkedSet<ElementType, IndexType>();
  
  IndexType size();
	Question isEmpty();
	clear();
	add(ElementType element);
	remove(ElementType element);
	ElementType getAtIndex(ElementType element);
}

Question.from(Set set) -> !set.isEmpty;