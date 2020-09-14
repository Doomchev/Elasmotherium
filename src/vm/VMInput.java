package vm;

import javax.swing.JOptionPane;

public class VMInput extends Command {
  @Override
  public Command execute() {
    stringStack[stackPointer] = JOptionPane.showInputDialog(stringStack[
        stackPointer]);
    typeStack[stackPointer] = TYPE_STRING;
    return nextCommand;
  }
}
