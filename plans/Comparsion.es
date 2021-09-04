enum Comparsion {
  smaller, equal, bigger;
  create<AnyNumber Type>(Type a, Type b) {
    switch(a - b) {
      case is < 0: return smaller;
      case is == 0: return equal;
      case is > 0: return bigger;
    }
  }
}