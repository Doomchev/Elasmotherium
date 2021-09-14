package ast;

import ast.function.CustomFunction;
import ast.function.FunctionCall;
import base.Base;
import base.ElException;
import base.ElException.Cannot;
import base.ElException.CannotGet;
import base.ElException.CannotMove;
import vm.VMCommand;
import vm.values.VMValue;

public abstract class Entity extends Base {
  // processor fields
  
  public ID getName() throws ElException {
    throw new CannotGet("name", this);
  }
  
  public ID getID() throws ElException {
    throw new CannotGet("id", this);
  }
  
  public ClassEntity getNativeClass() throws ElException {
    throw new CannotGet("native class", this);
  }
  
  public Entity getValue() throws ElException {
    throw new CannotGet("value", this);
  }
  
  public Entity getFormulaValue() throws ElException {
    return this;
  }
  
  public Entity getType() throws ElException {
    throw new CannotGet("type", this);
  }
  
  public String getStringValue() throws ElException {
    throw new CannotGet("string value", this);
  }
  
  public int getIndex() throws ElException {
    throw new CannotGet("index", this);
  }

  public Entity getBlockParameter(ID name) throws ElException {
    throw new CannotGet(name + " parameter", this);
  }
  
  // processing
    
  public void process() throws ElException {
    throw new Cannot("process", this);
  }
  
  public void call(FunctionCall call) throws ElException {
    throw new Cannot("call", this);
  }

  public void resolve(ClassEntity parameter) throws ElException {
    currentProcessor.resolve(this, parameter);
  }
  
  public Entity resolve() throws ElException {
    return this;
  }
  
  public Entity resolve(int parametersQuantity) throws ElException {
    return this;
  }
  
  public Entity resolveRecursively() throws ElException {
    return this;
  }

  public Entity resolveRecursively(int parametersQuantity) throws ElException {
    return this;
  }
  
  // moving functions
  
  public void move(Entity entity) throws ElException {
    throw new Cannot("move anything to", this);
  }

  public void moveToCode(Code code) throws ElException {
    throw new CannotMove(this, "code");
  }

  public void moveToBlock() throws ElException {
  }

  public void moveToFormula(Formula formula) throws ElException {
    throw new CannotMove(this, "formula");
  }

  public void moveToFunctionCall(FunctionCall call) throws ElException {
    throw new CannotMove(this, "function call");
  }

  public void moveToClass(ClassEntity classEntity) throws ElException {
    throw new CannotMove(this, "class");
  }

  public void moveToFunction(CustomFunction function) throws ElException {
    throw new CannotMove(this, "function");
  }

  public void moveToVariable(Variable variable) throws ElException {
    throw new CannotMove(this, "variable");
  }

  public void moveToType(Type type) throws ElException {
    throw new CannotMove(this, "type");
  }

  public void moveToParameters(Parameters parameters) throws ElException {
    throw new CannotMove(this, "parameters");
  }

  public void moveToStringSequence(StringSequence seq) throws ElException {
    throw new CannotMove(this, "string sequence");
  }

  public void moveToList(ListEntity list) throws ElException {
    throw new CannotMove(this, "list");
  }

  public void moveToMap(MapEntity map) throws ElException {
    throw new CannotMove(this, "map");
  }

  public void moveToObjectEntry(ObjectEntry entry) throws ElException {
    throw new CannotMove(this, "object entry");
  }

  public void moveToLink(Link link) throws ElException {
    throw new CannotMove(this, "link");
  }
  
  // other
  
  public VMValue createValue() throws ElException {
    throw new Cannot("create value for ", this);
  }

  public static void append(VMCommand command) {
    appendLog(command);
  }
  
  public void print(StringBuilder indent, String prefix) {
    println(indent.toString() + prefix + toString() + ";");
  }
}
