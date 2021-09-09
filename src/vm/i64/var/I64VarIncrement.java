package vm.i64.var;

import base.ElException;
import processor.ProParameter;
import vm.VMCommand;

public class I64VarIncrement extends VMCommand {
  private final int index;

  public I64VarIncrement(int index) {
    this.index = index;
  }
  
  @Override
  public VMCommand create(ProParameter parameter) throws ElException {
    return new I64VarIncrement(parameter.getValue().getIndex());
  }
  
  @Override
  public void execute() {
    i64Stack[currentCall.varIndex(index)]++;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
