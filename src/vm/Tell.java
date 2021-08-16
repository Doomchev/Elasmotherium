package vm;

import javax.swing.JOptionPane;

public class Tell extends VMCommand {
  @Override
  public void execute() {
    JOptionPane.showMessageDialog(null, stringStack[stackPointer]);
    stackPointer--;
    currentCommand++;
  }
}
