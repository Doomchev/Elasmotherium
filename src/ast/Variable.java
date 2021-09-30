package ast;

import ast.function.CustomFunction;
import ast.function.FunctionCall;
import ast.function.NativeFunction;
import base.ElException;
import processor.Processor;
import vm.values.VMValue;

public class Variable extends NamedEntity {
  public static final ID id = ID.get("variable");
  public static final ID fieldID = ID.get("field");
  public static final ID thisID = ID.get("this");
  
  private Entity type;
  private Entity value = null;
  private boolean isField = false;
  private int index;
  
  // creating

  public Variable(ID name, ClassEntity type) {
    super(name, 0, 0);
    this.type = type;
  }

  public Variable(IDEntity name, ClassEntity type) {
    super(name);
    this.type = type;
  }

  public Variable(IDEntity name) {
    super(name);
    this.type = null;
  }

  public Variable(IDEntity name, boolean isField) {
    this(name);
    this.isField = isField;
  }
  
  // properties
  
  @Override
  public ID getID() throws ElException {
    return isField ? fieldID : id;
  }
  
  @Override
  public Entity getValue() throws ElException {
    return value;
  }

  public void setValue(Entity value) {
    this.value = value;
  }
  
  @Override
  public ClassEntity getNativeClass() throws ElException {
    return type.getNativeClass();
  }
  
  @Override
  public Entity getType() throws ElException {
    return type.getType();
  }
  
  @Override
  public Entity getType(Entity[] subTypes) throws ElException {
    return type.getType(subTypes);
  }

  public void setType(Entity type) {
    this.type = type;
  }
  
  @Override
  public int getIndex() throws ElException {
    return index;
  }
  
  @Override
  public boolean isValue(ID name)
      throws ElException {
    return this.name == name;
  }
  
  // preprocessing
  
  public void resolveType() throws ElException {
    type = type.resolve();
    if(value != null) 
      value = value.resolveRecursively();
  }
   
  // processing
  
  @Override
  public void process() throws ElException {
    if(log) print(new StringBuilder(), "");
    addToScope(this);
    resolveType();
    if(value != null) currentProcessor.call(this, id, Processor.callMethod);
  }

  public void processField(ClassEntity classEntity, Code code)
      throws ElException {
    if(!isField) return;
    Variable field = classEntity.getField(name);
    FunctionCall equate = new FunctionCall(NativeFunction.equate);
    equate.add(field);
    equate.add(this);
    code.addLineFirst(equate);
    isField = false;
    type = field.type;
    value = null;
  }

  @Override
  public Entity getObject() throws ElException {
    currentProcessor.getObject(this);
    return type;
  }
  
  // moving functions
  
  @Override
  public void move(Entity entity) throws base.ElException {
    entity.moveToVariable(this);
  }

  @Override
  public void moveToClass(ClassEntity classEntity) {
    index = classEntity.addField(this);
    isField = true;
  }

  @Override
  public void moveToFunction(CustomFunction function) throws ElException {
    index = function.addParameter(this);
  }

  @Override
  public void moveToCode(Code code) {
    index = currentAllocation;
    currentAllocation++;
    code.addLine(this);
  }

  @Override
  public void moveToBlock() throws ElException {
    index = currentAllocation;
    currentAllocation++;
  }
  
  // other
  
  @Override
  public VMValue createValue() throws ElException {
    return type.createValue();
  }

  @Override
  public String toString() {
    return (isField ? "this." : "") + name.string;
  }

  public String toParamString() {
    return type + " " + name + ":" + index;
  }
  
  @Override
  public void print(StringBuilder indent, String prefix) {
    println(indent.toString() + prefix + type + " " + toString() + ":" + index
        + (value == null ? "" : " = " + value) + ";");
  }
}
