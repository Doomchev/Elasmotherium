package processor;

import ast.ClassEntity;
import ast.Entity;
import ast.ID;
import base.ElException;
import vm.VMCommand;

public abstract class ProParameter extends ProBase {
  static ProParameter get(String name) throws ElException {
    if(name.isEmpty()) return ProThis.instance;
    if(name.startsWith("#")) return new ProID(name);
    if(name.startsWith("$")) return new ProBlockParameter(name);
    switch(name) {
      case "this":
        return ProThis.instance;
      case "param":
        return ProParam.instance;
      case "value":
        return ProVariableValue.instance;
      case "function":
        return ProCurrentFunction.instance;
      default:
        if(name.startsWith("v")) {
          return new ProCallParameter(name.substring(1));
        } else {
          ClassEntity classEntity = ClassEntity.all.get(ID.get(name));
          if(classEntity != null) return new ProClass(classEntity);
          throw new ElException(name + " is not found.");
        }
    }
  }
  
  public ClassEntity getType() throws ElException {
    return getValue().getType().toClass();
  }
  
  public Entity getValue() throws ElException {
    throw new ElException("Cannot get value from", this);
  }
  
  public ID getID() throws ElException {
    throw new ElException("Cannot get id from", this);
  }
  
  public void addLabelCommand(VMCommand command) throws ElException {
    throw new ElException("Cannot add label command to", this);
  }
}
