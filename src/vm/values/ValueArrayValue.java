package vm.values;

import ast.exception.ElException;
import java.util.Arrays;

public class ValueArrayValue extends VMValue {
  public static class ValueArrayIterator extends VMValue {
    private final VMValue[] array;
    private int pos = 0;

    public ValueArrayIterator(VMValue[] array) {
      this.array = array;
    }

    @Override
    public VMValue create() {
      return new ValueArrayIterator(array);
    }

    @Override
    public boolean hasNext() {
      return pos < array.length;
    }

    @Override
    public VMValue valueNext() throws ElException {
      pos++;
      return array[pos - 1];
    }
  }
  
  private final VMValue[] array;

  public ValueArrayValue(int size) {
    this.array = new VMValue[size];
  }

  @Override
  public VMValue create() {
    return new ValueArrayValue(array.length);
  }

  @Override
  public VMValue valueGet(int index) throws ElException {
    return array[index];
  }

  @Override
  public void valueSet(int index, VMValue value) throws ElException {
    array[index] = value;
  }

  @Override
  public VMValue getIterator() throws ElException {
    return new ValueArrayIterator(array);
  }

  @Override
  public String toString() {
    return "Value" + Arrays.toString(array);
  }
}
