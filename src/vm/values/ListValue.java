package vm.values;

import base.ElException;
import java.util.Iterator;
import java.util.LinkedList;

public class ListValue extends VMValue {
  public static class ListIterator extends VMValue {
    private final Iterator<VMValue> iterator;

    public ListIterator(Iterator<VMValue> iterator) {
      this.iterator = iterator;
    }

    @Override
    public VMValue create() {
      return new ListIterator(iterator);
    }

    @Override
    public boolean hasNext() {
      return iterator.hasNext();
    }

    @Override
    public long i64Next() throws ElException {
      return iterator.next().i64Get();
    }

    @Override
    public VMValue valueNext() {
      return iterator.next();
    }

    @Override
    public String toString() {
      return "iterator";
    }
  }
  
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
  public VMValue getIterator() throws ElException {
    return new ListIterator(list.iterator());
  }

  @Override
  public String toString() {
    return "[" + listToString(list) + "]";
  }
}
