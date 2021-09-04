package vm.string;

import vm.VMCommand;

public class StringAdd extends VMCommand {
  @Override
  public void execute() {
    stackPointer--;
    stringStack[stackPointer] += stringStack[stackPointer + 1];
    currentCommand++;
  }
}
