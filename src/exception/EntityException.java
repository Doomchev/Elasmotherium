package exception;

import ast.Entity;
import ast.ID;

public class EntityException extends ElException {
  public final Entity entity;

  public EntityException(Entity entity, String message) {
    super(message);
    this.entity = entity.getErrorEntity();
  }
  
  public static class NotFound extends EntityException {
    public NotFound(Entity entity, String what) {
      super(entity, what + " is not found");
    }
  }
  
  public static class Cannot extends EntityException {
    public Cannot(String action, Entity entity) {
      super(entity, "Cannot " + action + " " + entity);
    }
    public Cannot(String action, Entity entity, String comment) {
      super(entity, "Cannot " + action + " " + entity + " " + comment);
    }
  }
  
  public static class CannotGet extends EntityException {
    public CannotGet(String what, Entity from) {
      super(from, "Cannot get " + what + " from " + from);
    }
  }
  
  public static class CannotSet extends EntityException {
    public CannotSet(String what, Entity parent) {
      super(parent, "Cannot set " + what + " of " + parent);
    }    
  }
  
  public static class CannotCreate extends EntityException {
    public CannotCreate(Entity entity, String what) {
      super(entity, "Cannot create " + what);
    }
    public CannotCreate(Entity entity, ID what) {
      this(entity, what.toString());
    }
    public CannotCreate(Entity object) {
      this(object, object.toString());
    }
  }
  
  public static class MethodException extends EntityException {
    public MethodException(Entity entity, String method, String message) {
      super(entity, entity.getClassName() + "." + method + " exception: "
          + message);
    }
  }
  
  public static class NotImplemented extends EntityException {
    public NotImplemented(Entity entity, String method) {
      super(entity, entity.getClassName() + "." + method
          + " is not implemented.");
    }
  }
}
