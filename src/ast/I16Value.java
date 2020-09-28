package ast;

public class I16Value extends Value {
  public short value;

  public I16Value(short value) {
    this.value = value;
  }
  
  @Override
  public ID getID() {
    return ID.i16ID;
  }

  @Override
  public Entity getType() {
    return ClassEntity.i16Class;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}