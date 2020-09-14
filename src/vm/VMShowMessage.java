package vm;

import javax.swing.JOptionPane;

public class VMShowMessage extends Command {
  @Override
  public Command execute() {
    JOptionPane.showMessageDialog(null, stringStack[stackPointer]);
    stackPointer--;
    return nextCommand;
  }
}
