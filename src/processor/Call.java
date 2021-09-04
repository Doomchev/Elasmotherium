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
    Function function = current.toFunction();
    if(function == null) {
      FunctionCall call = current.toCall();
      if(call == null) throw new ElException("Cannot call ", current);
      call.resolveAll();
    } else {
      if(log) println("Resolving function call " + toString());
      function.append();
    }
  }
}
