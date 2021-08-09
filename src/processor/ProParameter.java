package processor;

import ast.Entity;
import base.ElException;

public abstract class ProParameter extends ProBase {
  static ProParameter get(String name) throws ElException {
    switch(name) {
      case "this":
        return ProThis.instance;
      case "parent":
        return ProParent.instance;
      case "value":
        return ProVariableValue.instance;
      default:
        if(name.startsWith("v")) {
          return new ProCallParameter(name.substring(1));
        } else {
          throw new ElException(name + " is wrong parameter name.");
        }
    }
  }
  
  abstract Entity getValue() throws ElException;
}
