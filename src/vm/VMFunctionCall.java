package vm;

import ast.ObjectEntity;

public class VMFunctionCall {
  public VMFunctionCall(ObjectEntity thisObject) {
    this.thisObject = thisObject;
  }
  
  VMFunction function;
  int returnPoint, paramPosition = -1;
  ObjectEntity thisObject;
}
