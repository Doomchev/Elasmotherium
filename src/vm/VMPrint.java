package vm;

public class VMPrint extends Command {
  @Override
  public void execute() {
    System.out.println(stringStack[stackPointer]);
    stackPointer--;
    currentCommand = nextCommand;
  }
}
