package parser;

import ast.ID;

public class ActionUse extends Action {
  ID flag;

  public ActionUse(ID flag) {
    this.flag = flag;
  }
  
  @Override
  public void execute() {
    currentFlags.add(flag);
    if(log) log("USE flag " + flag.string);
    currentAction = nextAction;
  }
}
