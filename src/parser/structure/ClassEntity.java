package parser.structure;

import base.Processor;
import java.util.HashMap;
import java.util.LinkedList;

public class ClassEntity extends FlagEntity {
  public static HashMap<ID, ClassEntity> all = new HashMap<>();
  
  public Type parent;
  public LinkedList<Variable> variables = new LinkedList<>();
  public LinkedList<Variable> parameters = new LinkedList<>();
  public LinkedList<Function> methods = new LinkedList<>();
  public Scope scope;
  public boolean isNative, processed = false;

  public ClassEntity(ID name) {
    this.name = name;
    this.isNative = flags.contains(ID.nativeID);
    all.put(name, this);
  }
  
  public ClassEntity(ID name, boolean add) {
    this.name = name;
    this.isNative = true;
    if(add) all.put(name, this);
  }
  
  
  public static ClassEntity voidClass = new ClassEntity(ID.get("Void"), false);
  public static ClassEntity classClass = new ClassEntity(ID.get("Class"), true);
  
  public static ClassEntity unknownClass = new ClassEntity(ID.get("unknown"), false) {
    @Override
    public ClassEntity getAddType(ClassEntity classEntity) {
      if(classEntity == ClassEntity.unknownClass) error("Cannot resolve type");
      return classEntity.getAddType(this);
    }
    @Override
    public ClassEntity getSubtractType(ClassEntity classEntity) {
      return classEntity.getSubtractType(this);
    }
    @Override
    public ClassEntity getIntAddType() {
      return intClass;
    }
    @Override
    public ClassEntity getIntSubtractType() {
      return intClass;
    }
    @Override
    public ClassEntity getFloatAddType() {
      return floatClass;
    }
    @Override
    public ClassEntity getFloatSubtractType() {
      return floatClass;
    }  
    @Override
    public ClassEntity getStringAddType() {
      return stringClass;
    }
  
    @Override
    boolean isNumber() {
      return true;
    }
  };
    
  public static ClassEntity intClass = new ClassEntity(ID.get("Int"), true) {
    @Override
    public ClassEntity getAddType(ClassEntity classEntity) {
      return classEntity.getIntAddType();
    }
    @Override
    public ClassEntity getSubtractType(ClassEntity classEntity) {
      return classEntity.getIntSubtractType();
    }
    @Override
    public ClassEntity getIntAddType() {
      return this;
    }
    @Override
    public ClassEntity getIntSubtractType() {
      return this;
    }
    @Override
    public ClassEntity getFloatAddType() {
      return floatClass;
    }
    @Override
    public ClassEntity getFloatSubtractType() {
      return floatClass;
    }  
    @Override
    public ClassEntity getStringAddType() {
      return stringClass;
    }
  
    @Override
    boolean isNumber() {
      return true;
    }
  };
  
  public static ClassEntity floatClass = new ClassEntity(ID.get("Float"), true) {
    @Override
    public ClassEntity getAddType(ClassEntity classEntity) {
      return classEntity.getFloatAddType();
    }
    @Override
    public ClassEntity getSubtractType(ClassEntity classEntity) {
      return classEntity.getFloatSubtractType();
    }
    @Override
    public ClassEntity getIntAddType() {
      return this;
    }
    @Override
    public ClassEntity getIntSubtractType() {
      return this;
    }
    @Override
    public ClassEntity getFloatAddType() {
      return this;
    }
    @Override
    public ClassEntity getFloatSubtractType() {
      return this;
    }  
    @Override
    public ClassEntity getStringAddType() {
      return stringClass;
    }
  
    @Override
    boolean isNumber() {
      return true;
    }
  };
  
  public static ClassEntity stringClass = new ClassEntity(ID.get("String"), true) {
    @Override
    public ClassEntity getAddType(ClassEntity classEntity) {
      return classEntity.getStringAddType();
    }
    @Override
    public ClassEntity getSubtractType(ClassEntity classEntity) {
      return stringError();
    }
    @Override
    public ClassEntity getIntAddType() {
      return this;
    }
    @Override
    public ClassEntity getIntSubtractType() {
      return stringError();
    }    
    @Override
    public ClassEntity getFloatAddType() {
      return this;
    }
    @Override
    public ClassEntity getFloatSubtractType() {
      return stringError();
    }  
    @Override
    public ClassEntity getStringAddType() {
      return this;
    }
    private ClassEntity stringError() {
      error("String only can be used in addition operation");
      return null;
    }
  };
  
  public static ClassEntity booleanClass = new ClassEntity(ID.get("Boolean"), true);
  
  public ClassEntity getAddType(ClassEntity classEntity) {
    return arithmeticError();
  }
  
  public ClassEntity getSubtractType(ClassEntity classEntity) {
    return arithmeticError();
  }
  
  public ClassEntity getIntAddType() {
    return arithmeticError();
  }

  public ClassEntity getIntSubtractType() {
    return arithmeticError();
  }
  
  public ClassEntity getFloatAddType() {
    return arithmeticError();
  }

  public ClassEntity getFloatSubtractType() {
    return arithmeticError();
  }
  
  public ClassEntity getStringAddType() {
    return arithmeticError();
  }

  private ClassEntity arithmeticError() {
    error(name.string + " cannot be used in arithmetic operations");
    return null;
  }
  
  
  
  @Override
  public ID getID() {
    return classID;
  }
  
  @Override
  public LinkedList<? extends Entity> getChildren() {
    LinkedList<Entity> list = new LinkedList<>();
    list.addAll(variables);
    list.addAll(methods);
    return list;
  }

  public Variable getVariable(ID id) {
    for(Variable child : variables)
      if(child.name == id) return child;
    return null;
  }

  @Override
  public Entity getChild(ID id) {
    if(id == parentID) return parent;
    return null;
  }

  @Override
  public Entity getType() {
    return ClassEntity.classClass;
  }

  @Override
  Entity getFieldType(ID fieldName) {
    Entity type = scope.entries.get(fieldName).setTypes(scope);
    
    return type;
  }

  Entity getFieldType(ID fieldName, Type type) {
    return getFieldType(fieldName);
  }

  @Override
  public ClassEntity toClass() {
    return this;
  }

  @Override
  public Entity setTypes(Scope parentScope) {
    if(processed) return this;
    for(Function method : methods) method.setTypes(scope);
    for(Variable variableBase : variables) variableBase.setTypes(scope);
    processed = true;
    return this;
  }
  
  @Override
  public Entity setCallTypes(LinkedList<Entity> parameters, Scope parentScope) {
    return this;
  }

  @Override
  public void move(Entity entity) {
    entity.moveToClass(this);
  }

  @Override
  public void addToScope(Scope parentScope) {
    scope = new Scope(parentScope);
    for(Function method : methods) {
      if(!method.hasFlag(Processor.constructorID)) {
        scope.add(method);
        method.addToScope(scope);
      }
    }
    for(Variable variableBase : variables) scope.add(variableBase);
  }
  
  @Override
  public void logScope(String indent) {
    scope.log(indent);
  }
}
