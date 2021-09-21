package processor.parameter;

import ast.Entity;
import base.ElException;
import processor.ProBase;

public class ProParam extends ProParameter {
  static ProParam instance = new ProParam();
  
  private ProParam() {}
  
  @Override
  public Entity getValue() throws ElException {
    return ProBase.currentParam;
  }

  @Override
  public String toString() {
    return "param";
  }
}