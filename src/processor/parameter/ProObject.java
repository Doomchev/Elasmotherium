package processor.parameter;

import ast.exception.ElException;
import vm.VMFieldCommand;

public class ProObject extends ProParameter {
  static ProObject instance = new ProObject();
  
  private ProObject() {}

  @Override
  public int getIndex() throws ElException {
    return VMFieldCommand.OBJECT;
  }

  @Override
  public String toString() {
    return "param";
  }
}