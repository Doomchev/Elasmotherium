package processor;

import processor.parameter.ProParameter;
import ast.ClassEntity;
import base.ElException;
import base.ElException.MethodException;
import base.EntityException;
import vm.VMCommand;

public class TypeCommand extends ProCommand {
  private final ProParameter type, parameter;
  private final String postfix;
  
  public TypeCommand(String type, String postfix, String parameter)
      throws ElException {
    this.type = ProParameter.get(type);
    this.postfix = postfix;
    this.parameter = ProParameter.get(parameter);
  }

  public static VMCommand getCommand(ClassEntity type, String postfix
      , ProParameter parameter) throws ElException, EntityException {
    String typeName = type.nativeClass.getName().string;
    if(typeName.equals("Int")) typeName = "I64";
    VMCommand command = Processor.commands.get(typeName + postfix);
    if(command == null)
      throw new MethodException("TypeCommand", "getCommand", "Command "
          + typeName + postfix + " is not found.");
    return command.create(parameter);
  }
  
  @Override
  public void execute() throws ElException, EntityException {
    append(getCommand(type.getNativeClass(), postfix, parameter));
  }
}