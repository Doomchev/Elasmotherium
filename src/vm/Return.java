package vm;

import base.ElException;
import processor.ProParameter;

public class Return extends VMCommand {
  @Override
  public VMCommand create(ProParameter parameter) throws ElException {
    return new Return();
  }
  
  @Override
  public void execute() {
    currentCall.returnFromCall(0);
  }
}
