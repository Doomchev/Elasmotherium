package vm.functions;

import javax.swing.JOptionPane;
import vm.VMCommand;

public class Say extends VMCommand {
  @Override
  public void execute() {
    JOptionPane.showMessageDialog(null, stringStack[stackPointer]);
    stackPointer--;
    currentCommand++;
  }
}
