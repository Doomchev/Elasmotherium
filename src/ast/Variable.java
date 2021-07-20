package ast;

public class Variable extends NamedEntity {
  public Entity type, value = null;
  public Code code = null;
  public Link definition;
  public boolean isThis = false;
  public ClassEntity parentClass = null;
  public Function parentFunction;
  
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
  public String toString() {
    return name.string;
  }
  
  @Override
  public void print(String indent, String prefix) {
    println(indent + prefix + type + " " + toString()
        + (value == null ? "" : " = " + value) + ";");
  }
}
