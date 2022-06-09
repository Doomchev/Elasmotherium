package ast;

import ast.function.CustomFunction;
import ast.function.FunctionCall;
import base.Base;
import base.Module;
import exception.ElException;
import exception.ElException.CannotMove;
import exception.EntityException;
import exception.EntityException.CannotGet;
import exception.NotFound;
import processor.Processor;
import vm.VMCommand;
import vm.values.VMValue;

import java.util.LinkedList;

public abstract class Entity extends Base {
  public Module module;
  public int textStart, textEnd;
  
  public Entity(int textStart, int textEnd) {
    this.module = Module.current;
    this.textStart = textStart;
    this.textEnd = textEnd;
  }
  
  public Entity(IDEntity id) {
    this.module = Module.current;
    this.textStart = id == null ? 0 : id.textStart;
    this.textEnd = id == null ? 0 : id.textEnd;
  }
  
  // properties
  
  public ID getName() throws EntityException {
    throw new CannotGet("name", this);
  }
  
  public ID getID() throws EntityException {
    throw new CannotGet("id", this);
  }
  
  public Entity getType() throws EntityException {
    throw new CannotGet("type", this);
  }

  public Entity getSubType() throws EntityException {
    throw new CannotGet("subtype", this);
  }
  
  public Entity getType(Entity[] subTypes) throws EntityException {
    throw new CannotGet("type using subtypes", this);
  }
  
  public ClassEntity getNativeClass() throws EntityException {
    throw new CannotGet("native class", this);
  }
  
  public Entity getValue() throws EntityException {
    throw new CannotGet("value", this);
  }
  
  public String getStringValue() throws EntityException {
    throw new CannotGet("string value", this);
  }
  
  public int getIndex() throws EntityException {
    throw new CannotGet("index", this);
  }

  public Entity getBlockParameter(ID name) throws EntityException {
    throw new CannotGet(name + " parameter", this);
  }

  public Variable getField() throws EntityException {
    throw new CannotGet("field", this);
  }

  public Variable getField(ID name) throws EntityException, NotFound {
    throw new CannotGet("field " + name, this);
  }

  public void resolveField(ID name, Entity type) throws EntityException {
    throw new EntityException.Cannot("resolve field " + name + " of", this
        , "to " + type);
  }
  
  public Entity getMethod(ID name, int parametersQuantity)
      throws NotFound, EntityException {
    throw new CannotGet("method " + name, this);
  }

  public Entity[] getSubTypes(ID classID, int quantity) throws EntityException {
    throw new CannotGet("sub types", this);
  }

  public Entity getErrorEntity() {
    return this;
  }
  
  // processing
    
  public void compile() throws EntityException {
    throw new EntityException.Cannot("process", this);
  }
  
  public void compileCall(FunctionCall call) throws EntityException {
    throw new EntityException.Cannot("process", this, "with call");
  }

  public void compileCall(FunctionCall call, Entity[] subTypes)
      throws EntityException {
    throw new EntityException.Cannot("process", this, "with call and subtypes");
  }
  
  public Entity resolve() throws EntityException {
    return this;
  }

  public void resolve(Entity type) throws EntityException {
    try {
      currentProcessor.resolve(this, type);
    } catch (ElException ex) {
      throw new EntityException(this, ex.message);
    }
  }
  
  public Entity resolveFunction(int parametersQuantity) throws EntityException {
    return this;
  }

  public void resolve(Entity type, FunctionCall call)
      throws EntityException {
    throw new EntityException.Cannot("resolve", this, "to " + type);
  }
  
  public Entity resolveRecursively() throws EntityException {
    return this;
  }

  public Entity resolveRecursively(int parametersQuantity) throws EntityException {
    return this;
  }
  
  public void resolveChild(Entity type) throws EntityException {
    throw new EntityException.Cannot("resolve child", this);
  }

  public Entity getObject() throws EntityException {
    try {
      currentProcessor.getObject(this);
    } catch (ElException ex) {
      throw new EntityException(this, ex.message);
    }
    return Processor.currentType;
  }
  
  // moving functions
  
  public Entity getFormulaValue() throws ElException {
    return this;
  }
  
  public void move(Entity entity) throws ElException {
    throw new ElException.Cannot("move anything to", this);
  }

  public void moveToCode(Code code) throws ElException {
    throw new CannotMove(this, "code");
  }

  public void moveToBlock() {
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
  
  public VMValue createValue() {
    throw new NullPointerException();
  }

  public static void append(VMCommand command) {
    appendLog(command);
  }
  
  public void print(StringBuilder indent, String prefix) {
    println(indent.toString() + prefix + this + ";");
  }
  
  public void showDebugMessage(String message) {
    showDebugMessage(module.name + ".es", message
        , module.readText(), textStart, textEnd);
  }

  public String toString(LinkedList<Entity> parameters) {
    return this + "(" + listToString(parameters) + ")";
  }
}
