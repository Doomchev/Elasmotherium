package processor;

import ast.Entity;
import base.ElException;

public class ProThis extends ProParameter {
  static ProThis instance = new ProThis();
  
  private ProThis() {}
  
  @Override
  Entity getValue() throws ElException {
    return current;
  }

  @Override
  public String toString() {
    return "this";
  }
}
