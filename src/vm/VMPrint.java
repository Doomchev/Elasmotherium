package vm;

public class VMPrint extends VMCommand {
  @Override
  public void execute() {
    System.out.println(stringStack[stackPointer]);
    stackPointer--;
    currentCommand = nextCommand;
  }
}
