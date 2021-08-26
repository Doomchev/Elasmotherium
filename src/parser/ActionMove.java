package parser;

import ast.Entity;
import ast.Function;
import ast.ID;
import base.ElException;

public class ActionMove extends Action {
  private final EntityStack<Entity> from, to;
  private final boolean copy;

  public ActionMove(EntityStack<Entity> from, EntityStack<Entity> to, boolean copy) {
    this.from = from;
    this.to = to;
    this.copy = copy;
  }
  
  @Override
  public Action create(String params) throws ElException {
    String[] param = params.split(",");
    EntityStack<Entity> stack0 = EntityStack.all.get(ID.get(param[0]));
    if(stack0 == null) {
      ID id0 = ID.get(param[0]);
      Function function = Function.all.get(id0);
      if(function == null) {
        function = new Function(id0);
        function.priority = 0;
        Function.all.put(id0, function);
      }
      return new ActionMoveNewFunction(function, EntityStack.get(param[1]));
    } else {
      if(param.length == 1) {
        return new ActionMove(stack0, stack0, copy);
      } else {
        if(param.length != 2) throw new ElException(
            "MOVE command requires 2 parameters");
        return new ActionMove(stack0, EntityStack.get(param[1]), copy);
      }
    }
  }
  
  @Override
  public void execute() throws base.ElException {
    currentAction = this;
    Entity entity = copy ? from.peek() : from.pop();
    if(log) log("MOVING " + from.name.string + " to " + to.name.string + "("
        + to.peek().toString() + ")");
    to.peek().move(entity);
    currentAction = nextAction;
  }

  @Override
  public String toString() {
    return from.name + " to " + to.name;
  }
}
