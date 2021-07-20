package base;

import ast.Entity;
import ast.ID;
import parser.Action;

public class ElException extends Exception {
  public String message;

  public ElException(String message) {
    this.message = message;
  }

  public ElException(Entity what, String into) {
    this("Cannot insert " + what.getName() + " into " + into + ".");
  }

  public ElException(String message, Entity entity) {
    this(message + entity.getID() + ".");
  }

  public ElException(ID what) {
    this("Cannot create " + what + ".");
  }

  public ElException(Action action, String message) {
    this("parser code (" + action.parserLine + ")\n" + message);
  }
}
