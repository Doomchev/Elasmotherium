package ast;

import base.ElException;
import java.util.HashMap;
import java.util.LinkedList;
import vm.I64Value;
import vm.StringValue;
import vm.VMValue;

public class ClassEntity extends NamedEntity {
  public static HashMap<ID, ClassEntity> all = new HashMap<>();
  public static ClassEntity Int, String, Bool, Object;
  
  public Entity parent;
  public final LinkedList<Variable> fields = new LinkedList<>();
  public final LinkedList<Function> methods = new LinkedList<>();
  public final LinkedList<Function> constructors = new LinkedList<>();
  public int allocation = 0;
  public VMValue value;
  
  static {
    Int = create("Int", new I64Value(0));
    String = create("String", new StringValue(""));
    Bool = create("Bool", new I64Value(0));
    Object = create("Object", new I64Value(0));
  }
  
  public static ClassEntity create(ID name) {
    ClassEntity classEntity = new ClassEntity();
    classEntity.name = name;
    all.put(name, classEntity);
    return classEntity;
  }
  
  public static ClassEntity create(String name, VMValue value) {
    ClassEntity classEntity = new ClassEntity();
    classEntity.name = ID.get(name);
    classEntity.value = value;
    all.put(classEntity.name, classEntity);
    return classEntity;
  }
  
  // properties
  
  public Variable getField(ID id) {
    for(Variable field: fields) if(field.name == id) return field;
    return null;
  }
  
  public Function getMethod(ID id) {
    for(Function method: methods) if(method.name == id) return method;
    return null;
  }
  
  public boolean isNative() {
    return value != null;
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
    code.classes.add(this);
  }

  @Override
  public void moveToFunctionCall(FunctionCall call) throws ElException {
    throw new ElException("Anonymous classes are not implemented.");
  }
  
  // other

  @Override
  public VMValue createValue() throws ElException {
    return value.create();
  }
  
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
