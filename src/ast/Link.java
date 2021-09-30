package ast;

import ast.function.CustomFunction;
import base.ElException;
import java.util.LinkedList;

public class Link extends Value {
  public static final ID id = ID.get("link");
  
  private final ID name;
  private final LinkedList<Link> subTypes = new LinkedList<>();

  public Link(IDEntity id) {
    super(id);
    this.name = id.value;
  }
  
  // child objects

  void addSubType(Link type) {
    subTypes.add(type);
  }
  
  // properties
  
  @Override
  public ID getName() throws ElException {
    return name;
  }
  
  @Override
  public ID getID() throws ElException {
    return id;
  }
  
  @Override
  public Entity getType(Entity[] subTypes) throws ElException {
    return resolve().getType(subTypes);
  }
  
  // processing
  
  @Override
  public Entity resolve() throws ElException {
    if(subTypes.isEmpty()) return getVariableFromScope(name);
    ClassEntity basicClass = getClassFromScope(name);
    Entity[] resolvedTypes = new Entity[subTypes.size()];
    int index = 0;
    for(Link subType: subTypes) {
      resolvedTypes[index] = subType.resolve();
      index++;
    }
    return new Type(basicClass, resolvedTypes);
  }
  
  @Override
  public Entity resolveFunction(int parametersQuantity)
      throws ElException {
    return getFunctionFromScope(name, parametersQuantity);
  }

  @Override
  public void resolve(Entity type) throws ElException {
    getVariableFromScope(name).resolve(type);
  }
  
  @Override
  public Entity resolveRecursively() throws ElException {
    return resolve();
  }

  @Override
  public Entity resolveRecursively(int parametersQuantity) throws ElException {
    return resolveFunction(parametersQuantity);
  }

  @Override
  public Entity getObject() throws ElException {
    return resolve().getObject();
  }
  
  // moving functions

  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToLink(this);
  }

  @Override
  public void moveToLink(Link link) throws ElException {
    link.addSubType(this);
  }

  @Override
  public void moveToVariable(Variable variable) {
    variable.setType(this);
  }

  @Override
  public void moveToFunction(CustomFunction function) throws ElException {
    function.setReturnType(this);
  }
  
  // other
  
  @Override
  public String toString() {
    return "#" + name.string
        + (subTypes.isEmpty() ? "" : "<" + listToString(subTypes) + ">");
  }
}
