package ast.nativ;

import ast.Entity;
import ast.NativeFunction;

public class Increment extends NativeFunction {
  public Increment() {
    super("increment");
  }
  
  @Override
  public int getPriority() {
    return 18;
  }
  
  @Override
  public Entity calculateType(Entity param0, Entity param1) {
    if(!param0.getType().isNumber())
      throw new Error(param0.toString() + " cannot be incremented");
    return null;
  }
}
