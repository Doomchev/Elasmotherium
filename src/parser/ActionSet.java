package parser;

import parser.structure.Block;
import parser.structure.Entity;
import parser.structure.EntityStack;
import parser.structure.ID;

public class ActionSet extends Action {
  private static final ID ret = ID.get("return"), get = ID.get("get")
      , def = ID.get("default");
  
  private final EntityStack<? extends Entity> stack;
  private final EntityStack<? extends Entity> value;
  private final ID id;

  public ActionSet(EntityStack stack, ID id, EntityStack value) {
    this.stack = stack;
    this.id = id;
    this.value = value;
  }
  
  @Override
  public Action execute() {
    if(log) log("SET " + id.string + " to " + value.name + "("
        + value.toString() + ")");
    currentAction = this;
    Entity val = value.pop().toValue();
    EntityStack.block.peek().entries.add(new Block.Entry(id, val));
    currentAction = null;
    return nextAction;
  }

  @Override
  public String toString() {
    return stack.name + "." + id.string + " = " + value.name;
  }
}
