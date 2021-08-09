package processor;

import ast.Entity;
import base.ElException;

public class ProVariableValue extends ProParameter {
  static ProVariableValue instance = new ProVariableValue();
  
  private ProVariableValue() {}
  
  @Override
  Entity getValue() throws ElException {
    return current.getValue();
  }

  @Override
  public String toString() {
    return "value";
  }
}
