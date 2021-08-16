package processor;

import ast.ID;
import java.util.LinkedList;
import vm.VMCommand;

public class ProLabel extends ProParameter {
  public static LinkedList<ProLabel> all = new LinkedList<>();
  
  private final ID name;
  private final LinkedList<VMCommand> commands = new LinkedList<>();
  private int position;

  ProLabel(ID name) {
    this.name = name;
  }
  
  static ProLabel get(ID name) {
    for(ProLabel label: all) if(label.name == name) return label;
    ProLabel label = new ProLabel(name);
    all.add(label);
    return label;
  }

  static void addCommand(ID id, VMCommand command) {
    get(id).commands.add(command);
  }

  static void setPosition(ID id, int pos) {
    get(id).position = pos;
  }
  
  static void apply() {
    for(ProLabel label: all)
      for(VMCommand command: label.commands) command.setGoto(label.position);
  }

  @Override
  public String toString() {
    return name + "(" + position + ")";
  }
}