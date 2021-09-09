package processor;

import ast.Block;
import ast.ClassEntity;
import ast.Entity;
import ast.function.FunctionCall;
import base.Base;

public class ProBase extends Base {
  protected static Entity currentObject, object, param, currentField;
  protected static ClassEntity subtype = null;
  protected static Block currentBlock;
  protected static FunctionCall currentCall;
}
