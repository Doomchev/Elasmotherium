package ast;

public class I32Value extends Value {
  public int value;

  public I32Value(int value) {
    this.value = value;
  }
  
  @Override
  public ID getID() {
    return ID.i32ID;
  }

  @Override
  public Entity getType() {
    return ClassEntity.i32Class;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
