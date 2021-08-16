package vm;

import base.ElException;
import javax.swing.JOptionPane;

public class AskInt extends VMCommand {
  @Override
  public void execute() throws ElException {
    i64Stack[stackPointer] = Long.parseLong(JOptionPane.showInputDialog(null
        , stringStack[stackPointer]));
    if(log) typeStack[stackPointer] = ValueType.I64;
    currentCommand++;
  }
}
