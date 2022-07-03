package vm.bool;

import vm.VMCommand;

public class Or extends VMCommand {
  @Override
  public void execute() {
    stackPointer--;
    booleanStack[stackPointer]
        = booleanStack[stackPointer] || booleanStack[stackPointer + 1];
    if(log) typeStack[stackPointer] = ValueType.BOOLEAN;
    currentCommand++;
  }
}
