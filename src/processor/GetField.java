package processor;

import ast.ClassEntity;
import ast.Entity;
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
    Entity type = object.getType();
    ClassEntity classEntity = type.toClass();
    Entity field = classEntity.getField(id);
    if(field == null) field = classEntity.getMethod(id);
    if(field == null)
      throw new ElException(classEntity + "." + id + " not found.");
    FunctionCall call = parameter1.toCall();
    if(call == null) {
      if(log) println("Set current object to " + field + " (as field of " + object + ").");
      current = field;
    } else {
      if(log) println("Set call function to " + field + ".");
      call.setFunction(field.toFunction());
      current = call;
    }
  }  
}
