package ast;

import ast.function.CustomFunction;
import ast.function.FunctionCall;
import base.Base;
import base.ElException;
import base.ElException.Cannot;
import base.ElException.CannotCreate;
import base.ElException.CannotGet;
import base.ElException.CannotMove;
import processor.Processor;
import vm.VMCommand;
import vm.values.VMValue;

public abstract class Entity extends Base {
  // properties
  
  public ID getName() throws ElException {
    throw new CannotGet("name", this);
  }
  
  public ID getID() throws ElException {
    throw new CannotGet("id", this);
  }
  
  public Entity getType() throws ElException {
    throw new CannotGet("type", this);
  }
  
  public Entity getType(Entity[] subTypes) throws ElException {
    throw new CannotGet("type using subtypes", this);
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
  
  public String getStringValue() throws ElException {
    throw new CannotGet("string value", this);
  }
  
  public int getIndex() throws ElException {
    throw new CannotGet("index", this);
  }

  public Entity getBlockParameter(ID name) throws ElException {
    throw new CannotGet(name + " parameter", this);
  }

  public Variable getField() throws ElException {
    throw new CannotGet("field", this);
  }

  public Variable getField(ID name) throws ElException {
    throw new CannotGet("field " + name, this);
  }

  public void resolveField(ID name, Entity type) throws ElException {
    throw new Cannot("resolve field " + name + " of", this, "to " + type);
  }
  
  public Entity getMethod(ID name, int parametersQuantity) throws ElException {
    throw new CannotGet("method " + name, this);
  }

  public Entity[] getSubTypes(ID classID, int quantity) throws ElException {
    throw new CannotGet("sub types", this);
  }
  
  // processing
    
  public void process() throws ElException {
    throw new Cannot("process", this);
  }
  
  public void process(FunctionCall call) throws ElException {
    throw new Cannot("process", this, "with call");
  }

  public void process(FunctionCall call, Entity[] subTypes) throws ElException {
    throw new Cannot("process", this, "with call and subtypes");
  }
  
  public Entity resolve() throws ElException {
    return this;
  }
  
  public Entity resolveFunction(int parametersQuantity) throws ElException {
    return this;
  }

  public void resolve(Entity type) throws ElException {
    currentProcessor.resolve(this, type);
  }

  public void resolve(Entity type, FunctionCall call)
      throws ElException {
    throw new Cannot("resolve", this, "to " + type);
  }
  
  public Entity resolveRecursively() throws ElException {
    return this;
  }

  public Entity resolveRecursively(int parametersQuantity) throws ElException {
    return this;
  }
  
  public void resolveChild(Entity type) throws ElException {
    throw new Cannot("resolve child", this);
  }

  public Entity getObject() throws ElException {
    currentProcessor.getObject(this);
    return Processor.currentParam;
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
    throw new CannotCreate(this, "value");
  }

  public static void append(VMCommand command) {
    appendLog(command);
  }
  
  public void print(StringBuilder indent, String prefix) {
    println(indent.toString() + prefix + toString() + ";");
  }
}
