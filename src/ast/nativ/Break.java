package ast.nativ;

import static ast.Entity.addCommand;
import ast.FunctionCall;
import ast.NativeFunction;
import vm.VMEnd;

public class Break extends NativeFunction {
  public Break() {
    super("break");
  }
  
  @Override
  public void functionToByteCode(FunctionCall call) {
    addCommand(new VMEnd());
  }
}
