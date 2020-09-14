package vm;

public class I64ToString extends Command {
  @Override
  public Command execute() {
    stringStack[stackPointer] = String.valueOf(i64Stack[stackPointer]);
    typeStack[stackPointer] = TYPE_STRING;
    return nextCommand;
  }
}
