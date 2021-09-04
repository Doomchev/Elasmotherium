class Array<Int dimensionsQuantity, ElementType; AnyNumber IndexType> extends Array<ElementType; IndexType> {
  final Array<Int, dimensionsQuantity> _dimensions;
  
  create(Array<Int, dimensionsQuantity> dimensions) {
    _size = 1;
    for(i from 0 until dimensionsQuantity) _size *= dimensions[i];
    create(size);
    _dimensions = dimensions;
  }
  
  create(Array<Int, dimensionsQuantity> dimensions, ElementType initialValue) {
    create(dimensions);
    fill(initialValue);
  }
  
  Int size(IndexType index) {
    assert(0 <= index < dimensionsQuantity);
    return _dimensions[index];
  }
  
  Int _index(Array<IndexType> index) {
    assert(index.size == dimensionsQuantity);
    j = 1;
    for(i from 0 until dimensionsQuantity) {
      assert(0 <= index[i] < _dimensions[i]);
      j = j * _dimensions[i] + index[i];
    }
    return j;
  }
  
  ElementType getAtIndex(Array<IndexType> index) -> this[_index[index]];
  
  setAtIndex(Array<IndexType> index, ElementType value) this[_index[index]] = value;	
}