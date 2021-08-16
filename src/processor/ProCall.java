package processor;

import ast.ClassEntity;
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
    ClassEntity type
        = parameter == null ? null : parameter.getValue().getType();
    if(log) {
      log(newCurrent.toString() + "." + method + "(" + type + ")");
      subIndent += "| ";
    }
    currentProcessor.call(newCurrent, method, type);
    if(log) subIndent = subIndent.substring(2);
  }
}
