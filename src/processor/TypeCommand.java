package processor;

import ast.ClassEntity;
import base.ElException;
import static processor.Processor.commands;
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
      , ProParameter parameter)
      throws ElException {
    String typeName = type.getNativeClass().getName().string;
    if(typeName.equals("Int")) typeName = "I64";
    VMCommand command = commands.get(typeName + postfix);
    if(command == null)
      throw new ElException("Command " + typeName + postfix + " is not found.");
    return command.create(parameter);
  }
  
  @Override
  void execute() throws ElException {
    append(getCommand(type.getNativeClass(), postfix, parameter));
  }
}