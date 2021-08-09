package vm;

import base.ElException;

public class I64ThisIncrement extends VMCommand {
  int index;

  public I64ThisIncrement(int index) {
    this.index = index;
  }
  
  @Override
  public void execute() throws ElException {
    currentCall.thisObject.fields[index].increment();
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
