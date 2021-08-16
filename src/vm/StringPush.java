package vm;

import base.ElException;
import processor.ProParameter;

public class StringPush extends VMCommand {
  String value = "";

  public StringPush(String value) {
    this.value = value;
  }
  
  @Override
  public StringPush create(ProParameter parameter) throws ElException {
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
