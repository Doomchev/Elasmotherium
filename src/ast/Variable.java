package ast;

import static ast.Entity.addCommand;
import vm.I64Equate;
import vm.ObjectEquate;

public class Variable extends NamedEntity {
  public Entity type, value = null;
  public Code code = null;
  public Link definition;
  public boolean isThis = false;
  public ClassEntity parentClass = null;
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
  public int getIndex() {
    return index;
  }

  @Override
  public Variable toVariable() {
    return this;
  }

  @Override
  public void setFlag(ID flag) {
    if(flag == thisID) isThis = true;
  }
  
  @Override
  public void setIndex(int index) {
    this.index = index;
  }
  

  @Override
  public void resolveLinks(Variables variables) {
    type = type.toClass();
    if(value != null) value.resolveLinks(variables);
    currentFunction.varIndex++;
    //System.out.println(currentFunction.name + " - " + name);
    index = currentFunction.varIndex;
    variables.list.addFirst(this);
  }
  
  
  @Override
  public void move(Entity entity) {
    entity.moveToVariable(this);
  }

  @Override
  public void moveToClass(ClassEntity classEntity) {
    parentClass = classEntity;
    classEntity.fields.add(this);
  }

  @Override
  public void moveToFunction(Function function) {
    function.parameters.add(this);
  }

  @Override
  public void moveToFormula(Formula formula) {
    formula.chunks.add(new Link(this));
  }

  @Override
  public void moveToCode(Code code) {
    code.lines.add(this);
  }

  @Override
  public void toByteCode() {
    value.toByteCode();
    conversion(value.getType(), type);
    ClassEntity objectClass = type.toClass();
    if(objectClass == ClassEntity.i64Class) {
      addCommand(new I64Equate(index));
    } else if(objectClass.isNative) {
      throw new Error(type.getName()
          + " variable initialization is not implemented.");
    } else {
      addCommand(new ObjectEquate(index));
    }
  }

  @Override
  public String toString() {
    return name.string;
  }
}
