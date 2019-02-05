public class Lib {
  static class MultiDimensionalArray<E> {
    E[] values_;
    int[] sizes_;
    MultiDimensionalArray(E[] array, int... sizes) {
      values_ = array;
      sizes_ = sizes;
    }
    E getAtIndex(int... index) {
      int i = 0;
      for(int n = index.length; n >= 0; n--) {
        i = i * sizes_[n] + index[n];
      }
      return values_[i];
    }
  }
}
