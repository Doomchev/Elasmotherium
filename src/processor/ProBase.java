package processor;

import ast.Entity;
import base.Base;
import base.ElException;

public class ProBase extends Base {
  static Entity current, object, param;

  public static int getIndex() throws ElException {
    return current.getIndex();
  }
}
