package vm.values;

import exception.ElException;
import exception.ElException.Cannot;
import exception.ElException.CannotGet;
import exception.ElException.CannotSet;
import vm.VMBase;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

public abstract class VMValue extends VMBase {
  public abstract VMValue create();
  
  // i64
  
  public long i64Get() throws ElException {
    throw new CannotGet("i64", this);
  }
  
  public void i64Set(long value) throws ElException {
    throw new CannotSet("i64", this);
  }

  public long i64Get(int index) throws ElException {
    throw new CannotGet("i64 at index", this);
  }

  public void i64Set(int index, long value) throws ElException {
    throw new CannotSet("i64 at index", this);
  }
  
  public long i64Next() throws ElException {
    throw new CannotGet("next I64", this);
  }
  
  // f64
  
  public double f64Get() throws ElException {
    throw new CannotGet("f64", this);
  }
  
  public void f64Set(double value) throws ElException {
    throw new CannotSet("f64", this);
  }
  
  // string
  
  public String stringGet() throws ElException {
    throw new CannotGet("String", this);
  }
  
  public void stringSet(String value) throws ElException {
    throw new CannotSet("String", this);
  }
  
  // value

  public VMValue objectGet() throws ElException {
    throw new CannotGet("i64", this);
  }

  public VMValue valueGet(int index) throws ElException {
    throw new CannotGet("value at index", this);
  }

  public void valueSet(int index, VMValue value) throws ElException {
    throw new CannotSet("value at index", this);
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
  
  public VMValue valueNext() throws ElException {
    throw new CannotGet("next value", this);
  }
  
  public BufferedImage getImage() throws ElException {
    throw new CannotGet("image", this);
  }
}
