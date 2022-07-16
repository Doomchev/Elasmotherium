package vm;

import exception.ElException;
import exception.EntityException;
import processor.parameter.ProParameter;

public class IfFalseGoTo extends VMCommand {
  private int command;

  public IfFalseGoTo() {
    super();
  }

  @Override
  public VMCommand create(ProParameter parameter)
      throws ElException, EntityException {
    IfFalseGoTo goTo = new IfFalseGoTo();
    parameter.addLabelCommand(goTo);
    return goTo;
  }
  
  @Override
  public void setPosition(int command) {
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
