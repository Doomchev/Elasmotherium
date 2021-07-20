package ast;

import java.util.HashMap;
import java.util.LinkedList;
import vm.Command;

public class Function extends NamedEntity {
  public static final HashMap<ID, Function> all = new HashMap<>();
  
  public Code code = new Code();
  public Entity type = null;
  public final LinkedList<Variable> parameters = new LinkedList<>();
  public boolean isConstructor = false;
  public ClassEntity parentClass = null;
  public byte priority = VALUE;
  public Command startingCommand;
  
  public Function(ID name) {
    this.name = name;
  }
  
  public Function() {
    this.isConstructor = true;
  }
  
  @Override
  public ID getID() {
    return functionID;
  }

  @Override
  public byte getPriority() {
    return priority;
  }

  @Override
  public Function toFunction() {
    return this;
  }

  @Override
  public void move(Entity entity) throws base.ElException {
    entity.moveToFunction(this);
  }

  @Override
  public void moveToClass(ClassEntity classEntity) {
    parentClass = classEntity;
    classEntity.methods.add(this);
  }

  @Override
  public void moveToCode(Code code) {
    code.functions.add(this);
  }

  @Override
  public String toString() {
    return isConstructor ? "this" : name.string;
  }
  
  @Override
  public void print(String indent, String prefix) {
    String str = "";
    for(Variable parameter : parameters) {
      if(!str.isEmpty()) str += ", ";
      str += parameter.toString();
    }
    str = (type == null ? "" : type + " ") +
        (isConstructor ? "Constructor" : (parentClass == null ? ""
        : parentClass.name.string + ".") + name.string) + "(" +  str + ")";
    code.print(indent, prefix + str);
  }
}
 