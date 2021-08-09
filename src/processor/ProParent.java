package processor;

import ast.Entity;
import base.ElException;

public class ProParent extends ProParameter {
  static ProParent instance = new ProParent();
  
  private ProParent() {}
  
  @Override
  Entity getValue() throws ElException {
    return parent;
  }

  @Override
  public String toString() {
    return "parent";
  }
}