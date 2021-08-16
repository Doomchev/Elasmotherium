package processor;

import ast.ClassEntity;
import ast.ID;
import base.ElException;
import base.SimpleMap;
import base.SimpleMap.Entry;
import java.util.LinkedList;

class Switch extends ProCommand {
  private final ProParameter parameter;
  private final SimpleMap<ClassEntity, LinkedList<ProCommand>> cases
      = new SimpleMap<>();
  
  public Switch(String parameter) throws ElException {
    this.parameter = ProParameter.get(parameter);
  }

  LinkedList<ProCommand> addCase(String type) throws ElException {
    ClassEntity classEntity = ClassEntity.all.get(ID.get(type));
    if(classEntity == null)
      throw new ElException("Type " + type + " not found.");
    LinkedList<ProCommand> code = new LinkedList<>();
    cases.put(classEntity, code);
    return code;
  }

  @Override
  void execute() throws ElException {
    ClassEntity type = parameter.getValue().getType();
    if(log) {
      log("switch to " + type.name);
      subIndent += "| ";
    }
    for(Entry<ClassEntity, LinkedList<ProCommand>> entry : cases.entries) {
      if(entry.key == type) {
        currentProcessor.executeCode(entry.value);
        if(log) subIndent = subIndent.substring(2);
        return;
      }
    }
    throw new ElException("No case " + type + " for switch.");
  }
}
