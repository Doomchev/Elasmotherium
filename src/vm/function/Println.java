package vm.function;

import ast.Entity;
import exception.ElException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class Println extends VMCommand {
  public Println() {
  }

  public Println(int proLine, Entity entity) {
    super(proLine, entity);
  }

  @Override
  public VMCommand create(ProParameter parameter, int proLine, Entity entity)
      throws ElException {
    usesConsole = true;
    return super.create(parameter, proLine, entity);
  }
  
  @Override
  public void execute() {
    System.out.println(stringStack[stackPointer]);
    stackPointer--;
    currentCommand++;
  }
}
