native List<ValueType> {
  get Int size
  get ValueType first
  get ValueType last
  ValueType get(Int n)
  set(Int n, ValueType element)
  addFirst(ValueType element)
  addLast(ValueType element)
  clear()
  Boolean contains(ValueType element)
  Int indexOf(ValueType element)
  Int lastIndexOf(ValueType element)
  remove(ValueType element)
  removeAtIndex(Int n)
  removeFirst()
  removeLast()
}
