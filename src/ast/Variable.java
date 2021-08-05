package ast;

import base.ElException;

public class Variable extends NamedEntity {
  public Entity type, value = null;
  public Code code = null;
  public Link definition;
  public boolean isThis = false;
  public ClassEntity parentClass = null;
  public Function parentFunction;
  public int index;
  
  public Variable(Link link) {
    this.name = link.name;
    this.definition = link;
  }

  public Variable(ID id) {
    this.name = id;
  }

  public Variable(ID id, boolean isThis) {
    this.name = id;
    this.isThis = isThis;
  }
  
  @Override
  public ID getID() {
    return variableID;
  }

  @Override
  public Variable toVariable() {
    return this;
  }
  
  
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
    index = function.parameters.size();
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

  @Override
  public String toString() {
    return name.string;
  }
  
  @Override
  public void print(String indent, String prefix) {
    println(indent + prefix + type + " " + toString() + "(" + index + ")"
        + (value == null ? "" : " = " + value) + ";");
  }
}
