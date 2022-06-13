package ast;

import ast.function.Constructor;
import ast.function.Method;
import ast.function.StaticFunction;
import exception.ElException;
import exception.EntityException;
import exception.NotFound;
import vm.values.ObjectEntity;
import vm.values.VMValue;

import java.util.HashMap;
import java.util.LinkedList;

public class ClassEntity extends NamedEntity {
  public static final HashMap<ID, ClassEntity> all = new HashMap<>();
  public static final ClassEntity Int, Float, String, Bool, Object;
  public static ClassEntity current;
  
  private final LinkedList<ClassParameter> parameters = new LinkedList<>();
  private final LinkedList<Variable> fields = new LinkedList<>();
  private final LinkedList<Method> methods = new LinkedList<>();
  private final LinkedList<Constructor> constructors = new LinkedList<>();
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

  public ClassEntity(IDEntity name, boolean isNative) {
    super(name);
    this.nativeClass = isNative ? this : Object;
  }

  public ClassEntity(String name, boolean isNative) {
    super(name, 0, 0);
    this.nativeClass = isNative ? this : Object;
  }
  
  public static ClassEntity create(IDEntity name) {
    ClassEntity classEntity = new ClassEntity(name, false);
    all.put(name.value, classEntity);
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
  
  @Override
  public Entity getType() throws EntityException {
    return this;
  }

  public Entity getSubType() throws EntityException {
    throw new EntityException(this, "has no subtypes");
  }

  public Variable getField(ID name) throws NotFound {
    for(Variable field: fields) if(field.name == name) return field;
    throw new NotFound("field " + name, this);
  }
  
  public Entity getChild(ID name) throws NotFound {
    try {
      return getField(name);
    } catch(NotFound ignored) {
    }

    try {
      return getMethod(name, 0);
    } catch(NotFound ex) {
      throw new NotFound("Child " + name, this);
    }
  }

  @Override
  public void resolveField(ID name, Entity type) throws EntityException {
    if(log2) println("[resolve field " + name + " of " + type + " from " + this
        + "]");
    for(Variable field: fields) {
      if(field.name == name) {
        field.resolve(type);
        return;
      }
    }
    try {
      getMethod(name, 0).resolveChild(type);
    } catch (NotFound ex) {
      throw new EntityException(this, ex.message);
    } 
  }
  
  public Method getMethod(String name, int parametersQuantity) throws NotFound {
    return getMethod(ID.get(name), parametersQuantity);
  }
  
  @Override
  public Method getMethod(ID id, int parametersQuantity) throws NotFound {
    for(Method method: methods)
      if(method.isFunction(id, parametersQuantity)) return method;
    throw new NotFound("method " + id, parametersQuantity, this);
  }

  public Constructor getConstructor(int parametersQuantity)
      throws NotFound {
    for(Constructor constructor: constructors)
      if(constructor.matches(parametersQuantity)) return constructor;
    throw new NotFound("Constructor", parametersQuantity, this);
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

  public void addConstructor(Constructor function) {
    constructors.add(function);
  }
  
  // properties

  public void setValue(VMValue value) {
    this.value = value;
  }
  
  @Override
  public ClassEntity getNativeClass() {
    return nativeClass;
  }
  
  @Override
  public boolean isValue(ID name, boolean isThis) {
    return this.name == name && !isThis;
  }
  
  // resolving
  
  public void resolveLinks() throws EntityException {
    ClassEntity oldCurrent = current;
    current = this;
    allocateScope();
    
    for(ClassParameter parameter: parameters) addToScope(parameter);
    
    for(Variable field: fields) addToScope(field);
    for(StaticFunction method: methods) addToScope(method);

    for(Variable field: fields) field.resolveLinks();
    for(StaticFunction constructor: constructors) constructor.resolveLinks();
    for(StaticFunction method: methods) method.resolveLinks();
    
    deallocateScope();
    current = oldCurrent;
  }

  @Override
  public Entity resolveType() throws EntityException {
    return this;
  }

  public void addToScope() {
    addToScope(this);
    for(StaticFunction constructor: constructors) addToScope(constructor);
  }

  public void resolveConstructors() throws NotFound {
    for(StaticFunction constructor: constructors) {
      constructor.resolveConstructor(this);
    }
  }
   
  // compiling
  
  @Override
  public void compile() throws EntityException {
    ClassEntity oldCurrent = current;
    current = this;
    allocateScope();
    
    for(Variable field: fields) addToScope(field);
    for(StaticFunction method: methods) addToScope(method);
    
    for(StaticFunction constructor: constructors) constructor.compile();
    for(StaticFunction method: methods) method.compile();
    
    deallocateScope();
    current = oldCurrent;
  }
  
  @Override
  public void resolve(Entity type) throws EntityException {
    try {
      convert(getNativeClass(), type.getNativeClass());
    } catch (ElException ex) {
      throw new EntityException(this, ex.message);
    }
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

  public ObjectEntity newObject() {
    ObjectEntity object = new ObjectEntity(this, new VMValue[fields.size()]);
    int index = -1;
    for(Variable parameter: fields) {
      index++;
      object.fields[index] = parameter.createValue();
    }
    return object;
  }

  @Override
  public VMValue createValue() {
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