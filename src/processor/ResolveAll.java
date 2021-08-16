package processor;

import base.ElException;

public class ResolveAll extends ProCommand {
  @Override
  void execute() throws ElException {
    if(log) log("resolveAll");
    current.resolveAll();
  }
}
