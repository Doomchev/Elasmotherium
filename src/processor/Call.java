package processor;

import ast.Function;
import ast.FunctionCall;
import base.ElException;

public class Call extends ProCommand {
  static final Call instance = new Call();

  @Override
  ProCommand create(String param) throws ElException {
    return new Call();
  }
  
  @Override
  void execute() throws ElException {
    try {
      Function function = (Function) current;
      if(log) println("Resolving function call " + toString());
      function.append();
    } catch(ClassCastException ex) {
      try {
        ((FunctionCall) current).resolveAll();
      } catch(ClassCastException ex2) {
        throw new ElException("Cannot call ", current);
      }
    }
  }
}
