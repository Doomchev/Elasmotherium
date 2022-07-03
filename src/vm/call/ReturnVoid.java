package vm.call;

import exception.ElException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class ReturnVoid extends VMCommand {
  @Override
  public VMCommand create(ProParameter parameter) throws ElException {
    return new ReturnVoid();
  }
  
  @Override
  public void execute() {
    currentCall.returnFromCall(0);
  }
}
