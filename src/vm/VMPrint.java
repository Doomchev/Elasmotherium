package vm;

public class VMPrint extends Command {
  @Override
  public Command execute() {
    System.out.println(stringStack[stackPointer]);
    stackPointer--;
    return nextCommand;
  }
}
