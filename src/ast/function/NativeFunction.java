package ast.function;

import ast.Entity;
import ast.Formula;
import ast.ID;
import base.ElException;
import base.EntityException;
import base.ElException.NotFound;
import java.util.HashMap;

public class NativeFunction extends Function {
  private static final HashMap<ID, NativeFunction> all = new HashMap<>();
  public static NativeFunction ret, equate, dot, callFunction;
  
  public final byte priority;
  
  public NativeFunction(ID name, byte priority) {
    super(name, 0, 0);
    this.priority = priority;
  }
  
  private static NativeFunction create(String name, int priority) {
    ID functionID = ID.get(name);
    NativeFunction function = new NativeFunction(functionID, (byte) priority);
    all.put(functionID, function);
    return function;
  }

  public static NativeFunction get(String name) throws ElException {
    return get(ID.get(name));
  }

  public static NativeFunction get(ID name) throws ElException {
    NativeFunction function = all.get(name);
    if(function != null) return function;
    throw new NotFound("NativeFunction.get", "Native function " + name);
  }
  
  static {
    create("brackets", 18);
    
    dot = create("dot", 17);
    callFunction = create("callFunction", 17);
    create("at", 17);
    
    create("negative", 16);
    create("not", 16);
    create("iDivision", 14);
    create("mod", 14);
    create("division", 14);
    create("subtraction", 13);
    create("multiplication", 14);
    create("addition", 13);
    create("bitAnd", 11);
    create("bitOr", 10);
    create("less", 8);
    create("more", 8);
    create("lessOrEqual", 8);
    create("moreOrEqual", 8);
    create("equal", 7);
    create("notEqual", 7);
    create("or", 5);
    create("and", 6);
    create("ifOp", 4);
    
    create("increment", 3);
    create("decrement", 3);
    equate = create("equate", 3);
    create("add", 3);
    create("subtract", 3);
    create("multiply", 3);
    create("divide", 3);
    create("iDivide", 3);
    create("mode", 3);
    
    create("break", 0);
    create("continue", 0);
    ret = create("return", 0);
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
}