package base;

import ast.ID;
import parser.Action;
import vm.VMValue;

public class ElException extends Exception {
  public String message;

  public ElException(String message) {
    this.message = message;
  }

  public ElException(Base what, String into) {
    this("Cannot insert " + what.getName() + " into " + into + ".");
  }

  public ElException(String message, Base entity) {
    this(message + " " + entity.getName() + ".");
  }

  public ElException(String message, VMValue value) {
    this(message + " " + value.toString() + ".");
  }

  public ElException(ID what) {
    this("Cannot create " + what + ".");
  }

  public ElException(Action action, String message) {
    this("parser code (" + action.parserLine + ")\n" + message);
  }
}
