class Array<ElementType; AnyNumber IndexType> {
	static final notFound = IndexType.min < 0 ? -1 : IndexType.max;

	create(IndexType size) assert(0 <= size <= IndexType.max);
	
	create(IndexType size, ElementType initialValue) {
		create(size);
		fill(initialValue);
	}
  
  fill(ElementType value) for(IndexType i from 0 until size) this[i] = value;
	
	IndexType size();
	
	ElementType getAtIndex(IndexType index) assert(0 <= index < size);
	
	setAtIndex(IndexType index, ElementType value) assert(0 <= index < size);
	
	ThisType part(Int start, Int quantity) {
		assert(quantity >= 0 && 0 <= start <= size - quantity);
		ThisType array = ThisType(quantity);
		for(Int i from 0 until quantity) array[i] = this[i + start];
		return array;
	}
	
	copy(IndexType start, IndexType quantity, ThisType destination, IndexType destinationFrom) {
		assert(0 <= start < size - quantity && 0 <= destinationFrom < destination.size - quantity);
		for(i from 0 until quantity) destination[i + destinationFrom] this[i + start];
	}
	
  Question equals(Array array) {
    if(size != array.size) return no;
    for(i from 0 until size) if(this[i] != array[i]) return no;
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
		for(i from 0 until size) if(this[i] == element) return i;
		return IndexType.min < 0 ? -1 : IndexType.max;
	}
	
	Question contains(ElementType element) {
		for(i from 0 until size) if(this[i] == element) return yes;
		return no;
	}
	
	ThisType expand(IndexType newSize) {
		assert(newSize >= size);
		newArray = new ThisType(newSize);
		copy(0, size, newArray, 0);
		return newArray;
	}
}

section FixedSize {
	class Array<ElementType, Int fixedSize; IndexType> extends Array<ElementType; IndexType> {
		IndexType size -> fixedSize;
	}
}

section MultiDim {
	class Array<Int dimensionsQuantity, ElementType; AnyNumber IndexType> extends Array<ElementType; IndexType> {
		final Array<Int, dimensionsQuantity> _dimensions;
		
		create(Array<Int, dimensionsQuantity> dimensions) {
			_size = 1;
			for(i from 0 until dimensionsQuantity) _size *= dimensions[i];
			create(_size);
			_dimensions = dimensions;
		}
    
		create(Array<Int, dimensionsQuantity> dimensions, ElementType initialValue) {
      create(dimensions);
      fill(initialValue);
    }
		
		Int size.at(IndexType index) {
			assert(0 <= index < dimensionsQuantity);
			return _dimensions[index];
		}
		
		Int _index(Array<IndexType> index) {
			assert(index.size == dimensionsQuantity);
			for(i from 0 until dimensionsQuantity) assert(0 <= index[i] < _dimensions[i]);
			Int j = 0;
			for(i from 0 until dimensionsQuantity) j = j * _dimensions[i] + index[i];
			return j;
		}
		
		ElementType getAtIndex(Array<IndexType> index) -> this[_index[index]];
		
		setAtIndex(Array<IndexType> index, ElementType value) this[_index[index]] = value;	
	}
}

section FixedSizeMultiDim {
	class Array<Int dimensionsQuantity, ElementType, Array<Int, dimensionsQuantity> fixedDimensions; AnyNumber IndexType> extends Array<ElementType, size; IndexType> {
		Int size() {
			_size = 1;
			for(i from 0 until dimensionsQuantity) _size *= fixedDimensions[i];
			return _size;
		}
		
		Int size(IndexType index) {
			assert(0 <= index < dimensionsQuantity);
			return fixedDimensions[index];
		}
		
		Int _index(Array<IndexType> index) {
			assert(index.size == dimensionsQuantity);
			for(i from 0 until dimensionsQuantity) assert(0 <= index[i] < fixedDimensions[i]);
			j = 0;
			for(i from 0 until dimensionsQuantity) j = j * fixedDimensions[i] + index[i];
			return j;
		}
		
		ElementType getAtIndex(Array<IndexType> index) -> this[_index[index]];
		
		setAtIndex(Array<IndexType> index, ElementType value) this[_index[index]] = value;		
	}
}