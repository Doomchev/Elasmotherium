package ast;

import base.ElException;
import parser.ParserBase;
import vm.VMCommand;
import vm.VMValue;

public abstract class Entity extends ParserBase {
  public static final byte VALUE = -1;
  
  public byte getPriority() {
    return VALUE;
  }
  
  // processor fields
  
  public ID getID() throws ElException {
    throw new ElException("Cannot get id from", this);
  }
  
  public Entity getValue() throws ElException {
    throw new ElException("Cannot get value from", this);
  }
  
  public Entity getParameter(int index) throws ElException {
    throw new ElException("Cannot get parameter from", this);
  }
  
  public ID getObject() throws ElException {
    throw new ElException("Cannot get object from", this);
  }
  
  public ClassEntity getType() throws ElException {
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
  
  // processing
    
  public void process() throws ElException {
    throw new ElException("Cannot process", this);
  }
  
  public void resolveAll() throws ElException {
    throw new ElException(getName() + " is not a function call.");
  }
  
  // type conversion

  public ClassEntity toClass() throws ElException {
    throw new ElException(getName() + " cannot be converted to class.");
  }

  public FunctionCall toCall() {
    return null;
  }

  public Function toFunction() {
    return null;
  }
  
  public Entity toValue() throws ElException {
    return this;
  }

  public Variable toVariable() {
    return null;
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

  public void moveToFunction(Function function) throws ElException {
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

  @Override
  public String toString() {
    return "";
  }
  
  public void print(String indent, String prefix) {
    println(indent + prefix + toString() + ";");
  }
}
