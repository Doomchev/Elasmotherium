package parser;

import ast.ClassEntity;
import ast.ID;
import ast.Variable;
import exception.ElException;
import exception.EntityException;

public class ActionCreateBlockVariable extends Action {
  private final ID id;

  public ActionCreateBlockVariable(ID id) {
    this.id = id;
  }
  
  @Override
  public Action create(String params) throws ElException {
    return new ActionCreateBlockVariable(ID.get(params));
  }
  
  @Override
  public void execute() throws ElException, EntityException {
    if(log) log("CREATE BLOCK VARIABLE " + id);
    EntityStack.block.peek().add(new Variable(id, ClassEntity.Object));
    currentAction = nextAction;
  }

  @Override
  public String toString() {
    return id.string;
  }
}
