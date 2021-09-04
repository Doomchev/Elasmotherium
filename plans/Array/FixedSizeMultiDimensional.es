class Array<ElementType, Array<Int> dimensions; AnyNumber IndexType> extends Array<ElementType, __size(dimensions); IndexType> {
  static Int __size(Array<Int> dimensions) {
    _size = 1;
    for(i from 0 until dimensions.size) _size *= dimensions[i];
    return _size;
  }
  
  IndexType size(Int dimension) {
    assert(0 <= dimension < dimensions.size);
    return dimensions[index];
  }
  
  Int _index(Array<IndexType> index) {
    assert(index.size == dimensions.size);
    j = 1;
    for(i from 0 until dimensions.size) {
      assert(0 <= index[i] < dimensions[i]);
      j = j * dimensions[i] + index[i];
    }
    return j;
  }
  
  ElementType getAtIndex(Array<IndexType> index) -> this[_index[index]];
  
  setAtIndex(Array<IndexType> index, ElementType value) this[_index[index]] = value;		
}