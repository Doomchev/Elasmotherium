package ast;

import vm.I64Push;

public class I64Value extends Value {
  public long value;

  public I64Value(long value) {
    this.value = value;
  }
  
  @Override
  public ID getID() {
    return ID.i64ID;
  }

  @Override
  public Entity getType() {
    return ClassEntity.i64Class;
  }
  
  @Override
  public long i64Get() {
    return value;
  }
  
  @Override
  public void i64Set(long value) {
    this.value = value;
  }

  @Override
  public void toByteCode() {
    addCommand(new I64Push(value));
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}