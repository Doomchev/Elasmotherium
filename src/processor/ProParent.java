package processor;

import ast.Entity;
import base.ElException;

public class ProParent extends ProParameter {
  static ProParent instance = new ProParent();
  
  private ProParent() {}
  
  @Override
  public Entity getValue() throws ElException {
    return ProBase.targetClass;
  }

  @Override
  public String toString() {
    return "parent";
  }
}