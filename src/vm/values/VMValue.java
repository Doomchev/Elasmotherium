package vm.values;

import base.ElException;
import java.util.LinkedList;
import vm.VMBase;

public abstract class VMValue extends VMBase {
  public abstract VMValue create();
  
  public long i64Get() throws ElException {
    throw new ElException("Cannot get i64 from ", this);
  }
  
  public void i64Set(long value) throws ElException {
    throw new ElException("Cannot set i64 of ", this);
  }
  
  public double f64Get() throws ElException {
    throw new ElException("Cannot get f64 from ", this);
  }
  
  public void f64Set(double value) throws ElException {
    throw new ElException("Cannot set f64 of ", this);
  }
  
  public String stringGet() throws ElException {
    throw new ElException("Cannot get String from ", this);
  }
  
  public void stringSet(String value) throws ElException {
    throw new ElException("Cannot set String of ", this);
  }

  public long i64Get(int index) throws ElException {
    throw new ElException("Cannot set i64 at index of ", this);
  }

  public void i64Set(int index, long value) throws ElException {
    throw new ElException("Cannot get i64 at index of ", this);
  }
  
  public LinkedList<VMValue> listGet() throws ElException {
    throw new ElException("Cannot get List of ", this);
  }

  public VMValue getField(int fieldIndex) throws ElException {
    throw new ElException("Cannot set field of ", this);
  }

  public void setField(int fieldIndex, VMValue value) throws ElException {
    throw new ElException("Cannot set field of ", this);
  }

  public void increment() throws ElException {
    throw new ElException("Cannot increment ", this);
  }

  public VMValue getIterator() throws ElException {
    throw new ElException("Cannot get iterator from ", this);
  }
  
  public boolean hasNext() throws ElException {
    throw new ElException(this, " is not an iteraror.");
  }
  
  public long i64Next() throws ElException {
    throw new ElException("Cannot get next I64 from ", this);
  }
  
  public VMValue valueNext() throws ElException {
    throw new ElException("Cannot get next value from ", this);
  }
}
