package ast;

import vm.values.ObjectEntity;
import base.ElException;
import java.util.HashMap;
import java.util.LinkedList;
import vm.values.VMValue;

public class ClassEntity extends NamedEntity {
  public static final HashMap<ID, ClassEntity> all = new HashMap<>();
  public static final ClassEntity Int, Float, String, Bool, Object;
  
  private final LinkedList<ClassParameter> parameters = new LinkedList<>();
  private final LinkedList<Variable> fields = new LinkedList<>();
  private final LinkedList<Function> methods = new LinkedList<>();
  private final LinkedList<Function> constructors = new LinkedList<>();
  private int allocation = 0;
  private VMValue value = null;
  
  // native classes
  
  static {
    Int = create("Int", new vm.values.I64Value(0));
    Float = create("Float", new vm.values.F64Value(0));
    String = create("String", new vm.values.StringValue(""));
    Bool = create("Bool", new vm.values.I64Value(0));
    Object = create("Object", new vm.values.I64Value(0));
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
  
  public static ClassEntity get(String name) {
    return all.get(ID.get(name));
  }
  
  public static ClassEntity get(ID name) {
    return all.get(name);
  }
  
  // properties
  
  public boolean isNative() {
    return value != null;
  }
  
  // retrieving class objects
  
  public Variable getField(ID id) {
    for(Variable field: fields) if(field.name == id) return field;
    return null;
  }
  
  public Function getMethod(String name) {
    return getMethod(ID.get(name));
  }
  
  public Function getMethod(ID id) {
    for(Function method: methods) if(method.name == id) return method;
    return null;
  }

  public Function getConstructor() {
    return constructors.getFirst();
  }
  
  // adding child objects

  public int addParameter(ClassParameter parameter) {
    int index = parameters.size();
    parameters.add(parameter);
    return index;
  }

  public int addField(Variable variable) {
    int index = allocation;
    allocation++;
    fields.add(variable);
    return index;
  }

  public void addMethod(Function function, boolean isConstructor) {
    if(isConstructor)
      constructors.add(function);
    else
      methods.add(function);
  }
  
  // processor fields
  
  @Override
  public Entity getType() throws ElException {
    return this;
  }

  public void setValue(VMValue value) {
    this.value = value;
  }
   
  // processing
  
  @Override
  public void process() throws ElException {
    ClassEntity oldClass = currentClass;
    currentClass = this;
    allocateScope();
    
    for(Variable field: fields) addToScope(field);
    for(Function method: methods) addToScope(method);
    
    for(Function constructor: constructors) constructor.process();
    for(Function method: methods) method.process();
    
    deallocateScope();
    currentClass = oldClass;
  }

  public void processConstructors() throws ElException {
    for(Function constructor: constructors)
      constructor.process(this);
  }
  
  public void resolveTypes() throws ElException {
    ClassEntity oldClass = currentClass;
    currentClass = this;
    allocateScope();
    
    for(ClassParameter parameter: parameters) addToScope(parameter);
    
    for(Variable field: fields) field.resolveType();
    for(Function constructor: constructors) constructor.resolveTypes();
    for(Function method: methods) method.resolveTypes();
    
    deallocateScope();
    currentClass = oldClass;
  }
  
  // type conversion
  
  @Override
  public ClassEntity toClass() {
    return this;
  }
  
  @Override
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
    ObjectEntity object = new ObjectEntity(this, new VMValue[fields.size()]);
    int index = -1;
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
    println(indent + prefix + "class " + name + (parameters.isEmpty()
        ? "" : "<" + listToString(parameters) + ">") + " {");
    String newIndent = indent + "  ";
    for(Variable field: fields) field.print(newIndent, "");
    if(!fields.isEmpty() && !constructors.isEmpty()) println("");
    for(Function method: constructors) method.print(newIndent, "");
    if(!constructors.isEmpty() || !fields.isEmpty()
        && !methods.isEmpty()) println("");
    for(Function method: methods) method.print(newIndent, "");
    println(indent + "}");
  }
}