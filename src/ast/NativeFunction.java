package ast;

import java.util.HashMap;
import java.util.LinkedList;

public class NativeFunction extends Function {
  public static final HashMap<ID, NativeFunction> all = new HashMap<>();
  
  public NativeFunction(String name) {
    super(ID.get(name));
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
  public int getPriority() {
    return 17;
  }

  @Override
  public ID getID() {
    return name;
  }
  
  @Override
  public void setCallTypes(LinkedList<Entity> parameters, Scope parentScope) {
    if(parameters.isEmpty()) return;
    type = calculateType(parameters.getFirst(), parameters.getLast());
  }
  
  public Entity calculateType(Entity param0, Entity param1) {
    return null;
  }

  public final int ANY = 40, STRING = 30, NUMBER = 20, INTEGER = 10;
  
  public Entity getPriorityType(Entity param0, Entity param1, int limit) {
    Entity type0 = param0.getType();
    Entity type1 = param1.getType();
    int priority0 = type0.getClassPriority();
    int priority1 = type1.getClassPriority();
    if(priority0 >= limit) error(param0.toString() + " cannot be "
        + getActionName());
    if(priority1 >= limit) error(param1.toString() + " cannot be "
        + getActionName());
    if(priority0 > priority1) {
      param1.setConvertTo(type0);
      return type0;
    } else if(priority0 > priority1) {
      param0.setConvertTo(type1);
      return type1;
    }
    return type0;
  }

  public void check(Entity param0, Entity param1, int limit) {
    if(param0.getType().getClassPriority() >= limit)
      error(param0.toString() + " cannot be "+ getActionName());
    if(param1.getType().getClassPriority() >= limit)
      error(param1.toString() + " cannot be "+ getActionName());
  }

  public String getActionName() {
    return "";
  }

  @Override
  public void toByteCode(FunctionCall call) {
    for(Entity parameter : call.parameters) parameter.toByteCode();
    functionToByteCode(call);
  }
  
  @Override
  public void functionToByteCode(FunctionCall call) {
    error(toString() + " is not implemented.");
  }

  @Override
  public String toString() {
    return name.string;
  }
}