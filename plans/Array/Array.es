class Array<ElementType; AnyNumber IndexType> {
	static final notFound = IndexType.min < 0 ? -1 : IndexType.max;

	create(IndexType size) assert(0 <= size <= IndexType.max);
	
	create(IndexType size, ElementType initialValue) {
		create(size);
		fill(initialValue);
	}

  from(List<ElementType> list) {
    assert(list.size < IndexType.max);
    array = new ThisType(list.size);
    IndexType index = 0;
    for(element: list) {
      array[index] = element;
      index++;
    }
    return array;
  }
  
  fill(ElementType value) for(IndexType i = 0 ..< size) this[i] = value;
	
	IndexType size();
	
	ElementType getAtIndex(IndexType index) assert(0 <= index < size);
	
	setAtIndex(IndexType index, ElementType value) assert(0 <= index < size);
	
	ThisType part(Int start, Int quantity) {
		assert(quantity >= 0 && 0 <= start <= size - quantity);
		array = ThisType(quantity);
		for(i = 0 ..< quantity) array[i] = this[i + start];
		return array;
	}
	
	copy(IndexType start, IndexType quantity, Array<ElementType> destination, IndexType destinationStart) {
		assert(0 <= start < size - quantity && 0 <= destinationStart < destination.size - quantity);
		for(i = 0 ..< quantity) destination[i + destinationStart] = this[i + start];
	}
	
  Question equals(Array array) {
    if(size != array.size) return no;
    for(i = 0 ..< size) if(this[i] != array[i]) return no;
    return yes;
  }
	
	range(IndexType start, IndexType end, ThisType value) {
		if(start < 0) start = size + start;
		if(end < 0) end = size + end;
		assert(0 <= start <= end <= size && value.size == end - start);
		value.copy(0, value.size, this, start);
	}
	
	ThisType range(IndexType start, IndexType end) {
		if(start < 0) start = size + start;
		if(end < 0) end = size + end;
		assert(0 <= start <= end <= size);
		return part(start, end - start);
	}
	
	ThisType rangeFrom(IndexType start) {
		if(start < 0) start = size + start;
		assert(0 <= start <= size);
		return part(start, size - start);
	}
	
	ThisType rangeUntil(IndexType end) {
		if(end < 0) end = size + end;
		assert(0 <= end <= size);
		return part(0, end);
	}
	
	IndexType indexOf(ElementType element) {
		for(i = 0 ..< size) if(this[i] == element) return i;
		return IndexType.min < 0 ? -1 : IndexType.max;
	}
	
	Question contains(ElementType element) {
		for(i = 0 ..< size) if(this[i] == element) return yes;
		return no;
	}
	
	ThisType expand(IndexType newSize) {
		assert(newSize >= size);
		newArray = new ThisType(newSize);
		copy(0, size, newArray, 0);
		return newArray;
	}
}