package vm;

public class I64StackMoveReturnValue extends Command {
  @Override
  public Command execute() {
    i64Stack[currentCall.paramPosition] = i64Stack[stackPointer];
    typeStack[currentCall.paramPosition] = TYPE_I64;
    return nextCommand;
  }
}
