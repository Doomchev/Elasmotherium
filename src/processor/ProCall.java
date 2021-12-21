package processor;

import processor.parameter.ProParameter;
import ast.Entity;
import ast.ID;
import exception.ElException;
import exception.EntityException;

public class ProCall extends ProCommand {
  private final ProParameter callObject, parameter;
  private final ID method;
  
  public ProCall(String object, String method, String parameter)
      throws ElException {
    super();
    this.callObject = ProParameter.get(object);
    this.method = ID.get(method);
    this.parameter = parameter.isEmpty() ? null : ProParameter.get(parameter);
  }
  
  @Override
  public void execute() throws ElException, EntityException {
    Entity newCurrent = callObject.getValue().resolve();
    Entity callParam = parameter == null ? null : parameter.getValue();
    if(log) {
      log(callObject + "(" + newCurrent.toString() + ")."
          + method + "(" + callParam + ")");
      subIndent.append("| ");
    }
    currentProcessor.call(newCurrent, method, callParam);
    if(log) {
      subIndent.delete(0, 2);
    }
  }
}
