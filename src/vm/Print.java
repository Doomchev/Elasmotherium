package vm;

import base.ElException;
import processor.ProParameter;

public class Print extends VMCommand {

  @Override
  public VMCommand create(ProParameter parameter) throws ElException {
    usesConsole = true;
    return super.create(parameter);
  }
  
  @Override
  public void execute() {
    System.out.println(stringStack[stackPointer]);
    stackPointer--;
    currentCommand++;
  }
}
