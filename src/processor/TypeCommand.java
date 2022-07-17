package processor;

import ast.ClassEntity;
import exception.ElException;
import exception.ElException.MethodException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class TypeCommand extends ProCommand {
  private final ProParameter type, parameter;
  private final String postfix;
  
  public TypeCommand(String type, String postfix, String parameter, int proLine)
      throws ElException {
    super(proLine);
    this.type = ProParameter.get(type);
    this.postfix = postfix;
    this.parameter = ProParameter.get(parameter);
  }

  public static VMCommand getCommand(ClassEntity type, String postfix
      , ProParameter parameter, int proLine) throws ElException {
    String typeName = type.nativeClass.getName().string;
    if(typeName.equals("Int")) typeName = "I64";
    if(typeName.equals("Float")) typeName = "F64";
    VMCommand command = Processor.commands.get(typeName + postfix);
    if(command == null)
      throw new MethodException("TypeCommand", "getCommand", "Command "
          + typeName + postfix + " is not found.");
    return command.create(parameter, proLine, null);
  }
  
  @Override
  public void execute() throws ElException {
    append(getCommand(type.getNativeClass(), postfix, parameter, line));
  }
}