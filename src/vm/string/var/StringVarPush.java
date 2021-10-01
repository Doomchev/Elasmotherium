package vm.string.var;

import ast.exception.ElException;
import ast.exception.EntityException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class StringVarPush extends VMCommand {
  private final int index;

  public StringVarPush(int index) {
    this.index = index;
  }

  @Override
  public VMCommand create(ProParameter parameter)
      throws ElException, EntityException {
    return new StringVarPush(parameter.getIndex());
  }
  
  @Override
  public void execute() {
    stackPointer++;
    stringStack[stackPointer] = stringStack[currentCall.varIndex(index)];
    if(log) typeStack[stackPointer] = ValueType.STRING;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
