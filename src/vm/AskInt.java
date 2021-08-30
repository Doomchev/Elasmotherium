package vm;

import base.ElException;
import javax.swing.JOptionPane;

public class AskInt extends VMCommand {
  @Override
  public void execute() throws ElException {
    try {
      i64Stack[stackPointer] = Long.parseLong(JOptionPane.showInputDialog(null
         , stringStack[stackPointer]));
    } catch(NumberFormatException ex) {
      System.exit(1);
    }
    if(log) typeStack[stackPointer] = ValueType.I64;
    currentCommand++;
  }
}
