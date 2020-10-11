package ast.nativ;

import ast.Entity;
import ast.NativeFunction;
import static parser.ParserBase.error;

public class Negative extends NativeFunction {
  public Negative() {
    super("negative");
  }
  
  @Override
  public int getPriority() {
    return 16;
  }
  
  @Override
  public Entity calculateType(Entity param0, Entity param1) {
    Entity type0 = param0.getType();
    if(!type0.isNumber())
      throw new Error(param0.toString() + " cannot be negated");
    return type0;
  }
}
