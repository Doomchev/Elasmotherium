package vm;

import javax.swing.JOptionPane;

public class VMShowMessage extends VMCommand {
  @Override
  public void execute() {
    JOptionPane.showMessageDialog(null, stringStack[stackPointer]);
    stackPointer--;
    currentCommand = nextCommand;
  }
}
