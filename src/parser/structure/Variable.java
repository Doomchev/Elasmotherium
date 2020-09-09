package parser.structure;

import static parser.structure.Entity.addCommand;
import vm.I64Equate;

public class Variable extends FlagEntity {
  public Entity type, value = null;
  public Code code = null;
  public Link definition;
  public boolean isClassField = false;
  public int index = -1;
  
  public Variable(Link link) {
    this.name = link.name;
    this.definition = link;
    addFlags();
  }

  public Variable(ID id) {
    this.name = id;
    addFlags();
  }

  @Override
  boolean isClassField() {
    return isClassField;
  }

  @Override
  public ID getID() {
    return variableID;
  }

  @Override
  public Entity getChild(ID id) {
    if(id == typeID) return type;
    if(id == valueID) return value;
    if(id == codeID) return code;
    return super.getChild(id);
  }

  @Override
  public Entity getType() {
    return type;
  }
  
  @Override
  public int getStackIndex() {
    return index;
  }

  @Override
  public Variable toVariable() {
    return this;
  }

  @Override
  public void addToScope(Scope scope) {
    scope.add(this, name);
  }

  @Override
  public void setTypes(Scope parentScope) {
    if(type.toClass() == null) type.setTypes(parentScope);
    type = type.toClass();
    if(index < 0) setAllocation();
    value.setTypes(parentScope);
  }
  
  @Override
  public void setIndex(int index) {
    this.index = index;
  }
  
  @Override
  public void move(Entity entity) {
    entity.moveToVariable(this);
  }

  @Override
  void moveToClass(ClassEntity classEntity) {
    isClassField = true;
    classEntity.fields.add(this);
  }

  @Override
  void moveToFunction(Function function) {
    function.parameters.add(this);
  }

  @Override
  void moveToFormula(Formula formula) {
    formula.chunks.add(new Link(this));
  }

  @Override
  void moveToCode(Code code) {
    code.lines.add(this);
  }

  @Override
  public void toByteCode() {
    value.toByteCode();
    conversion(value.getType(), type);
    if(type == ClassEntity.i64Class) {
      addCommand(new I64Equate(index));
    } else {
      error(type.getName() + " variable initialization is not implemented.");
    }
  }

  @Override
  public String toString() {
    return name.string;
  }

  public void setAllocation() {
    if(type == i64Class) {
      currentFunction.i64quantity++;
      index = currentFunction.i64quantity;
    } else {
      error(type.getName() + " allocation is not implemented.");
    }
  }
}
