package vm;

import javax.swing.JOptionPane;

public class VMShowMessage extends Command {
  @Override
  public Command execute() {
    JOptionPane.showMessageDialog(null, stringStack[stringStackPointer]);
    stringStackPointer--;
    return nextCommand;
  }
}
