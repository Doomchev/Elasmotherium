package vm;

import base.ElException;

public class I64FieldPush extends VMFieldCommand {
  public I64FieldPush(int fieldIndex) {
    super(fieldIndex);
  }
  
  @Override
  public VMCommand create(int index) throws ElException {
    return new I64FieldPush(index);
  }
  
  @Override
  public void execute() throws ElException {
    i64Stack[stackPointer]
        = objectStack[stackPointer].fields[fieldIndex].i64Get();
    if(log) typeStack[stackPointer] = ValueType.I64;
    currentCommand++;
  }
}
