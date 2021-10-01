package vm.texture;

import ast.exception.ElException;
import vm.VMCommand;

public class TextureDraw extends VMCommand {
  @Override
  public VMCommand create() throws ElException {
    usesWindow = true;
    return super.create();
  }
  
  @Override
  public void execute() throws ElException {
    stackPointer -= 9;
    int tx = (int) i64Stack[stackPointer + 2];
    int ty = (int) i64Stack[stackPointer + 3];
    int sx = (int) i64Stack[stackPointer + 6];
    int sy = (int) i64Stack[stackPointer + 7];
    currentGraphics.drawImage(objectStack[stackPointer + 1].getImage()
        , tx, ty
        , tx + (int) i64Stack[stackPointer + 4]
        , ty + (int) i64Stack[stackPointer + 5]
        , sx, sy
        , sx + (int) i64Stack[stackPointer + 8]
        , sy + (int) i64Stack[stackPointer + 9]
        , null);
    currentCommand++;
  }
}
