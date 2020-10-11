package ast.nativ;

import ast.Entity;
import ast.NativeFunction;

public class Add extends NativeFunction {
  public Add() {
    super("add");
  }
  
  @Override
  public int getPriority() {
    return 18;
  }
  
  @Override
  public Entity calculateType(Entity param0, Entity param1) {
    if(!param0.getType().isNumber())
      throw new Error(param0.toString() + " cannot be added");
    return null;
  }
}
