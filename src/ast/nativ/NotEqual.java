package ast.nativ;

import ast.ClassEntity;
import ast.Entity;
import static ast.Entity.addCommand;
import ast.FunctionCall;
import ast.NativeFunction;
import vm.VMEnd;

public class NotEqual extends NativeFunction {
  public NotEqual() {
    super("notEqual");
  }
  
  @Override
  public int getPriority() {
    return 7;
  }
  
  @Override
  public Entity calculateType(Entity param0, Entity param1) {
    return ClassEntity.booleanClass;
  }
}
