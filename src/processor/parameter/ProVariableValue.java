package processor.parameter;

import ast.Entity;
import base.EntityException;

public class ProVariableValue extends ProParameter {
  static ProVariableValue instance = new ProVariableValue();
  
  private ProVariableValue() {}
  
  @Override
  public Entity getValue() throws EntityException {
    return currentObject.getValue();
  }

  @Override
  public String toString() {
    return "value";
  }
}
