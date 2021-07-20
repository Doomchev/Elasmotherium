package ast;

import base.ElException;
import java.util.HashMap;
import java.util.LinkedList;

public class ClassEntity extends NamedEntity {
  public static HashMap<ID, ClassEntity> all = new HashMap<>();
  
  public Entity parent;
  public LinkedList<Variable> fields = new LinkedList<>();
  public LinkedList<Variable> parameters = new LinkedList<>();
  public LinkedList<Function> methods = new LinkedList<>();
  public boolean isNative;

  public ClassEntity(ID name) {
    this.name = name;
    all.put(name, this);
  }
  
  public ClassEntity(ID name, boolean add) {
    this.name = name;
    this.isNative = true;
    if(add) all.put(name, this);
  }
  
  
  
  @Override
  public ID getID() {
    return classID;
  }
  
  @Override
  public ClassEntity toClass() {
    return this;
  }

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
  
  @Override
  public void print(String indent, String prefix) {
    println(indent + prefix + "class " + name + "{");
    String newIndent = indent + "  ";
    for(Variable field: fields) field.print(newIndent, "");
    for(Function method: methods) method.print(newIndent, "");
    println(indent + "}");
  }
}
