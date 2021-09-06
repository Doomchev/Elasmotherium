package ast;

import static ast.FunctionCall.resolve;
import static base.Base.currentProcessor;
import static base.Base.log;
import static base.Base.println;
import base.ElException;
import java.util.HashMap;
import java.util.LinkedList;
import parser.EntityStack;
import vm.CallFunction;
import vm.VMCommand;
import static ast.Entity.append;

public class Function extends NamedEntity  {
  public static final ID id = ID.get("function");
  
  public static final HashMap<ID, Function> all = new HashMap<>();
  public static Function ret, equate;
  
  public byte priority = VALUE;
  
  private boolean isConstructor = false;
  private ClassEntity parentClass = null;
  private Entity returnType = null;
  private final LinkedList<Variable> parameters = new LinkedList<>();
  private Code code = new Code();
  private int startingCommand;
  private int allocation = 0;
  private VMCommand command = null;
  
  // constructors
  
  public Function(ID name) {
    super(name);
    if(name == null) this.isConstructor = true;
  }
  
  public Function(ID name, byte priority) {
    super(name);
    this.priority = priority;
  }
  
  public Function(VMCommand command, String name, ClassEntity... paramTypes) {
    super(ID.get(name));
    this.command = command;
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
  
  private static Function create(String name, int priority) {
    ID functionID = ID.get(name);
    Function function = new Function(functionID, (byte) priority);
    all.put(functionID, function);
    return function;
  }
  
  // native functions
  
  static {
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
    
    create("increment", 3);
    create("decrement", 3);
    equate = create("equate", 3);
    create("add", 3);
    create("subtract", 3);
    create("multiply", 3);
    create("divide", 3);
    create("iDivide", 3);
    create("mode", 3);
    
    create("break", 0);
    create("continue", 0);
    ret = create("return", 0);
  }
  
  // parameters
  
  public boolean isNative() {
    return priority != VALUE;
  }

  public boolean isMethod() {
    return parentClass != null;
  }

  @Override
  public byte getPriority() {
    return priority;
  }

  public int getCallAllocation() {
    return parameters.size() - 1;
  }

  public int getCallDeallocation() {
    return parameters.size() + (isMethod() ? 1 : 0) - (isConstructor ? 1 : 0);
  }

  public int getStartingCommand() {
    return startingCommand;
  }

  public void setReturnType(Entity returnType) {
    this.returnType = returnType;
  }
  
  // allocation

  public void setAllocation() {
    allocation = Math.max(allocation, currentAllocation);
  }

  public void printAllocation(String fileName) {
    code.print("", fileName + ":" + allocation + " ");
  }
  
  // child objects

  public int addParameter(Variable variable) {
    int index = allocation;
    allocation++;
    parameters.add(variable);
    return index;
  }

  public void setFunctionCommand(ID id, VMCommand command) throws ElException {
    code.setFunctionCommand(id, command);
  }
  
  public void setCommand(VMCommand command) {
    this.command = command;
  }
  
  // processor fields

  public int getAllocation() {
    return allocation;
  }
  
  @Override
  public ID getObject() throws ElException {
    return isNative() ? name : id;
  }
  
  @Override
  public Entity getType() throws ElException {
    return isConstructor ? parentClass : returnType.getType();
  }
  
  public Entity getParameter(int index) throws ElException {
    return parameters.get(index);
  }

  void setCode(Code code) {
    this.code = code;
  }

  public void pushCode() {
    EntityStack.code.push(code);
  }
  
  // processing
  
  @Override
  public void process() throws ElException {
    if(command != null) return;
    startingCommand = vm.VMBase.currentCommand + 1;
    Function oldFunction = currentFunction;
    currentFunction = this;
    allocateScope();
    for(Variable param: parameters) addToScope(param);
    VMCommand endingCommand = returnType == null ? new vm.Return() : null;
    code.processWithoutScope(endingCommand);
    deallocateScope();
    currentFunction = oldFunction;
  }

  public void process(ClassEntity classEntity) throws ElException {
    for(Variable param: parameters)
      param.processField(classEntity, code);
  }

  public void processConstructors() throws ElException {
    code.processConstructors();
  }
  
  public void processCode(VMCommand endingCommand) throws ElException {
    code.processWithoutScope(endingCommand);
  }

  public void resolve(FunctionCall call) throws ElException {
    if(log) println(subIndent + "Resolving function call " + toString());
    
    if(isConstructor) {
      if(command != null) {
        resolveParameters(call);
        append(command.create());
        return;
      }
      append(new vm.object.ObjectCreate(parentClass));
    }
    
    if(!isNative()) resolveParameters(call);
    
    if(this == Function.ret) {
      currentProcessor.call(call);
      return;
    } else if(isConstructor) {
      append(new vm.CallFunction(this));
      return;
    }
    
    if(command != null) {
      append(command.create());
    } else {
      append(new vm.CallFunction(this));
    }
  }
  
  @Override
  public void resolveAll() throws ElException {
    if(command == null)
      throw new ElException("Custom functions are not yet implemented.");
    append(command.create());
  }

  public void resolveParameters(FunctionCall call) throws ElException {
    int i = 0;
    for(Entity parameter: parameters) {
      currentProcessor.call(
          call.getParameter(i), resolve, parameter.getType());
      i++;
    }
  }
  
  public void resolveTypes() throws ElException {
    if(returnType != null) returnType = returnType.resolve();
    for(Variable param: parameters) param.resolveType();
  }
  
  public void append() {
    append(command == null ? new CallFunction(this) : command);
  }
  
  // moving functions

  @Override
  public void move(Entity entity) throws base.ElException {
    entity.moveToFunction(this);
  }

  @Override
  public void moveToClass(ClassEntity classEntity) throws ElException {
    deallocateFunction();
    parentClass = classEntity;
    classEntity.addMethod(this, isConstructor);
  }

  @Override
  public void moveToCode(Code code) {
    deallocateFunction();
    code.add(this);
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
      str += parameter.toParamString();
    }
    str = (returnType == null ? "" : returnType + " ") +
        (isConstructor ? "create" : name.string) + "(" +  str + "):"
        + allocation;
    if(command == null) {
      code.print(indent, prefix + str);
    } else {
      println(indent + prefix + str + ";");
    }
  }
}
 