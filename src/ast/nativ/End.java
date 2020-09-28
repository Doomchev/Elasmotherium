package ast.nativ;

import static ast.Entity.addCommand;
import ast.FunctionCall;
import ast.NativeFunction;
import vm.VMEnd;

public class End extends NativeFunction {
  public End() {
    super("end");
  }
  
  @Override
  public void functionToByteCode(FunctionCall call) {
    addCommand(new VMEnd());
  }
}
