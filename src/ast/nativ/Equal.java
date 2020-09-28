package ast.nativ;

import ast.ClassEntity;
import ast.Entity;
import static ast.Entity.addCommand;
import ast.FunctionCall;
import ast.NativeFunction;
import static parser.ParserBase.error;
import vm.I64IsEqual;

public class Equal extends NativeFunction {
  public Equal() {
    super("equal");
  }
  
  @Override
  public int getPriority() {
    return 7;
  }
  
  @Override
  public Entity calculateType(Entity param0, Entity param1) {
    return ClassEntity.booleanClass;
  }

  @Override
  public void functionToByteCode(FunctionCall call) {
    Entity type0 = getPriorityType(call.parameters.getFirst()
        , call.parameters.getLast(), ANY);
    if(type0 == ClassEntity.i64Class) {
      addCommand(new I64IsEqual());
    } else {
      error("IsEqual of " + type0.toString() + " is not implemented.");
    }
  }

  @Override
  public String getActionName() {
    return "compared";
  }
}
