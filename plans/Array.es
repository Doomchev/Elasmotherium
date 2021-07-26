class Array<ElementType; AnyNumber IndexType> {
	static final IndexType notFound = -1;
	
	this(IndexType size) assert(size >= 0);
	
	this(IndexType size, ElementType fill) {
		this(size);
		for(IndexType i .. size) this[i] = fill;
	}
	
	IndexType size();
	
	ElementType at(IndexType index) assert(0 <= index < size);
	
	at(IndexType index, ElementType value) assert(0 <= index < size);
	
	This part(Int from, Int quantity) {
		assert(quantity >= 0 && 0 <= from <= size - quantity);
		This string = This(quantity);
		for(Int i = 0 .. quantity) string[i] = this[i + from];
		return string;
	}
	
	copy(Int from, Int quantity, This destination, Int destinationFrom) {
		assert(0 <= from < size - quantity && 0 <= destinationFrom < destination.size - quantity);
		for(Int i = 0 .. quantity) destination[i + destinationFrom] this[i + from];
	}
	
  Bool isEqualTo(This string) {
    if(size != string.size) return no;
    for(Int i = 0 .. size) if(this[i] != string[i]) return no;
    return yes;
  }
	
	range(IndexType from, IndexType until, This value) {
		if(from < 0) from = size + from;
		if(until < 0) until = size + until;
		assert(0 <= from <= until <= size && value.size == until - from);
		value.copy(0, value.size, this, from);
	}
	
	This range(IndexType from, IndexType until) {
		if(from < 0) from = size + from;
		if(until < 0) until = size + until;
		assert(0 <= from <= until <= size);
		return part(from, until - from);
	}
	
	This rangeFrom(IndexType from) {
		if(from < 0) from = size + from;
		assert(0 <= from <= size);
		return part(from, size - from);
	}
	
	This rangeUntil(IndexType until) {
		if(until < 0) until = size + until;
		assert(0 <= until <= size);
		return part(0, until);
	}
	
	IndexType indexOf(ElementType element) {
		for(IndexType i = 0 .. size) if(this[i] == element) return i;
		return notFound;
	}
	
	Bool contains(CharType char) {
		for(IndexType i = 0 .. size) if(this[i] == char) return yes;
		return no;
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
		this(Array<Int, dimensionsQuantity> dimensions) {
			Int _size = 1;
			for(Int i = 0 .. dimensionsQuantity) _size *= dimensions[i];
			this(_size);
			_dimensions = dimensions;
		}
		Int size.at(IndexType index) {
			assert(0 <= index < dimensionsQuantity);
			return _dimensions[index];
		}
		Int _index(Array<IndexType> index) {
			assert(index.size == dimensionsQuantity);
			for(Int i .. dimensionsQuantity) assert(0 <= index[i] < _dimensions[i]);
			Int j = 0;
			for(Int i .. dimensionsQuantity) j = j * _dimensions[i] + index[i];
			return j;
		}
		ElementType at(Array<IndexType> index) -> this[_index[index]];
		at(Array<IndexType> index, ElementType value) this[_index[index]] = value;	
	}
}

section FixedSizeMultiDim {
	class Array<Int dimensionsQuantity, ElementType, Array<Int, dimensionsQuantity> fixedDimensions; AnyNumber IndexType> extends Array<ElementType, size; IndexType> {
		Int size() {
			Int _size = 1;
			for(Int i = 0 ..< dimensionsQuantity) _size *= fixedDimensions[i];
			return _size;
		}
		Int size.at(IndexType index) {
			assert(0 <= index < dimensionsQuantity);
			return fixedDimensions[index];
		}
		Int _index(Array<IndexType> index) {
			assert(index.size == dimensionsQuantity);
			for(Int i ..< dimensionsQuantity) assert(0 <= index[i] < fixedDimensions[i]);
			Int j = 0;
			for(Int i ..< dimensionsQuantity) j = j * fixedDimensions[i] + index[i];
			return j;
		}
		ElementType at(Array<IndexType> index) -> this[_index[index]];
		at(Array<IndexType> index, ElementType value) this[_index[index]] = value;		
	}
}