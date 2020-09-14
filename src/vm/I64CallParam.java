package vm;

public class I64CallParam extends Command {
  @Override
  public Command execute() {
    currentCall.i64ParamPosition = i64StackPointer + 1;
    return nextCommand;
  }
}
