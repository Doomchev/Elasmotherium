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
	
	ThisType part(Int from, Int quantity) {
		assert(quantity >= 0 && 0 <= from <= size - quantity);
		ThisType array = ThisType(quantity);
		for(Int i from 0 until quantity) array[i] = this[i + from];
		return array;
	}
	
	copy(Int from, Int quantity, ThisType destination, Int destinationFrom) {
		assert(0 <= from < size - quantity && 0 <= destinationFrom < destination.size - quantity);
		for(Int i from 0 until quantity) destination[i + destinationFrom] this[i + from];
	}
	
  Question equals(Array array) {
    if(size != array.size) return no;
    for(Int i from 0 until size) if(this[i] != array[i]) return no;
    return yes;
  }
	
	range(IndexType from, IndexType until, ThisType value) {
		if(from < 0) from = size + from;
		if(until < 0) until = size + until;
		assert(0 <= from <= until <= size && value.size == until - from);
		value.copy(0, value.size, this, from);
	}
	
	ThisType range(IndexType from, IndexType until) {
		if(from < 0) from = size + from;
		if(until < 0) until = size + until;
		assert(0 <= from <= until <= size);
		return part(from, until - from);
	}
	
	ThisType rangeFrom(IndexType from) {
		if(from < 0) from = size + from;
		assert(0 <= from <= size);
		return part(from, size - from);
	}
	
	ThisType rangeUntil(IndexType until) {
		if(until < 0) until = size + until;
		assert(0 <= until <= size);
		return part(0, until);
	}
	
	IndexType indexOf(ElementType element) {
		for(IndexType i from 0 until size) if(this[i] == element) return i;
		return IndexType.min < 0 ? -1 : IndexType.max;
	}
	
	Question contains(ElementType element) {
		for(IndexType i from 0 until size) if(this[i] == element) return yes;
		return no;
	}
	
	ThisType expand(IndexType newSize) {
		assert(newSize >= size);
		ThisType newArray = new ThisType(newSize);
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
			Int _size = 1;
			for(Int i from 0 until dimensionsQuantity) _size *= dimensions[i];
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
			for(Int i from 0 until dimensionsQuantity) assert(0 <= index[i] < _dimensions[i]);
			Int j = 0;
			for(Int i from 0 until dimensionsQuantity) j = j * _dimensions[i] + index[i];
			return j;
		}
		
		ElementType getAtIndex(Array<IndexType> index) -> this[_index[index]];
		
		setAtIndex(Array<IndexType> index, ElementType value) this[_index[index]] = value;	
	}
}

section FixedSizeMultiDim {
	class Array<Int dimensionsQuantity, ElementType, Array<Int, dimensionsQuantity> fixedDimensions; AnyNumber IndexType> extends Array<ElementType, size; IndexType> {
		Int size() {
			Int _size = 1;
			for(Int i from 0 until dimensionsQuantity) _size *= fixedDimensions[i];
			return _size;
		}
		
		Int size.at(IndexType index) {
			assert(0 <= index < dimensionsQuantity);
			return fixedDimensions[index];
		}
		
		Int _index(Array<IndexType> index) {
			assert(index.size == dimensionsQuantity);
			for(Int i from 0 until dimensionsQuantity) assert(0 <= index[i] < fixedDimensions[i]);
			Int j = 0;
			for(Int i from 0 until dimensionsQuantity) j = j * fixedDimensions[i] + index[i];
			return j;
		}
		
		ElementType getAtIndex(Array<IndexType> index) -> this[_index[index]];
		
		setAtIndex(Array<IndexType> index, ElementType value) this[_index[index]] = value;		
	}
}