package vm;

public class I64StackMoveReturnValue extends Command {
  @Override
  public Command execute() {
    i64Stack[currentCall.i64ParamPosition] = i64Stack[i64StackPointer];
    return nextCommand;
  }
}
