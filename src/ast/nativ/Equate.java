package ast.nativ;

import ast.FunctionCall;
import ast.NativeFunction;
import ast.Variables;

public class Equate extends NativeFunction {
  public Equate() {
    super("equate");
  }

  @Override
  public void resolveLinks(FunctionCall call, Variables variables) {
    call.parameters.getFirst().resolveEquationLinks(variables);
    call.parameters.getLast().resolveLinks(variables);
  }
  
  @Override
  public void toByteCode(FunctionCall call) {
    call.parameters.getLast().toByteCode();
    call.parameters.getFirst().equationToByteCode();
  }
}
