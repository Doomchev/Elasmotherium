package parser.structure;

public class DecimalValue extends Value {
  public float value;

  public DecimalValue(float value) {
    this.value = value;
  }
  
  @Override
  public ID getID() {
    return decimalID;
  }

  @Override
  public Entity getType() {
    return ClassEntity.floatClass;
  }
  
  @Override
  public Entity setTypes(Scope parentScope) {
    return ClassEntity.floatClass;
  }
  
  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
