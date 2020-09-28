package ast.nativ;

import ast.Entity;
import ast.NativeFunction;

public class Decrement extends NativeFunction {
  public Decrement() {
    super("decrement");
  }
  
  @Override
  public int getPriority() {
    return 18;
  }
  
  @Override
  public Entity calculateType(Entity param0, Entity param1) {
    if(!param0.getType().isNumber())
      error(param0.toString() + " cannot be decremented");
    return null;
  }
}
