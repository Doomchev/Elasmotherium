package vm;

import javax.swing.JOptionPane;

public class VMInput extends Command {
  @Override
  public Command execute() {
    stringStack[stringStackPointer] = JOptionPane.showInputDialog(stringStack[
        stringStackPointer]);
    return nextCommand;
  }
}
