package processor;

import ast.ClassEntity;
import ast.ID;
import base.ElException;

public class GetField extends ProCommand {
  public static final GetField instance = new GetField();

  private GetField() {
  }

  @Override
  ProCommand create(String param) throws ElException {
    return new GetField();
  }

  @Override
  void execute() throws ElException {
    ID id = current.getParameter(1).getID();
    ClassEntity type = object.getType();
    current = type.getField(id);
    if(current == null) current = type.getMethod(id);
    if(current == null)
      throw new ElException(type + "." + id + " not found.");
  }  
}
