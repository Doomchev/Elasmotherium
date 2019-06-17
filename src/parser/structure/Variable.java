package parser.structure;

public class Variable extends FlagEntity {
  public Entity type, value = null;
  public Code code = null;
  public Link definition;
  public boolean isClassField = false;
  
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
  public Entity setTypes(Scope parentScope) {
    if(type == null) type = value.setTypes(parentScope);
    if(type.toClass() == null) type.setTypes(parentScope);
    return type;
  }
  
  @Override
  public void move(Entity entity) {
    entity.moveToVariable(this);
  }

  @Override
  void moveToClass(ClassEntity classEntity) {
    isClassField = true;
    classEntity.variables.add(this);
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
  public String toString() {
    return name.string;
  }
}
