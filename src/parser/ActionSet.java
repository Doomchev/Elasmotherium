package parser;

import ast.Entity;
import ast.EntityStack;
import ast.ID;

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
  public void execute() throws base.ElException {
    if(log) log("SET " + id.string + " to " + value.name + "("
        + value.toString() + ")");
    currentAction = this;
    Entity val = value.pop().toValue();
    EntityStack.block.peek().entries.put(id, val);
    currentAction = nextAction;
  }

  @Override
  public String toString() {
    return "block." + id.string + " = " + value.name;
  }
}
