package parser.structure;

public class F32Value extends Value {
  public float value;

  public F32Value(float value) {
    this.value = value;
  }
  
  @Override
  public ID getID() {
    return f32ID;
  }

  @Override
  public ClassEntity getType() {
    return ClassEntity.f32Class;
  }
  
  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
