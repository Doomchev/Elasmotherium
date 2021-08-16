package processor;

import ast.Entity;
import ast.ID;
import base.ElException;

public class ProCall extends ProCommand {
  ProParameter object;
  ProParameter parameter;
  ID method;
  
  public ProCall(String object, String method, String parameter)
      throws ElException {
    super();
    this.object = ProParameter.get(object);
    this.method = ID.get(method);
    this.parameter = parameter.isEmpty() ? null : ProParameter.get(parameter);
  }
  
  @Override
  void execute() throws ElException {
    Entity newCurrent = object.getValue();
    if(log) {
      log(newCurrent.toString() + "." + method + "("
          + (parameter == null ? "" : parameter.getValue()) + ")");
      subIndent += "| ";
    }
    Entity oldParent = parent;
    if(parameter != null) parent = parameter.getValue();
    currentProcessor.call(newCurrent, method);
    parent = oldParent;
    if(log) subIndent = subIndent.substring(2);
  }
}
