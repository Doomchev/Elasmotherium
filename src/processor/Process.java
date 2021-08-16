package processor;

import ast.ID;
import base.ElException;

public class Process extends ProCommand {
  ID name;

  public Process(ID name) {
    this.name = name;
  }
  
  @Override
  Process create(String param) throws ElException {
    return new Process(ID.get(param));
  }
  
  @Override
  void execute() throws ElException {
    current.getBlockParameter(name).process();
  }
}
