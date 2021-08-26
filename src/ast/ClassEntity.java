package ast;

import vm.values.ObjectEntity;
import base.ElException;
import java.util.HashMap;
import java.util.LinkedList;
import vm.values.I64Value;
import vm.values.StringValue;
import vm.values.VMValue;

public class ClassEntity extends NamedEntity {
  public static final HashMap<ID, ClassEntity> all = new HashMap<>();
  public static final ClassEntity Int, String, Bool, Object;
  
  private final LinkedList<Variable> fields = new LinkedList<>();
  private final LinkedList<Function> methods = new LinkedList<>();
  private final LinkedList<Function> constructors = new LinkedList<>();
  private int allocation = 0;
  private VMValue value;
  
  // native classes
  
  static {
    Int = create("Int", new I64Value(0));
    String = create("String", new StringValue(""));
    Bool = create("Bool", new I64Value(0));
    Object = create("Object", new I64Value(0));
  }
  
  // creating

  public ClassEntity(ID name) {
    super(name);
  }

  public ClassEntity(String name) {
    super(name);
  }
  
  public static ClassEntity create(ID name) {
    ClassEntity classEntity = new ClassEntity(name);
    all.put(name, classEntity);
    return classEntity;
  }
  
  public static ClassEntity create(String name, VMValue value) {
    ClassEntity classEntity = new ClassEntity(name);
    classEntity.value = value;
    all.put(classEntity.name, classEntity);
    return classEntity;
  }
  
  // properties
  
  public boolean isNative() {
    return value != null;
  }
  
  // retrieving child objects
  
  public Variable getField(ID id) {
    for(Variable field: fields) if(field.name == id) return field;
    return null;
  }
  
  public Function getMethod(ID id) {
    for(Function method: methods) if(method.name == id) return method;
    return null;
  }

  public Function getConstructor() {
    return constructors.getFirst();
  }
  
  // adding child objects

  int addField(Variable variable) {
    int index = allocation;
    allocation++;
    fields.add(variable);
    return index;
  }

  void addMethod(Function function, boolean isConstructor) {
    if(isConstructor)
      constructors.add(function);
    else
      methods.add(function);
  }
  
  // processor fields
  
  @Override
  public ClassEntity getType() throws ElException {
    return this;
  }
   
  // processing
  
  @Override
  public void process() throws ElException {
    ClassEntity oldClass = currentClass;
    currentClass = this;
    allocateScope();
    for(Variable field: fields) addToScope(field);
    for(Function constructor: constructors) constructor.process();
    for(Function method: methods) method.process();
    deallocateScope();
    currentClass = oldClass;
  }

  public void processConstructors() throws ElException {
    for(Function constructor: constructors)
      constructor.process(this);
  }
  
  // type conversion
  
  @Override
  public ClassEntity toClass() {
    return this;
  }
  
  public ClassEntity toNativeClass() {
    return isNative() ? this : Object;
  }
  
  // moving funcitons

  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToClass(this);
  }

  @Override
  public void moveToCode(Code code) {
    code.add(this);
  }

  @Override
  public void moveToFunctionCall(FunctionCall call) throws ElException {
    throw new ElException("Anonymous classes are not implemented.");
  }
  
  // creating objects of this class

  public ObjectEntity newObject() throws ElException {
    ObjectEntity object = new ObjectEntity(this);
    int index = -1;
    object.fields = new VMValue[fields.size()];
    for(Variable parameter: fields) {
      index++;
      object.fields[index] = parameter.createValue();
    }
    return object;
  }

  @Override
  public VMValue createValue() throws ElException {
    return value.create();
  }
  
  // other
  
  @Override
  public void print(String indent, String prefix) {
    println(indent + prefix + "class " + name + "{");
    String newIndent = indent + "  ";
    for(Variable field: fields) field.print(newIndent, "");
    for(Function method: constructors) method.print(newIndent, "");
    for(Function method: methods) method.print(newIndent, "");
    println(indent + "}");
  }
}
