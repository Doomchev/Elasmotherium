package ast.nativ;

import static ast.Entity.addCommand;
import ast.FunctionCall;
import ast.NativeFunction;
import vm.VMEnd;

public class Continue extends NativeFunction {
  public Continue() {
    super("continue");
  }
  
  @Override
  public void functionToByteCode(FunctionCall call) {
    addCommand(new VMEnd());
  }
}
