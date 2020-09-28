package ast;

public class F64Value extends Value {
  public double value;

  public F64Value(double value) {
    this.value = value;
  }
  
  @Override
  public ID getID() {
    return f64ID;
  }

  @Override
  public ClassEntity getType() {
    return ClassEntity.f64Class;
  }
  
  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
