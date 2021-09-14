package processor.parameter;

import ast.Entity;
import base.ElException;

public class ProVariableValue extends ProParameter {
  static ProVariableValue instance = new ProVariableValue();
  
  private ProVariableValue() {}
  
  @Override
  public Entity getValue() throws ElException {
    return currentObject.getValue();
  }

  @Override
  public String toString() {
    return "value";
  }
}
