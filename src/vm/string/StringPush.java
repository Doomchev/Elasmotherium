package vm.string;

import base.ElException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class StringPush extends VMCommand {
  private final String value;

  public StringPush(String value) {
    this.value = value;
  }
  
  @Override
  public VMCommand create(ProParameter parameter) throws ElException {
    return new StringPush(parameter.getValue().getStringValue());
  }
  
  @Override
  public void execute() {
    stackPointer++;
    stringStack[stackPointer] = value;
    if(log) typeStack[stackPointer] = ValueType.STRING;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " \"" + value + "\"";
  }
}
