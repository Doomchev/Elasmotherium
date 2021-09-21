Array<ElementType, IndexType>.shuffle()
  for(IndexType i = 0 ..= size - 2)
    swap(this[i], this[IndexType[i + 1 ..< size]]);