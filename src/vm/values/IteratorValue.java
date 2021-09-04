package vm.values;

import java.util.Iterator;

public class IteratorValue extends VMValue {
  private final Iterator<VMValue> iterator;

  public IteratorValue(Iterator<VMValue> iterator) {
    this.iterator = iterator;
  }

  @Override
  public VMValue create() {
    return new IteratorValue(null);
  }
  
  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }
  
  @Override
  public VMValue next() {
    return iterator.next();
  }

  @Override
  public String toString() {
    return "iterator";
  }
}
