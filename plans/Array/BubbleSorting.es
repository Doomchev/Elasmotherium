Array<ElementType, IndexType>.sort() {
  for(IndexType i from 0 until size - 1) {
    IndexType minIndex = 0;
    ElementType minElement = this[i];
    for(IndexType j from i + 1 until size)
      if(minElement.compare(this[j]) > 0 {
        minElement = this[j];
        minIndex = j;
      }
    }
    swap(this[i], this[minIndex]]);
  }
}