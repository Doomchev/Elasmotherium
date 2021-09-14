package processor.parameter;

import ast.Entity;
import base.ElException;

public class ProObject extends ProParameter {
  static ProObject instance = new ProObject();
  
  private ProObject() {}
  
  @Override
  public Entity getValue() throws ElException {
    return object;
  }

  @Override
  public String toString() {
    return "object";
  }
}
