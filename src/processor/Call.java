package processor;

import ast.Entity;
import ast.function.StaticFunction;
import ast.function.FunctionCall;
import base.ElException;
import base.ElException.Cannot;

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
      StaticFunction function = (StaticFunction) value;
      if(log) println("Resolving function call " + toString());
      function.append(currentCall);
    } catch(ClassCastException ex) {
      FunctionCall call;
      try {
        call = (FunctionCall) value;
      } catch(ClassCastException ex2) {
        throw new Cannot("call", value);
      }
      call.process();
    }
  }
}