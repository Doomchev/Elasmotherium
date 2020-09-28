package ast.nativ;

import ast.Entity;
import ast.NativeFunction;
import static parser.ParserBase.error;

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
      error(param0.toString() + " cannot be incremented");
    return null;
  }
}
