package vm.function;

import exception.ElException;
import exception.EntityException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class Println extends VMCommand {
  @Override
  public VMCommand create(ProParameter parameter)
      throws ElException, EntityException {
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
