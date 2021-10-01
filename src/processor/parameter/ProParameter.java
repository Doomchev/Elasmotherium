package processor.parameter;

import processor.block.BlockVariable;
import processor.block.BlockLabel;
import processor.block.BlockParameter;
import ast.ClassEntity;
import ast.Entity;
import ast.ID;
import ast.exception.ElException;
import ast.exception.ElException.Cannot;
import ast.exception.ElException.CannotGet;
import ast.exception.ElException.MethodException;
import ast.exception.EntityException;
import ast.exception.NotFound;
import processor.ProBase;
import vm.VMCommand;

public abstract class ProParameter extends ProBase {
  public static ProParameter get(String name) throws ElException {
    if(name.isEmpty()) return ProThis.instance;
    if(name.startsWith("$")) return new BlockParameter(name.substring(1));
    if(name.startsWith("#")) return new BlockLabel(name.substring(1));
    if(name.startsWith("@")) return new BlockVariable(name.substring(1));
    switch(name) {
      case "this":
        return ProThis.instance;
      case "param":
        return ProParam.instance;
      case "value":
        return ProVariableValue.instance;
      case "currentFunction":
        return ProCurrentFunction.instance;
      case "object":
        return ProObject.instance;
      case "last":
        return ProLast.instance;
      default:
        if(name.startsWith("v")) {
          return new ProCallParameter(name.substring(1));
        } else {
          ClassEntity classEntity = ClassEntity.all.get(ID.get(name));
          if(classEntity != null) return new ProClass(classEntity);
          throw new MethodException("ProParameter", "get"
              , name + " is not found.");
        }
    }
  }
  
  public Entity getType(Entity[] subTypes) throws ElException, EntityException {
    return getValue().getType(subTypes);
  }
  
  public ClassEntity getNativeClass() throws ElException, EntityException {
    return getValue().getNativeClass();
  }

  public int getIndex() throws ElException, EntityException {
    return getValue().getIndex();
  }
  
  public Entity getValue() throws ElException, EntityException {
    throw new CannotGet("value", this);
  }

  public void setValue(Entity value) throws ElException {
    throw new Cannot("set value of", this);
  }
  
  public ID getID() throws ElException {
    throw new CannotGet("id", this);
  }
  
  public void addLabelCommand(VMCommand command)
      throws ElException, EntityException {
    throw new Cannot("add label command to", this);
  }
}
