package vm;

public class I64StackMoveReturnValue extends VMCommand {
  @Override
  public void execute() {
    i64Stack[currentCall.paramPosition] = i64Stack[stackPointer];
    typeStack[currentCall.paramPosition] = TYPE_I64;
    currentCommand = nextCommand;
  }
}
