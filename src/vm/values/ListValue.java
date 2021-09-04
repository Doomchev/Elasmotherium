package vm.values;

import base.ElException;
import java.util.LinkedList;

public class ListValue extends VMValue {
  private final LinkedList<VMValue> list = new LinkedList<>();

  @Override
  public VMValue create() {
    return new ListValue();
  }
  
  @Override
  public LinkedList<VMValue> listGet() {
    return list;
  }

  @Override
  public IteratorValue getIterator() throws ElException {
    return new IteratorValue(list.iterator());
  }

  @Override
  public String toString() {
    return "[" + listToString(list) + "]";
  }
}
