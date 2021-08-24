package ast;

import base.ElException;

public class Variable extends NamedEntity {
  public static ID id = ID.get("variable");
  public static ID fieldID = ID.get("field");
  public static ID thisID = ID.get("this");
  
  public Entity type, value = null;
  public Code code = null;
  public ClassEntity parentClass = null;
  public Function parentFunction;
  public int index;

  public Variable(ID id) {
    this.name = id;
  }

  public Variable(ClassEntity type) {
    this.name = id;
    this.type = type;
  }

  public Variable(ID id, ClassEntity classEntity) {
    this.name = id;
    this.parentClass = classEntity;
  }
  
  public boolean isField() {
    return parentClass != null;
  }
  
  // processor fields
  
  @Override
  public Entity getValue() throws ElException {
    return value;
  }
  
  @Override
  public ClassEntity getType() throws ElException {
    return type.getType();
  }
  
  @Override
  public ID getObject() throws ElException {
    return isField() ? fieldID : id;
  }
  
  @Override
  public int getIndex() throws ElException {
    return index;
  }
  
  // processing
  
  @Override
  public void process() throws ElException {
    if(log) print("", "");
    addToScope(name, this);
    currentProcessor.call(this);
  }

  // type conversion
  
  @Override
  public Variable toVariable() {
    return this;
  }
  
  // moving functions
  
  @Override
  public void move(Entity entity) throws base.ElException {
    entity.moveToVariable(this);
  }

  @Override
  public void moveToClass(ClassEntity classEntity) {
    parentClass = classEntity;
    index = classEntity.allocation;
    classEntity.allocation++;
    classEntity.fields.add(this);
  }

  @Override
  public void moveToFunction(Function function) {
    index = function.allocation;
    function.allocation++;
    function.parameters.add(this);
  }

  @Override
  public void moveToFormula(Formula formula) {
    formula.chunks.add(new Link(this));
  }

  @Override
  public void moveToCode(Code code) {
    index = currentAllocation;
    currentAllocation++;
    code.lines.add(this);
  }

  @Override
  public void moveToBlock() throws ElException {
    index = currentAllocation;
    currentAllocation++;
  }
  
  // other

  @Override
  public String toString() {
    return (isField() ? "this." : "") + name.string;
  }
  
  @Override
  public void print(String indent, String prefix) {
    println(indent + prefix + type + " " + toString() + ":" + index
        + (value == null ? "" : " = " + value) + ";");
  }
}
