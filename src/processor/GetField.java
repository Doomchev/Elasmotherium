package processor;

import ast.ClassEntity;
import ast.Entity;
import ast.Function;
import ast.FunctionCall;
import ast.ID;
import base.ElException;

public class GetField extends ProCommand {
  static final GetField instance = new GetField();

  private GetField() {
  }

  @Override
  ProCommand create(String param) throws ElException {
    return new GetField();
  }

  @Override
  void execute() throws ElException {
    Entity parameter1 = current.getParameter(1);
    ID id = parameter1.getName();
    ClassEntity classEntity = (ClassEntity) object.getType();
    Entity field = classEntity.getField(id);
    if(field == null) field = classEntity.getMethod(id);
    if(field == null)
      throw new ElException(classEntity + "." + id + " not found.");
    try {
      FunctionCall call = (FunctionCall) parameter1;
      if(log) println("Set call function to " + field + ".");
      call.setFunction(field);
      current = call;
    } catch(ClassCastException ex) {
      if(log) println("Set current object to " + field + " (as field of "
          + object + ").");
      current = field;
    }
  }  
}
