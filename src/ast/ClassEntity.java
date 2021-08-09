package ast;

import base.ElException;
import java.util.HashMap;
import java.util.LinkedList;

public class ClassEntity extends NamedEntity {
  public static HashMap<ID, ClassEntity> all = new HashMap<>();
  public static ClassEntity Int, Object, String;
  
  public Entity parent;
  public LinkedList<Variable> fields = new LinkedList<>();
  public LinkedList<Variable> parameters = new LinkedList<>();
  public LinkedList<Function> methods = new LinkedList<>();
  public boolean isNative;
  public int allocation = 0;
  
  static {
    Int = create("Int", true);
    String = create("String", true);
    Object = create("Object", true);
  }
  
  public static ClassEntity create(ID name) {
    ClassEntity classEntity = new ClassEntity();
    classEntity.name = name;
    all.put(name, classEntity);
    return classEntity;
  }
  
  public static ClassEntity create(ID name, boolean add) {
    ClassEntity classEntity = new ClassEntity();
    classEntity.name = name;
    classEntity.isNative = true;
    if(add) all.put(name, classEntity);
    return classEntity;
  }
  
  public static ClassEntity create(String name, boolean add) {
    return create(ID.get(name), add);
  }
  
  // processor fields
  
  @Override
  public ClassEntity getType() throws ElException {
    return this;
  }
  
  // type conversion
  
  @Override
  public ClassEntity toClass() {
    return this;
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
    name = ID.get(call.function.toString() + "1");
    call.function = this;
  }
  
  // other
  
  @Override
  public void print(String indent, String prefix) {
    println(indent + prefix + "class " + name + "{");
    String newIndent = indent + "  ";
    for(Variable field: fields) field.print(newIndent, "");
    for(Function method: methods) method.print(newIndent, "");
    println(indent + "}");
  }
}
