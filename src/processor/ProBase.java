package processor;

import ast.Block;
import ast.ClassEntity;
import ast.Entity;
import base.Base;

public class ProBase extends Base {
  public static Entity currentObject, currentParam;
  public static ClassEntity subtype = null;
  protected static Block currentBlock;
}
