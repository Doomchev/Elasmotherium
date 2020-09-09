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
  public int getPriority() {
    return priority;
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

  public final int ANY = 0, NUMBER = 1, INTEGER = 2;
  
  public Entity getPriorityType(Entity param0, Entity param1, int type) {
    Entity type0 = param0.getType();
    Entity type1 = param1.getType();
    int priority0 = type0.getClassPriority();
    int priority1 = type1.getClassPriority();
    int priorityLimit = type == ANY ? 30 : (type == NUMBER ? 20 : 10);
    if(priority0 >= priorityLimit) error(param0.toString() + " cannot be "
        + getActionName());
    if(priority1 >= priorityLimit) error(param1.toString() + " cannot be "
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

  public void check(Entity param0, Entity param1, int type) {
    int priorityLimit = type == ANY ? 30 : (type == NUMBER ? 20 : 10);
    if(param0.getType().getClassPriority() >= priorityLimit)
      error(param0.toString() + " cannot be "+ getActionName());
    if(param1.getType().getClassPriority() >= priorityLimit)
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
    error(getName() + " is not implemented.");
  }

  @Override
  public String toString() {
    return name.string;
  }
}