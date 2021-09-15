package processor;

import ast.Block;
import ast.ClassEntity;
import ast.Entity;
import base.Base;

public class ProBase extends Base {

  public static Entity currentObject;
  public static Entity currentParameter;
  public static Entity currentField;
  public static Entity object;
  public static ClassEntity subtype = null;
  protected static Block currentBlock;
  protected static int parametersQuantity;
}
