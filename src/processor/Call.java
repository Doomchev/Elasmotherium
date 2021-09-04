package processor;

import ast.Entity;
import ast.Function;
import ast.FunctionCall;
import base.ElException;

public class Call extends ProCommand {
  static final Call instance = new Call(null);

  private final ProParameter parameter;

  public Call(ProParameter parameter) {
    this.parameter = parameter;
  }
  
  @Override
  ProCommand create(String param) throws ElException {
    return new Call(ProParameter.get(param));
  }
  
  @Override
  void execute() throws ElException {
    Entity value = parameter.getValue();
    try {
      Function function = (Function) value;
      if(log) println("Resolving function call " + toString());
      function.append();
    } catch(ClassCastException ex) {
      try {
        ((FunctionCall) value).resolveAll();
      } catch(ClassCastException ex2) {
        throw new ElException("Cannot call ", current);
      }
    }
  }
}
