package ast;

import ast.function.CustomFunction;
import ast.function.FunctionCall;
import base.ElException;
import java.util.LinkedList;

public class Link extends Value {
  public static final ID id = ID.get("link");
  
  private final ID name;
  private final LinkedList<Link> subTypes = new LinkedList<>();

  public Link(ID name) {
    this.name = name;
  }
  
  // properties
  
  @Override
  public ID getName() throws ElException {
    return name;
  }

  void addSubType(Link type) {
    subTypes.add(type);
  }
  
  // processor fields
  
  @Override
  public ID getID() throws ElException {
    return id;
  }
  
  @Override
  public Entity getType() throws ElException {
    return resolve().getType();
  }
  
  // processing
  
  @Override
  public Entity resolve() throws ElException {
    if(subTypes.isEmpty()) return getFromScope(name);
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
  public Entity resolve(int parametersQuantity) throws ElException {
    return getFromScope(paramName(name, parametersQuantity));
  }
  
  @Override
  public void call(FunctionCall call) throws ElException {
    resolve(call.getParametersQuantity()).call(call);
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
