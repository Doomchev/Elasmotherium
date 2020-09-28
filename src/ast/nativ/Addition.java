package ast.nativ;

import ast.ClassEntity;
import ast.Entity;
import static ast.Entity.addCommand;
import ast.FunctionCall;
import ast.NativeFunction;
import static parser.ParserBase.error;
import vm.I64Add;
import vm.StringAdd;

public class Addition extends NativeFunction {
  public Addition() {
    super("addition");
  }
  
  @Override
  public int getPriority() {
    return 13;
  }
  
  @Override
  public Entity calculateType(Entity param0, Entity param1) {
    return getPriorityType(param0, param1, STRING);
  }

  @Override
  public void functionToByteCode(FunctionCall call) {
    Entity type0 = call.getType();
    if(type0 == ClassEntity.i64Class) {
      addCommand(new I64Add());
    } else if(type0 == ClassEntity.stringClass) {
      addCommand(new StringAdd());
    } else {
      error("Addition of " + type0.toString() + " is not implemented.");
    }
  }

  @Override
  public String getActionName() {
    return "added";
  }
}
