package processor;

import ast.Entity;
import ast.function.FunctionCall;
import base.ElException;

public class ProCallParameter extends ProParameter {
  private final int index;

  public ProCallParameter(String index) throws ElException {
    super();
    try {
      this.index = Integer.parseInt(index);
    } catch(NumberFormatException ex) {
      throw new ElException("Wrong parameter number format.");
    }
  }

  @Override
  public Entity getValue() throws ElException {
    return ((FunctionCall) ProBase.currentObject).getParameter(index);
  }

  @Override
  public void setValue(Entity value) {
    ((FunctionCall) ProBase.currentObject).setParameter(index, value);
  }

  @Override
  public String toString() {
    return "v" + index;
  }
}
