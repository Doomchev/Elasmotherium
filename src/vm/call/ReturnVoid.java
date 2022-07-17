package vm.call;

import ast.Entity;
import exception.ElException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class ReturnVoid extends VMCommand {
  public ReturnVoid(int proLine, Entity entity) {
    super(proLine, entity);
  }

  @Override
  public VMCommand create(ProParameter parameter, int proLine, Entity entity) throws ElException {
    return new ReturnVoid(proLine, entity);
  }
  
  @Override
  public void execute() {
    currentCall.returnFromCall(0);
  }
}
