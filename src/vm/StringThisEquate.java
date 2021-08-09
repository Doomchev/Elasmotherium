package vm;

import base.ElException;

public class StringThisEquate extends VMCommand {
  int index;

  public StringThisEquate(int index) {
    this.index = index;
  }
  
  @Override
  public void execute() throws ElException {
    currentCall.thisObject.fields[index].stringSet(stringStack[stackPointer]);
    stackPointer--;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
