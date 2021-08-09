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
    this.parameter = ProParameter.get(parameter);
  }
  
  @Override
  void execute() throws ElException {
    if(log) {
      log(object.getValue().toString() + "." + method + "(" + parameter + ")");
      subIndent += "| ";
    }
    Entity oldParent = parent, oldCurrent = current;
    parent = parameter.getValue();
    current = object.getValue();
    currentProcessor.call(current, method);
    parent = oldParent;
    current = oldCurrent;
    if(log) subIndent = subIndent.substring(2);
  }
}
