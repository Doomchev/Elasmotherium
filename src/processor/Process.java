package processor;

import ast.ID;
import base.ElException;

public class Process extends ProCommand {
  public static final Process instance = new Process(null);
  
  ID name;

  private Process(ID name) {
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
