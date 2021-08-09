package vm;

import ast.ObjectEntity;

public class VMFunctionCall {
  public VMFunctionCall(ObjectEntity thisObject) {
    this.thisObject = thisObject;
  }
  
  VMFunction function;
  VMCommand returnPoint;
  int paramPosition = -1;
  ObjectEntity thisObject;
}
