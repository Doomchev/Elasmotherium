package vm;

import base.ElException;
import processor.ProParameter;

public class StringVarEquate extends VMCommand {
  private final int index;

  public StringVarEquate(int index) {
    this.index = index;
  }
  
  @Override
  public VMCommand create(ProParameter parameter) throws ElException {
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
