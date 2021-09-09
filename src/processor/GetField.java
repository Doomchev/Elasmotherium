package processor;

import ast.ClassEntity;
import ast.Entity;
import ast.ID;
import ast.function.FunctionCall;
import base.ElException;

public class GetField extends ProCommand {
  static final GetField instance = new GetField(null);

  private final ProParameter parameter;

  private GetField(ProParameter parameter) {
    this.parameter = parameter;
  }
  
  @Override
  ProCommand create(String param) throws ElException {
    return new GetField(ProParameter.get(param));
  }

  @Override
  void execute() throws ElException {
    Entity parameterValue = parameter.getValue();
    ID id = parameterValue.getName();
    ClassEntity classEntity = (ClassEntity) object.getType();
    Entity field;
    try {
      field = classEntity.getField(id);
    } catch(ElException ex) {
      field = classEntity.getMethod(id, currentCall.getParametersQuantity());
    }
    try {
      FunctionCall call = (FunctionCall) parameterValue;
      if(log) println("Set call function to " + field + ".");
      call.setFunction(field);
    } catch(ClassCastException ex) {
      if(log) println("Set current object to " + field + " (as field of "
          + object + ").");
      parameter.setValue(field);
    }
  }  
}
