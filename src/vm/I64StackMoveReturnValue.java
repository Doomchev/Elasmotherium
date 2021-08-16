package vm;

public class I64StackMoveReturnValue extends VMCommand {
  @Override
  public void execute() {
    i64Stack[currentCall.paramPosition] = i64Stack[stackPointer];
    if(log) typeStack[currentCall.paramPosition] = ValueType.I64;
    currentCommand++;
  }
}
