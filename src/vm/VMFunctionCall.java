package vm;

import ast.Function;
import ast.ObjectEntity;

public class VMFunctionCall extends VMBase {
  public VMFunctionCall(ObjectEntity thisObject, int paramPosition
      , int deallocation) {
    this.paramPosition = paramPosition;
    this.returnPoint = currentCommand + 1;
    this.deallocation = deallocation;
  }
  
  Function function;
  int returnPoint, paramPosition, deallocation;
}
