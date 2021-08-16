package vm;

import ast.Function;
import ast.ObjectEntity;

public class VMFunctionCall extends VMBase {
  public VMFunctionCall(ObjectEntity thisObject, int pos) {
    this.thisObject = thisObject;
    this.paramPosition = pos;
    this.returnPoint = currentCommand + 1;
  }
  
  Function function;
  int returnPoint, paramPosition;
  ObjectEntity thisObject;
}
