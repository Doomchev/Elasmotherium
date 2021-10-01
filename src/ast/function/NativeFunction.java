package ast.function;

import ast.Entity;
import ast.Formula;
import ast.ID;
import ast.exception.ElException;
import ast.exception.EntityException;
import ast.exception.NotFound;
import java.util.HashMap;
import java.util.LinkedList;

@SuppressWarnings("ResultOfObjectAllocationIgnored")
public class NativeFunction extends Function {
  private static class UnaryNativeFunction extends NativeFunction {
    public UnaryNativeFunction(String name, int priority, String operator) {
      super(name, priority, operator);
    }
    @Override
    public String toString(LinkedList<Entity> parameters) {
      return operator + parameters.getFirst();
    }
  }
  
  private static final HashMap<ID, NativeFunction> all = new HashMap<>();
  public static NativeFunction ret, equate, dot, callFunction;
  
  public final byte priority;
  public final String operator;
  
  private NativeFunction(String name, int priority, String operator) {
    super(ID.get(name), 0, 0);
    this.priority = (byte) priority;
    this.operator = operator;
    all.put(this.name, this);
  }
  
  private NativeFunction(String name, int priority) {
    this(name, priority, "");
  }

  public static NativeFunction get(String name) throws NotFound {
    return get(ID.get(name));
  }

  public static NativeFunction get(ID name) throws NotFound {
    NativeFunction function = all.get(name);
    if(function != null) return function;
    throw new NotFound("Native function " + name);
  }
  
  static {
    new NativeFunction("brackets", 18);
    
    dot = new NativeFunction("dot", 17, ".");
    callFunction = new NativeFunction("callFunction", 17);
    new NativeFunction("at", 17) {
      @Override
      public String toString(LinkedList<Entity> parameters) {
        return parameters.getFirst() + "[" + parameters.getLast() + "]";
      }
    };
    
    new UnaryNativeFunction("negative", 16, "-");
    new UnaryNativeFunction("not", 16, "!");
    
    new NativeFunction("iDivision", 14, " ~/ ");
    new NativeFunction("mod", 14, " % ");
    new NativeFunction("division", 14, " / ");
    new NativeFunction("subtraction", 13, " - ");
    new NativeFunction("multiplication", 14, " * ");
    new NativeFunction("addition", 13, " + ");
    new NativeFunction("bitAnd", 11, " & ");
    new NativeFunction("bitOr", 10, " | ");
    new NativeFunction("less", 8, " < ");
    new NativeFunction("more", 8, " > ");
    new NativeFunction("lessOrEqual", 8, " <= ");
    new NativeFunction("moreOrEqual", 8, " >= ");
    new NativeFunction("equal", 7, " == ");
    new NativeFunction("notEqual", 7, " != ");
    new NativeFunction("and", 6, " && ");
    new NativeFunction("or", 5, " || ");
    new NativeFunction("ifOp", 4);
    
    new NativeFunction("increment", 3);
    new NativeFunction("decrement", 3);
    equate = new NativeFunction("equate", 3, " = ");
    new NativeFunction("add", 3);
    new NativeFunction("subtract", 3);
    new NativeFunction("multiply", 3);
    new NativeFunction("divide", 3);
    new NativeFunction("iDivide", 3);
    new NativeFunction("mode", 3);
    
    new NativeFunction("break", 0);
    new NativeFunction("continue", 0);
    ret = new NativeFunction("return", 0);
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
    return parameters.getFirst() + operator + parameters.getLast();
  }
}