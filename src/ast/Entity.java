package ast;

import ast.function.FunctionCall;
import ast.function.CustomFunction;
import base.Base;
import base.ElException;
import java.util.LinkedList;
import processor.Processor;
import vm.VMCommand;
import vm.values.VMValue;

public abstract class Entity extends Base {
  // processor fields
  
  public ID getName() throws ElException {
    throw new ElException("Cannot get name from", this);
  }
  
  public ID getID() throws ElException {
    throw new ElException("Cannot get id from", this);
  }
  
  public ClassEntity getNativeClass() throws ElException {
    throw new ElException("Cannot convert " + this
        + " to native class.");
  }
  
  public Entity getValue() throws ElException {
    throw new ElException("Cannot get value from", this);
  }
  
  public Entity getFormulaValue() throws ElException {
    return this;
  }
  
  public Entity getType() throws ElException {
    throw new ElException("Cannot get type of", this);
  }
  
  public String getStringValue() throws ElException {
    throw new ElException("Cannot get string value of", this);
  }
  
  public int getIndex() throws ElException {
    throw new ElException("Cannot get index of", this);
  }

  public Entity getBlockParameter(ID name) throws ElException {
    throw new ElException(name + " for " + this + " is not found.");
  }

  public int getParametersQuantity() throws ElException {
    throw new ElException("There are no parameters in ", this);
  }
  
  // processing
    
  public void process() throws ElException {
    throw new ElException("Cannot process", this);
  }
  
  public void call(FunctionCall call) throws ElException {
    throw new ElException("Cannot call", this);
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
  
  // moving functions
  
  public void move(Entity entity) throws ElException {
    throw new ElException("Cannot move anything to", this);
  }

  public void moveToCode(Code code) throws ElException {
    throw new ElException(this, "code");
  }

  public void moveToBlock() throws ElException {
  }

  public void moveToFormula(Formula formula) throws ElException {
    throw new ElException(this, "formula");
  }

  public void moveToFunctionCall(FunctionCall call) throws ElException {
    throw new ElException(this, "function call");
  }

  public void moveToClass(ClassEntity classEntity) throws ElException {
    throw new ElException(this, "class");
  }

  public void moveToFunction(CustomFunction function) throws ElException {
    throw new ElException(this, "function");
  }

  public void moveToVariable(Variable variable) throws ElException {
    throw new ElException(this, "variable");
  }

  public void moveToType(Type type) throws ElException {
    throw new ElException(this, "type");
  }

  public void moveToParameters(Parameters parameters) throws ElException {
    throw new ElException(this, "parameters");
  }

  public void moveToStringSequence(StringSequence seq) throws ElException {
    throw new ElException(this, "string sequence");
  }

  public void moveToList(ListEntity list) throws ElException {
    throw new ElException(this, "list");
  }

  public void moveToMap(MapEntity map) throws ElException {
    throw new ElException(this, "map");
  }

  public void moveToObjectEntry(ObjectEntry entry) throws ElException {
    throw new ElException(this, "object entry");
  }

  public void moveToLink(Link link) throws ElException {
    throw new ElException(this, "link");
  }
  
  // other
  
  public VMValue createValue() throws ElException {
    throw new ElException("Cannot create value for ", this);
  }

  public static void append(VMCommand command) {
    appendLog(command);
  }
  
  public void print(StringBuilder indent, String prefix) {
    println(indent.toString() + prefix + toString() + ";");
  }

  public void call(LinkedList<Entity> parameters) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
