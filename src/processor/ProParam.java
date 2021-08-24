package processor;

import ast.Entity;
import base.ElException;

public class ProParam extends ProParameter {
  static ProParam instance = new ProParam();
  
  private ProParam() {}
  
  @Override
  public Entity getValue() throws ElException {
    return ProBase.param;
  }

  @Override
  public String toString() {
    return "param";
  }
}