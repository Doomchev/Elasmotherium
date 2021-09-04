import Type.Any;

swap<Type>(Reference<Type> a, Reference<Type> b) {
  Type c = a.value;
  b.value = a.value;
  a.value = c;
}