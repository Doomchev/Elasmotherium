package base;

import ast.Entity;
import ast.ID;
import parser.Action;
import parser.EntityStack;
import processor.ProCommand;
import processor.parameter.ProParameter;
import vm.VMCommand;
import vm.values.VMValue;

public class ElException extends Exception {
  public static class NotFound extends ElException {
    public NotFound(String object, String what) {
      super(object, what + " is not found");
    }
    public NotFound(Entity entity, String what) {
      super(entity, what + " is not found");
    }
    public NotFound(Entity entity, String what, String where) {
      super(entity, what + " is not found in " + where);
    }
    public NotFound(Entity entity, String what, Base where) {
      this(entity, what, where.toString());
    }
  }
  
  public static class Cannot extends ElException {
    public Cannot(String action, Entity entity) {
      super(entity, "Cannot " + action + " " + entity);
    }
    public Cannot(String action, ProParameter parameter) {
      super(parameter, "Cannot " + action + " " + parameter);
    }
    public Cannot(String action, VMValue parameter) {
      super(parameter, "Cannot " + action + " " + parameter);
    }
    public Cannot(String action, Entity entity, String comment) {
      super(entity, "Cannot " + action + " " + entity + " " + comment);
    }
  }
  
  public static class CannotGet extends ElException {
    public CannotGet(String what, Entity from) {
      super(from, "Cannot get " + what + " from " + from);
    }
    public CannotGet(String what, ProParameter from) {
      super(from, "Cannot get " + what + " from " + from);
    }
    public CannotGet(String what, VMValue from) {
      super(from, "Cannot get " + what + " from " + from);
    }
  }
  
  public static class CannotSet extends ElException {
    public CannotSet(String what, Entity parent) {
      super(parent, "Cannot set " + what + " of " + parent);
    }    
    public CannotSet(String what, VMValue parent) {
      super(parent, "Cannot set " + what + " of " + parent);
    }    
  }
  
  public static class CannotMove extends ElException {
    public CannotMove(Entity what, String to) {
      super(what, "Cannot move " + what + " to " + to);
    }
  }
  
  public static class CannotCreate extends ElException {
    private CannotCreate(Base object, String what) {
      super(object, "Cannot create " + what);
    }
    public CannotCreate(Entity object, String what) {
      super(object, "Cannot create " + what);
    }
    public CannotCreate(EntityStack entityStack, String what) {
      super(entityStack, "Cannot create " + what);
    }
    public CannotCreate(ProCommand command, String what) {
      super(command, what);
    }
    public CannotCreate(VMCommand command, String what) {
      super(command, what);
    }
    public CannotCreate(Entity entity, ID what) {
      this(entity, what.toString());
    }
    public CannotCreate(EntityStack object, ID what) {
      this(object, what.toString());
    }
    public CannotCreate(Entity object) {
      this(object, object.toString());
    }
    public CannotCreate(VMCommand object) {
      this(object, object.toString());
    }
  }
  
  public static class MethodException extends ElException {
    public MethodException(Entity object, String method, String message) {
      super(object + "." + method, message);
    }
    public MethodException(Action object, String method, String message) {
      super(object + "." + method, message);
    }
    public MethodException(EntityStack object, String method, String message) {
      super(object + "." + method, message);
    }
    public MethodException(VMCommand object, String method, String message) {
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
