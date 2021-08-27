class LinkedList<ElementType; AnyInt IndexType = Int, Question trackSize = yes> extends List<ElementType; IndexType> {
  static final notFound = IndexType.min < 0 ? -1 : IndexType.max;
  
  class Node {
    ElementType _element;
    Node _previous, _next;
    
    create(field._element, field._previous, field._next);
    
    remove() {
      node._previous._next = node._next;
      node._next._previous = node._previous;
    }
  }
  
  class Iterator extends Iterator<ElementType> {
    final Node _base;
    Node _node;
    
    create(field._base) _node = _base._next;
    
    Question hasNext -> _node == _base;
    
    ElementType next() {
      element = _node._element;
      _node = _node._next;
      return element;
    }
    
    remove() {
      _node.remove();
    }
  }
  
  Node _base = Node(null, base, base);

  IndexType _size;
  
	IndexType size() {
    if(trackSize) return _size;
    node = _base._next;
    IndexType size = 0;
    while(node != _base) {
      node = node._next;
      size++;
    }
    return size;
  }
  
	Question isEmpty -> base._next == _base;
  
  ElementType first() {
    assert(!isEmpty);
    return_base._next._element;
  }
  
	ElementType last() {
    assert(!isEmpty);
    return _base._previous._element;
  }
  
	clear() {
    base._next = base;
    base._previous = base;
    if(trackSize) _size = 0;
  }
	
	Question contains(ElementType element) {
    node = _base._next
    while(node != _base) {
      if(node._element == element) return yes;
      node = node._next;
    }
    return no;
	}
  
	addFirst(ElementType element) {
    next = _base._next;
    node = Node(element, _base, next);
    next._previous = node;
    _base._next = node;
    if(trackSize) _size++;
  }
  
	addLast(ElementType element) {
    previous = _base._previous;
    node = Node(element, previous, _base);
    previous._next = node;
    _base._previous = node;
    if(trackSize) _size++;
  }
	
	remove(ElementType element) {
    node = _base._next
    while(node != _base) {
      if(node._element == element) {
        node.remove();
        _size--;
        return;
      }
      node = node._next;
    }
  }
  
  removeFirst() {
    assert(!isEmpty);
    _base._next.remove();
  }
  
  removeLast() {
    assert(!isEmpty);
    _base._previous.remove();
  }
	
	IndexType indexOf(ElementType element) {
    node = _base._next
    IndexType index = 0;
    while(node != _base) {
      if(node._element == element) return index;
      node = node._next;
      index++;
    }
    return notFound;
  }
  
	ElementType getAtIndex(IndexType index) {
    if(trackSize) assert(0 <= index < _size);
    node = _base._next
    for(i from 0 until index) {
      node = node._next;
      if(!trackSize && node == base) return null;
    }
    return node._element;
  }
  
	setAtIndex(IndexType index, ElementType element) {
    if(trackSize) assert(0 <= index < _size);
    node = _base._next
    for(i from 0 until index) {
      node = node._next;
      if(!trackSize && node == base) return null;
    }
    node._element = element;
  }
  
	removeAtIndex(IndexType index) {
    if(trackSize) assert(0 <= index < _size);
    node = _base._next
    for(i from 0 until index) {
      node = node._next;
      if(!trackSize && node == base) return null;
    }
    node.remove();
    _size--;
  }
  
  Iterator iterator -> new Iterator(_base);
}

Question.from(List list) -> !list.isEmpty;