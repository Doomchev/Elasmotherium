package vm;

import ast.ObjectEntity;

public class VMFunctionCall {
  VMFunction function;
  Command returnPoint;
  int paramPosition = -1;
  ObjectEntity thisObject;
}
