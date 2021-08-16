package vm;

import base.ElException;
import processor.ProParameter;

public class IfFalseGoTo extends VMCommand {
  public int command;

  public IfFalseGoTo() {
  }
  
  public IfFalseGoTo(int command) {
    this.command = command;
  }
  
  @Override
  public IfFalseGoTo create(ProParameter parameter) throws ElException {
    IfFalseGoTo goTo = new IfFalseGoTo();
    parameter.addLabelCommand(goTo);
    return goTo;
  }
  
  @Override
  public void setGoto(int command) {
    this.command = command;
  }
  
  @Override
  public void execute() {
    stackPointer--;
    if(booleanStack[stackPointer + 1]) {
      currentCommand++;
    } else {
      currentCommand = command;
    }
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + command;
  }
}
