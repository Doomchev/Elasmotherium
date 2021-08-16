package ast;

import base.ElException;
import java.util.HashMap;
import java.util.LinkedList;
import vm.VMBase;
import vm.VMCommand;

public class Function extends NamedEntity  {
  public static ID id = ID.get("function");
  public static final HashMap<ID, Function> all = new HashMap<>();
  
  public Code code = new Code();
  public Entity returnType = null;
  public final LinkedList<Variable> parameters = new LinkedList<>();
  public boolean isConstructor = false;
  public ClassEntity parentClass = null;
  public byte priority = VALUE;
  public int startingCommand, allocation = 0;
  public VMCommand command = null;
  
  public Function(ID name) {
    this.name = name;
    if(name == null) this.isConstructor = true;
  }
  
  public Function(ID name, byte priority) {
    this.name = name;
    this.priority = priority;
  }
  
  public Function(VMCommand command, String name, ClassEntity... paramTypes) {
    this.command = command;
    this.name = ID.get(name);
    for(ClassEntity paramType: paramTypes)
      parameters.add(new Variable(paramType));
  }
  
  public Function(VMCommand command, ClassEntity returnType, String name
      , ClassEntity... paramTypes) {
    this(command, name, paramTypes);
    this.returnType = returnType;
  }

  public static Function create(ID id) {
    return allocateFunction(new Function(id));
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
  public byte getPriority() {
    return priority;
  }
  
  public boolean isNative() {
    return priority != VALUE;
  }
  
  // processor fields
  
  @Override
  public ID getObject() throws ElException {
    return isNative() ? name : id;
  }
  
  @Override
  public ClassEntity getType() throws ElException {
    return returnType.toClass();
  }
  
  @Override
  public Entity getParameter(int index) throws ElException {
    return parameters.get(index);
  }
  
  // processing
  
  @Override
  public void resolveAll() throws ElException {
    if(command == null)
      throw new ElException("Custom functions are not yet implemented.");
    VMBase.append(command.create(null));
  }
  
  // type conversion

  @Override
  public Function toFunction() {
    return this;
  }
  
  // moving functions

  @Override
  public void move(Entity entity) throws base.ElException {
    entity.moveToFunction(this);
  }

  @Override
  public void moveToClass(ClassEntity classEntity) {
    deallocateFunction();
    parentClass = classEntity;
    classEntity.methods.add(this);
  }

  @Override
  public void moveToCode(Code code) {
    deallocateFunction();
    code.functions.add(this);
  }

  @Override
  public void moveToBlock() throws ElException {
    deallocateFunction();
  }
  
  // other

  @Override
  public String toString() {
    return isConstructor ? "this" : name.string;// + "." + priority;
  }
  
  @Override
  public void print(String indent, String prefix) {
    String str = "";
    for(Variable parameter : parameters) {
      if(!str.isEmpty()) str += ", ";
      str += parameter.type + " " + parameter.name + ":" + parameter.index;
    }
    str = (returnType == null ? "" : returnType + " ") +
        (isConstructor ? "create" : name.string) + "(" +  str + "):"
        + allocation;
    code.print(indent, prefix + str);
  }
}
 