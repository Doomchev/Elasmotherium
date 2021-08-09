package vm;

import base.ElException;

public class VMValue {
  public long i64Get() throws ElException {
    throw new ElException("Cannot get i64 from ", this);
  }
  
  public void i64Set(long value) throws ElException {
    throw new ElException("Cannot set i64 of ", this);
  }
  
  public String stringGet() throws ElException {
    throw new ElException("Cannot get String from ", this);
  }
  
  public void stringSet(String value) throws ElException {
    throw new ElException("Cannot set String of ", this);
  }

  public void increment() throws ElException {
    throw new ElException("Cannot increment ", this);
  }
}
