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
  
  public Function(ID name, byte priority) {
    this.name = name;
    this.priority = priority;
  }
  
  public Function() {
    this.isConstructor = true;
  }
  
  private static void create(String name, int priority) {
    ID id = ID.get(name);
    all.put(id, new Function(id, (byte) priority));
  }
  
  static {
    create("increment", 3);
    create("decrement", 3);
    create("iDivide", 3);
    create("mode", 3);
    create("subtract", 3);
    create("multiply", 3);
    create("divide", 3);
    create("brackets", 18);
    create("negative", 16);
    create("not", 16);
    create("iDivision", 14);
    create("mod", 14);
    create("division", 14);
    create("subtraction", 13);
    create("multiplication", 14);
    create("addition", 13);
    create("less", 8);
    create("more", 8);
    create("lessOrEqual", 8);
    create("moreOrEqual", 8);
    create("equal", 7);
    create("notEqual", 7);
    create("or", 5);
    create("and", 6);
    create("ifOp", 4);
    create("elseOp", 4);
    create("add", 3);
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
    return isConstructor ? "this" : name.string;// + "." + priority;
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
 