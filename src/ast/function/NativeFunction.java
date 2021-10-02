package ast.function;

import ast.Entity;
import ast.Formula;
import ast.ID;
import ast.exception.ElException;
import ast.exception.EntityException;
import ast.exception.NotFound;
import java.util.HashMap;
import java.util.LinkedList;

public class NativeFunction extends Function {
  private static final HashMap<ID, NativeFunction> all = new HashMap<>();
  public static NativeFunction equate, dot, callFunction;
  
  public final byte priority;
  public final String operator;
  
  private NativeFunction(ID name, byte priority, String operator) {
    super(name, 0, 0);
    this.priority = priority;
    this.operator = operator;
  }
  
  public static void create(String name, byte priority, String operator) {
    ID nameID = ID.get(name);
    all.put(nameID, new NativeFunction(nameID, priority, operator));
  }

  public static NativeFunction get(String name) throws NotFound {
    return get(ID.get(name));
  }

  public static NativeFunction get(ID name) throws NotFound {
    NativeFunction function = all.get(name);
    if(function != null) return function;
    throw new NotFound("Native function " + name);
  }
  
  // properties
  
  @Override
  public ID getID() throws EntityException {
    return name;
  }
  
  // processing
  
  @Override
  public void process(FunctionCall call) throws EntityException {
    try {
      currentProcessor.processCall(call, name);
    } catch (ElException ex) {
      throw new EntityException(this, ex.message);
    }
  }

  @Override
  public void resolve(Entity type, FunctionCall call)
      throws EntityException {
    try {
      currentProcessor.resolveCall(call, name, type);
    } catch (ElException ex) {
      throw new EntityException(this, ex.message);
    }
  }
  
  // moving functions

  @Override
  public void moveToFunctionCall(FunctionCall call) {
    call.setFunction(this);
  }

  @Override
  public void moveToFormula(Formula formula) {
    formula.add(this);
  }
  
  // other

  @Override
  public String toString(LinkedList<Entity> parameters) {
    if(operator.isEmpty() || parameters.isEmpty())
      return super.toString(parameters);
    String op = operator.replace("a", parameters.getFirst().toString());
    if(op.contains("b")) op = op.replace("b", parameters.getLast().toString());
    return op;
  }
}