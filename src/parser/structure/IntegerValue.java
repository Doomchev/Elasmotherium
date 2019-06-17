package parser.structure;

public class IntegerValue extends Value {
  public int value;

  public IntegerValue(int value) {
    this.value = value;
  }
  
  @Override
  public ID getID() {
    return integerID;
  }

  @Override
  public Entity getType() {
    return ClassEntity.intClass;
  }
  
  @Override
  public Entity setTypes(Scope parentScope) {
    return ClassEntity.intClass;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
