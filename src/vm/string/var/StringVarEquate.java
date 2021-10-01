package vm.string.var;

import ast.exception.ElException;
import ast.exception.EntityException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class StringVarEquate extends VMCommand {
  private final int index;

  public StringVarEquate(int index) {
    this.index = index;
  }
  
  @Override
  public VMCommand create(ProParameter parameter)
      throws ElException, EntityException {
    return new StringVarEquate(parameter.getValue().getIndex());
  }
  
  @Override
  public void execute() {
    stringStack[currentCall.varIndex(index)] = stringStack[stackPointer];
    if(log) typeStack[currentCall.varIndex(index)] = ValueType.STRING;
    stackPointer--;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
