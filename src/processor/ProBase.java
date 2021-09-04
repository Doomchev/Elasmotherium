package processor;

import ast.Block;
import ast.ClassEntity;
import ast.Entity;
import base.Base;

public class ProBase extends Base {
  protected static Entity current, object, param, currentField;
  protected static ClassEntity subtype = null;
  protected static Block currentBlock;
}
