package parser;

import ast.Entity;
import ast.EntityStack;
import ast.ID;
import base.ElException;

public class ActionSet extends Action {
  private static final ID ret = ID.get("return"), get = ID.get("get")
      , def = ID.get("default");
  
  private final EntityStack<? extends Entity> value;
  private final ID id;

  public ActionSet(ID id, EntityStack value) {
    this.id = id;
    this.value = value;
  }
  
  @Override
  public ActionSet create(String params) throws ElException {
    String[] param = params.split(",");
    if(param.length != 2) throw new ElException(
        "SET command requires 2 parameters");
    return new ActionSet(ID.get(param[0]), EntityStack.get(param[1]));
  }
  
  @Override
  public void execute() throws base.ElException {
    if(log) log("SET " + id.string + " to " + value.name + "("
        + value.toString() + ")");
    currentAction = this;
    Entity val = value.pop().toValue();
    EntityStack.block.peek().set(id, val);
    currentAction = nextAction;
  }

  @Override
  public String toString() {
    return "block." + id.string + " = " + value.name;
  }
}
