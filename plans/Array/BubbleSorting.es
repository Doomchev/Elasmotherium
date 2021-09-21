Array<ElementType, IndexType>.sort() {
  for(IndexType i = 0 ..< size - 1) {
    IndexType minIndex = 0;
    ElementType minElement = this[i];
    for(IndexType j = i + 1 ..< size)
      if(minElement.compare(this[j]) > 0 {
        minElement = this[j];
        minIndex = j;
      }
    }
    swap(this[i], this[minIndex]]);
  }
}