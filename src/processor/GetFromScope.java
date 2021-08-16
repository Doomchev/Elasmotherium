package processor;

import ast.Entity;
import base.ElException;

public class GetFromScope extends ProCommand {
  @Override
  void execute() throws ElException {
    if(log) log("getFromScope");
    Entity entity = getFromScope(current.getID());
    current.resolveTo(entity);
    current = entity;
  }
}
