package vm.texture;

import ast.exception.ElException;
import vm.VMBase;
import vm.VMCommand;

public class TextureWidth extends VMCommand {
  @Override
  public void execute() throws ElException {
    i64Stack[stackPointer] = objectStack[stackPointer].getImage().getWidth();
    if(log) typeStack[stackPointer] = VMBase.ValueType.I64;
    currentCommand++;
  }
}
