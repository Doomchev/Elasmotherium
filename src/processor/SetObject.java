package processor;

import base.ElException;

public class SetObject extends ProCommand {
  static final SetObject instance = new SetObject();

  private SetObject() {
  }

  @Override
  ProCommand create(String param) throws ElException {
    return new SetObject();
  }

  @Override
  void execute() throws ElException {
    object = current;
    if(log) log("Set object to " + object);
  }
}
