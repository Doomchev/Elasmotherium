package vm;

public class I64IsEqual extends Command {
  @Override
  public void execute() {
    stackPointer--;
    booleanStack[stackPointer] = i64Stack[stackPointer]
        == i64Stack[stackPointer + 1];
    typeStack[stackPointer] = TYPE_BOOLEAN;
    currentCommand = nextCommand;
  }
}
