package ast.nativ;

import ast.Entity;
import ast.NativeFunction;

public class BitOr extends NativeFunction {
  public BitOr() {
    super("bitOr");
  }
  
  @Override
  public int getPriority() {
    return 9;
  }
  
  @Override
  public Entity calculateType(Entity param0, Entity param1) {
    return getPriorityType(param0, param1, INTEGER);
  }
}
