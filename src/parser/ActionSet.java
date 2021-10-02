package parser;

import ast.Block;
import ast.Entity;
import ast.ID;
import exception.ElException;
import exception.ElException.ActionException;

public class ActionSet extends Action {
  private final EntityStack<? extends Entity> value;
  private final ID id;

  public ActionSet(ID id, EntityStack value) {
    this.id = id;
    this.value = value;
  }
  
  @Override
  public Action create(String params) throws ElException {
    String[] param = params.split(",");
    if(param.length == 1) return new ActionSet(ID.get(param[0]), null);
    if(param.length == 2)
      return new ActionSet(ID.get(param[0]), EntityStack.get(param[1]));
    throw new ActionException(this, "SET", "requires 2 parameters");
    
  }
  
  @Override
  public void execute() throws exception.ElException {
    if(log) log(value == null ? "SET BLOCK TYPE TO " + id
        : "SET " + id + " to " + value.name + "(" + value + ")");
      
    currentAction = this;
    Block block = EntityStack.block.peek();
    if(value == null) {
      block.type = id;
    } else {
      block.set(id, value.pop().getFormulaValue());
    }
    currentAction = nextAction;
  }

  @Override
  public String toString() {
    return "block." + id.string + " = " + value.name;
  }
}
