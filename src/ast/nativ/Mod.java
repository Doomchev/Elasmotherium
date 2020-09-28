package ast.nativ;

import ast.Entity;
import ast.NativeFunction;

public class Mod extends NativeFunction {
  public Mod() {
    super("mod");
  }
  
  @Override
  public int getPriority() {
    return 14;
  }
  
  @Override
  public Entity calculateType(Entity param0, Entity param1) {
    return getPriorityType(param0, param1, INTEGER);
  }
}
