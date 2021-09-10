package vm.values;

import base.ElException;
import base.ElException.Cannot;
import base.ElException.CannotGet;
import base.ElException.CannotSet;
import java.util.LinkedList;
import vm.VMBase;

public abstract class VMValue extends VMBase {
  public abstract VMValue create();
  
  public long i64Get() throws ElException {
    throw new CannotGet("i64", this);
  }
  
  public void i64Set(long value) throws ElException {
    throw new CannotSet("i64", this);
  }
  
  public double f64Get() throws ElException {
    throw new CannotGet("f64", this);
  }
  
  public void f64Set(double value) throws ElException {
    throw new CannotSet("f64", this);
  }
  
  public String stringGet() throws ElException {
    throw new CannotGet("String", this);
  }
  
  public void stringSet(String value) throws ElException {
    throw new CannotSet("String", this);
  }

  public long i64Get(int index) throws ElException {
    throw new CannotSet("i64 at index", this);
  }

  public void i64Set(int index, long value) throws ElException {
    throw new CannotSet("i64 at index", this);
  }
  
  public LinkedList<VMValue> listGet() throws ElException {
    throw new CannotGet("List", this);
  }

  public VMValue getField(int fieldIndex) throws ElException {
    throw new CannotGet("field", this);
  }

  public void setField(int fieldIndex, VMValue value) throws ElException {
    throw new CannotSet("field", this);
  }

  public void increment() throws ElException {
    throw new Cannot("increment", this);
  }

  public VMValue getIterator() throws ElException {
    throw new CannotGet("iterator", this);
  }
  
  public boolean hasNext() throws ElException {
    throw new CannotGet("next", this);
  }
  
  public long i64Next() throws ElException {
    throw new CannotGet("next I64", this);
  }
  
  public VMValue valueNext() throws ElException {
    throw new CannotGet("next value", this);
  }
}
