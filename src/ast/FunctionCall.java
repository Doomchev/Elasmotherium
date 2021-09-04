package ast;

import base.ElException;
import java.util.LinkedList;

public class FunctionCall extends Value {
  public static final ID id = ID.get("call");
  public static final ID resolve = ID.get("resolve");
  
  public byte priority;
  
  private Function function;
  private ID name;
  private final LinkedList<Entity> parameters = new LinkedList<>();
  
  // creating

  public FunctionCall(Function function) {
    this.function = function;
    this.priority = function == null ? 17 : function.priority;
  }
  
  // parameters

  public void setName(ID name) {
    this.name = name;
  }

  public void add(Entity value) {
    parameters.add(value);
  }

  public void add(LinkedList<Value> values) {
    parameters.addAll(values);
  }

  public void addFirst(Entity value) {
    parameters.addFirst(value);
  }

  @Override
  public byte getPriority() {
    return priority;
  }
  
  // processor fields
  
  @Override
  public ID getName() throws ElException {
    return name;
  }
  
  @Override
  public Entity getParameter(int index) throws ElException {
    if(index >= parameters.size()) throw new ElException("Parameter number"
        + index + " is not found.");
    return parameters.get(index);
  }
  
  @Override
  public ID getObject() throws ElException {
    resolveID();
    return function.getObject();
  }
  
  @Override
  public Entity getType() throws ElException {
    return function.getType();
  }

  public Function getFunction() {
    return function;
  }
  
  public void setFunction(Entity function) {
    this.function = (Function) function;
  }
  
  // processing
  
  public void resolveID() throws ElException {
    if(function == null) {
      Entity entity = getFromScope(name);
      try {
        setFunction(entity);
      } catch(ClassCastException ex) {
        setFunction(((ClassEntity) entity).getConstructor());
      }
    }
  }
  
  @Override
  public void process() throws ElException {
    resolveID();
    if(log) println(toString());
    currentProcessor.call(this);
  }
  
  @Override
  public void resolveAll() throws ElException {
    function.resolve(this);
  }
  
  public void resolveParameters() throws ElException {
    function.resolveParameters(this);
  }
  
  // moving functions

  @Override
  public void move(Entity entity) throws base.ElException {
    entity.moveToFunctionCall(this);
  }

  @Override
  public void moveToCode(Code code) {
    code.addLine(this);
  }

  // other
  
  @Override
  public String toString() {
    return (name == null ? function : name)
        + "(" + listToString(parameters) + ")";
  }
}
