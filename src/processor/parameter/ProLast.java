package processor.parameter;

import ast.exception.ElException;
import vm.VMFieldCommand;

public class ProLast extends ProParameter {
  static ProLast instance = new ProLast();
  
  private ProLast() {}

  @Override
  public int getIndex() throws ElException {
    return VMFieldCommand.LAST;
  }

  @Override
  public String toString() {
    return "param";
  }
}