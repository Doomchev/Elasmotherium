package ast;

public class I8Value extends Value {
  public byte value;

  public I8Value(byte value) {
    this.value = value;
  }
  
  @Override
  public ID getID() {
    return ID.i8ID;
  }

  @Override
  public Entity getType() {
    return ClassEntity.i8Class;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
