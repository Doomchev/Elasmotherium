package ast.nativ;

import ast.NativeFunction;

public class ElseOp extends NativeFunction {
  public ElseOp() {
    super("elseOp");
  }
  
  @Override
  public int getPriority() {
    return 4;
  }
}
