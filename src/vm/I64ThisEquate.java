package vm;

import base.ElException;

public class I64ThisEquate extends VMCommand {
  int index;

  public I64ThisEquate(int index) {
    this.index = index;
  }
  
  @Override
  public void execute() throws ElException {
    currentCall.thisObject.fields[index].i64Set(i64Stack[stackPointer]);
    stackPointer--;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
