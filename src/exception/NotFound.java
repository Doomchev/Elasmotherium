package exception;

import ast.ClassEntity;
import base.Base;

public class NotFound extends Exception {
  public final String message;

  public NotFound(String what) {
    this.message = what + " is not found";
  }
  public NotFound(String what, String where) {
    this.message = what + " is not found in " + where;
  }
  public NotFound(String what, int parametersQuantity) {
    this.message = what + " with " + parametersQuantity
        + " parameters is not found";
  }
  public NotFound(String what, Base where) {
    this.message = what + " is not found in " + where;
  }
  public NotFound(String what, int parametersQuantity, ClassEntity where) {
    this.message = what + " with " + parametersQuantity
        + " parameters is not found in " + where;
  }
}
