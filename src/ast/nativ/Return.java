package ast.nativ;

import static ast.Entity.addCommand;
import static ast.Entity.i64Class;
import ast.FunctionCall;
import ast.NativeFunction;
import vm.VMDeallocate;
import vm.I64StackMoveReturnValue;
import vm.VMBase;
import vm.VMReturn;

public class Return extends NativeFunction {
  public Return() {
    super("return");
  }
  
  @Override
  public void functionToByteCode(FunctionCall call) {
    int paramQuantity = VMBase.currentFunction.paramIndex
        + VMBase.currentFunction.varIndex + 2;
    if(VMBase.currentFunction.type == i64Class && paramQuantity > 0)
      addCommand(new I64StackMoveReturnValue());
    if(paramQuantity > 0) addCommand(new VMDeallocate(paramQuantity));
    addCommand(new VMReturn());
  }
}
