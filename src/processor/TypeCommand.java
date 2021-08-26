package processor;

import ast.ClassEntity;
import base.ElException;
import static processor.Processor.commands;
import vm.VMCommand;

public class TypeCommand extends ProCommand {
  private final ProParameter type;
  private final String postfix;
  
  public TypeCommand(String type, String postfix)
      throws ElException {
    this.type = ProParameter.get(type);
    this.postfix = postfix;
  }

  public static VMCommand getCommand(ClassEntity type, String postfix)
      throws ElException {
    String typeName = type.toNativeClass().getName().string;
    if(typeName.equals("Int")) typeName = "I64";
    VMCommand command = commands.get(typeName + postfix);
    if(command == null)
      throw new ElException("Command " + typeName + postfix + " is not found.");
    return command.create(ProThis.instance);
  }
  
  @Override
  void execute() throws ElException {
    append(getCommand(type.getValue().getType(), postfix));
  }
}