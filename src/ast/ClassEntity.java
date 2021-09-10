package ast;

import ast.function.Method;
import ast.function.StaticFunction;
import vm.values.ObjectEntity;
import base.ElException;
import base.ElException.NotFound;
import java.util.HashMap;
import java.util.LinkedList;
import vm.values.VMValue;

public class ClassEntity extends NamedEntity {
  public static final HashMap<ID, ClassEntity> all = new HashMap<>();
  public static final ClassEntity Int, Float, String, Bool, Object;
  
  private final LinkedList<ClassParameter> parameters = new LinkedList<>();
  private final LinkedList<Variable> fields = new LinkedList<>();
  private final LinkedList<StaticFunction> methods = new LinkedList<>();
  private final LinkedList<StaticFunction> constructors = new LinkedList<>();
  public ClassEntity nativeClass;
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

  public ClassEntity(ID name, boolean isNative) {
    super(name);
    this.nativeClass = isNative ? this : Object;
  }

  public ClassEntity(String name, boolean isNative) {
    super(name);
    this.nativeClass = isNative ? this : Object;
  }

  public ClassEntity(String name, ClassEntity nativeClass) {
    super(name);
    this.nativeClass = nativeClass;
  }
  
  public static ClassEntity create(ID name) {
    ClassEntity classEntity = new ClassEntity(name, false);
    all.put(name, classEntity);
    return classEntity;
  }
  
  public static ClassEntity create(String name, VMValue value) {
    ClassEntity classEntity = new ClassEntity(name, true);
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
  
  // retrieving class objects
  
  public Variable getField(ID id) throws ElException {
    for(Variable field: fields) if(field.name == id) return field;
    throw new NotFound(this, "Field " + name);
  }
  
  public StaticFunction getMethod(String name, int parametersQuantity)
      throws ElException {
    return getMethod(ID.get(name), parametersQuantity);
  }
  
  public StaticFunction getMethod(ID id, int parametersQuantity)
      throws ElException {
    for(StaticFunction method: methods)
      if(method.name == id && method.getParametersQuantity()
          == parametersQuantity) return method;
    throw new NotFound(this, this + "." + id);
  }

  public StaticFunction getConstructor(int parametersQuantity) throws ElException {
    for(StaticFunction constructor: constructors)
      if(constructor.getParametersQuantity() == parametersQuantity)
        return constructor;
    throw new NotFound(this, "Constructor of " + toString() + " with "
        + parametersQuantity + " parameters");
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

  public void addMethod(Method method) {
    methods.add(method);
  }

  public void addConstructor(StaticFunction function) {
    constructors.add(function);
  }
  
  // processor fields
  
  @Override
  public Entity getType() throws ElException {
    return this;
  }

  public void setValue(VMValue value) {
    this.value = value;
  }
  
  @Override
  public ClassEntity getNativeClass() {
    return nativeClass;
  }
   
  // processing
  
  @Override
  public void process() throws ElException {
    ClassEntity oldClass = currentClass;
    currentClass = this;
    allocateScope();
    
    for(Variable field: fields) field.addToScope();
    for(StaticFunction method: methods) method.addToScope();
    
    for(StaticFunction constructor: constructors) constructor.process();
    for(StaticFunction method: methods) method.process();
    
    deallocateScope();
    currentClass = oldClass;
  }

  public void processConstructors() throws ElException {
    for(StaticFunction constructor: constructors)
      constructor.processConstructor(this);
  }
  
  public void resolveTypes() throws ElException {
    ClassEntity oldClass = currentClass;
    currentClass = this;
    allocateScope();
    
    for(ClassParameter parameter: parameters) parameter.addToScope();
    
    for(Variable field: fields) field.resolveType();
    for(StaticFunction constructor: constructors) constructor.resolveTypes();
    for(StaticFunction method: methods) method.resolveTypes();
    
    deallocateScope();
    currentClass = oldClass;
  }
  
  @Override
  public void addToScope() {
    super.addToScope();
    for(StaticFunction constructor: constructors) constructor.addToScope();
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
  public void print(StringBuilder indent, String prefix) {
    println(indent + prefix + "class " + name + (parameters.isEmpty()
        ? "" : "<" + listToString(parameters) + ">") + " {");
    indent.append("  ");
    for(Variable field: fields) field.print(indent, "");
    if(!fields.isEmpty() && !constructors.isEmpty()) println("");
    for(StaticFunction method: constructors) method.print(indent, "");
    if(!constructors.isEmpty() || !fields.isEmpty()
        && !methods.isEmpty()) println("");
    for(StaticFunction method: methods) method.print(indent, "");
    indent.delete(0, 2);
    println(indent + "}");
  }
}