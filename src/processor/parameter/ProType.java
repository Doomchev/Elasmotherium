package processor.parameter;

import ast.Entity;
import exception.ElException;
import processor.ProBase;

public class ProType extends ProParameter {
  static ProType instance = new ProType();
  
  private ProType() {}
  
  @Override
  public Entity getValue() throws ElException {
    return ProBase.currentType;
  }

  @Override
  public String toString() {
    return "param";
  }
}