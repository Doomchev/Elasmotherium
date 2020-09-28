package ast.nativ;

import ast.Entity;
import ast.NativeFunction;

public class BitAnd extends NativeFunction {
  public BitAnd() {
    super("bitAnd");
  }
  
  @Override
  public int getPriority() {
    return 11;
  }
  
  @Override
  public Entity calculateType(Entity param0, Entity param1) {
    return getPriorityType(param0, param1, INTEGER);
  }
}
