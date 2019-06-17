package parser.structure;

import java.util.HashMap;
import java.util.LinkedList;

public class NativeFunction extends Function {
  public static final HashMap<ID, NativeFunction> all = new HashMap<>();
  
  int priority;
  
  public NativeFunction(String name, int priority) {
    super(ID.get(name));
    this.priority = priority;
    all.put(this.name, this);
  }
  
  @Override
  public boolean isEmptyFunction() {
    return true;
  }
  
  @Override
  public boolean isNativeFunction() {
    return true;
  }

  @Override
  int getPriority() {
    return priority;
  }

  @Override
  public ID getID() {
    return name;
  }

  @Override
  public Entity setCallTypes(LinkedList<Entity> parameters, Scope parentScope) {
    return ClassEntity.voidClass;
  }
  
  public Entity calculateType(Entity type0, Entity type1) {
    return null;
  }

  @Override
  public String toString() {
    return name.string;
  }
}
