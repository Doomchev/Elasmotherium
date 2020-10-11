package ast.nativ;

import ast.ClassEntity;
import ast.Entity;
import static ast.Entity.addCommand;
import ast.FunctionCall;
import ast.NativeFunction;
import static parser.ParserBase.error;
import vm.I64IsMore;

public class More extends NativeFunction {
  public More() {
    super("more");
  }
  
  @Override
  public int getPriority() {
    return 7;
  }
  
  @Override
  public Entity calculateType(Entity param0, Entity param1) {
    getPriorityType(param0, param1, NUMBER);
    return ClassEntity.booleanClass;
  }

  @Override
  public void functionToByteCode(FunctionCall call) {
    Entity type0 = getPriorityType(call.parameters.getFirst()
        , call.parameters.getLast(), NUMBER);
    if(type0 == ClassEntity.i64Class) {
      addCommand(new I64IsMore());
    } else {
      throw new Error("Addition of " + type0.toString() + " is not implemented.");
    }
  }

  @Override
  public String getActionName() {
    return "compared";
  }
}
