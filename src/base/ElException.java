package base;

import ast.ID;
import parser.Action;

public class ElException extends Exception {
  public static class NotFound extends ElException {
    public NotFound(String object, String what) {
      super(object, what + " is not found");
    }
    public NotFound(Base object, String what) {
      super(object, what + " is not found");
    }
    public NotFound(Base object, String what, String where) {
      super(object, what + " is not found in " + where);
    }
    public NotFound(Base object, String what, Base where) {
      this(object, what, where.toString());
    }
  }
  
  public static class Cannot extends ElException {
    public Cannot(String action, Base object) {
      super(object, "Cannot " + action + " " + object);
    }
    public Cannot(String action, Base object, String comment) {
      super(object, "Cannot " + action + " " + object + " " + comment);
    }
  }
  
  public static class CannotGet extends ElException {
    public CannotGet(String what, Base from) {
      super(from, "Cannot get " + what + " from " + from);
    }    
  }
  
  public static class CannotSet extends ElException {
    public CannotSet(String what, Base parent) {
      super(parent, "Cannot set " + what + " of " + parent);
    }    
  }
  
  public static class CannotMove extends ElException {
    public CannotMove(Base what, String to) {
      super(what, "Cannot move " + what + " to " + to);
    }
  }
  
  public static class CannotCreate extends ElException {
    public CannotCreate(Base object, String what) {
      super(object, "Cannot create " + what);
    }
    public CannotCreate(Base object, ID what) {
      this(object, what.toString());
    }
    public CannotCreate(Base object) {
      this(object, object.toString());
    }
  }
  
  public static class MethodException extends ElException {
    public MethodException(Base object, String method, String message) {
      super(object + "." + method, message);
    }
    public MethodException(String object, String method, String message) {
      super(object + "." + method, message);
    }
  }
  
  public static class ActionException extends ElException {
    public ActionException(Action action, String name, String message) {
      super(action, name + " " + message);
    }
  }
  
  public static class NotImplemented extends ElException {
    public NotImplemented(Base object, String method) {
      super(object + "." + method, "Not implemented.");
    }
  }
  
  public final String message;
  
  protected ElException(String object, String message) {
    this.message = object + ": " + message;
  }
  
  public ElException(Base object, String message) {
    this(object.getClassName(), message);
  }
}
