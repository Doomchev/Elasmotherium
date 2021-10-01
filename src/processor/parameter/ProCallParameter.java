package processor.parameter;

import ast.Entity;
import ast.function.FunctionCall;
import ast.exception.ElException;
import ast.exception.ElException.MethodException;
import ast.exception.NotFound;
import processor.ProBase;

public class ProCallParameter extends ProParameter {
  private final int index;

  public ProCallParameter(String index) throws ElException {
    super();
    try {
      this.index = Integer.parseInt(index);
    } catch(NumberFormatException ex) {
      throw new MethodException("ProCallParameter", "create"
          , "Wrong parameter number format.");
    }
  }

  @Override
  public Entity getValue() throws ElException {
    try {
      return ((FunctionCall) ProBase.currentObject).getParameter(index);
    } catch (NotFound ex) {
      throw new ElException.MethodException("ProCallParameter", "getValue"
          , ex.message);
    }
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
