package vm.values;

import exception.ElException;
import java.util.Arrays;

public class I64ArrayValue extends VMValue {
  public static class I64ArrayIterator extends VMValue {
    private final long[] array;
    private int pos = 0;

    public I64ArrayIterator(long[] array) {
      this.array = array;
    }

    @Override
    public VMValue create() {
      return new I64ArrayIterator(array);
    }

    @Override
    public boolean hasNext() {
      return pos < array.length;
    }

    @Override
    public long i64Next() throws ElException {
      pos++;
      return array[pos - 1];
    }
  }
  
  private final long[] array;

  public I64ArrayValue(int size) {
    this.array = new long[size];
  }

  @Override
  public VMValue create() {
    return new I64ArrayValue(array.length);
  }

  @Override
  public long i64Get(int index) throws ElException {
    return array[index];
  }

  @Override
  public void i64Set(int index, long value) throws ElException {
    array[index] = value;
  }

  @Override
  public VMValue getIterator() throws ElException {
    return new I64ArrayIterator(array);
  }

  @Override
  public String toString() {
    return "Int" + Arrays.toString(array);
  }
}
