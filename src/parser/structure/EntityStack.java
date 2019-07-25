package parser.structure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;
import parser.ParserBase;

@SuppressWarnings("ResultOfObjectAllocationIgnored")
public class EntityStack<EntityType> extends ParserBase {
  public static final HashMap<ID, EntityStack> all = new HashMap<>();
  public static final EntityStack<ID> id = new EntityStack<ID>(ID.idID) {
    @Override
    public boolean isStringBased() {
      return true;
    }

    @Override
    public ID createFromString(String string) {
      return ID.get(string);
    }
  };
  public static final EntityStack<Block> block = new EntityStack<>(ID.blockID);
  public static final EntityStack<FunctionCall> call
      = new EntityStack<FunctionCall>(ID.callID) {
    @Override
    public FunctionCall create() {
      return new FunctionCall(null);
    }
  };
  public static final EntityStack<Link> link
      = new EntityStack<Link>(ID.linkID) {
    @Override
    public Link create() {
      return new Link(id.pop());
    }
  };
  public static final EntityStack<Function> function
      = new EntityStack<Function>(ID.functionID) {
    @Override
    public Function create() {
      return new Function(id.pop());
    }
  };
  public static final EntityStack<Code> code
      = new EntityStack<Code>(ID.codeID) {
    @Override
    public Code create() {
      return new Code();
    }
  };
  public static final EntityStack<ClassEntity> classStack
      = new EntityStack<ClassEntity>(ID.classID) {
    @Override
    public ClassEntity create() {
      return new ClassEntity(id.pop());
    }
  };
  
  public static final NativeFunction end = new NativeFunction("end", 17);
  public static final NativeFunction ret = new NativeFunction("return", 17);
  public static final NativeFunction equate = new NativeFunction("equate", 18) {
    @Override
    public Entity setCallTypes(LinkedList<Entity> parameters, Scope parentScope) {
      Variable variable = parameters.getFirst().createVariable(parentScope);
      if(variable == null) return super.setTypes(parentScope);
      variable.value = parameters.getLast();
      return variable.setTypes(parentScope);
    }
  };

  static {
    new EntityStack<Variable>(ID.variableID) {
      @Override
      public Variable create() {
        return new Variable(id.pop());
      }
    };
    
    new EntityStack<Type>(ID.typeID) {
      @Override
      public Type create() {
        return new Type(id.pop());
      }
    };
    
    new EntityStack<Formula>(ID.formulaID) {
      @Override
      public Formula create() {
        return new Formula();
      }
    };
    
    new EntityStack<Parameters>(ID.parametersID) {
      @Override
      public Parameters create() {
        return new Parameters();
      }
    };
    
    EntityStack<Value> valueStack = new EntityStack<Value>(ID.valueID) {
      @Override
      public Value create() {
        error("Value is abstract and cannot be created");
        return null;
      }
    };
    
    new EntityStack(ID.integerID, valueStack) {
      @Override
      public boolean isStringBased() {
        return true;
      }

      @Override
      public IntegerValue createFromString(String string) {
        return new IntegerValue(Integer.parseInt(string));
      }
    };
    
    new EntityStack(ID.decimalID, valueStack) {
      @Override
      public boolean isStringBased() {
        return true;
      }

      @Override
      public DecimalValue createFromString(String string) {
        return new DecimalValue(Float.parseFloat(string));
      }
    };
    
    new EntityStack(ID.stringID, valueStack) {
      @Override
      public boolean isStringBased() {
        return true;
      }

      @Override
      public StringValue createFromString(String string) {
        return new StringValue(ID.get(string));
      }
    };
    
    new EntityStack(ID.objectID, valueStack) {
      @Override
      public Object create() {
        return new Object();
      }
    };
    
    new EntityStack(ID.stringSequenceID, valueStack) {
      @Override
      public StringSequence create() {
        return new StringSequence();
      }
    };
    
    new EntityStack(ID.entryID, valueStack) {
      @Override
      public ObjectEntry create() {
        return new ObjectEntry(id.pop());
      }
    };
    
    new EntityStack(ID.listID, valueStack) {
      @Override
      public ListEntity create() {
        return new ListEntity();
      }
    };
    
    new EntityStack(ID.mapID, valueStack) {
      @Override
      public MapEntity create() {
        return new MapEntity();
      }
    };
    
    new EntityStack(ID.mapEntryID, valueStack) {
      @Override
      public MapEntry create() {
        return new MapEntry();
      }
    };
    
    // native functions
    
    new NativeFunction("break", 17);
    new NativeFunction("continue", 17);
    new NativeFunction("increment", 18) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        if(!type0.isNumber()) error(type0.getName() + " cannot be incremented");
        return null;
      }
    };
    new NativeFunction("decrement", 18) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        if(!type0.isNumber()) error(type0.getName() + " cannot be decremented");
        return null;
      }
    };
    new NativeFunction("add", 18) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        if(!type0.isNumber()) error(type0.getName() + " cannot be decremented");
        return null;
      }
    };
    new NativeFunction("subtract", 18) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        if(!type0.isNumber()) error(type0.getName() + " cannot be decremented");
        return null;
      }
    };
    new NativeFunction("multiply", 18) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        if(!type0.isNumber()) error(type0.getName() + " cannot be decremented");
        return null;
      }
    };
    new NativeFunction("divide", 18) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        if(!type0.isNumber()) error(type0.getName() + " cannot be decremented");
        return null;
      }
    };
    new NativeFunction("dot", 17) {
      @Override
      public Entity setCallTypes(LinkedList<Entity> parameters, Scope parentScope) {
        Entity parentType = parameters.getFirst().setTypes(parentScope);
        return parameters.getLast().setTypes(parentType.getScope(), true);
      }
    };
    new NativeFunction("atIndex", 17);
    new NativeFunction("brackets", 17);
    new NativeFunction("negative", 16) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        return type0;
      }
    };
    new NativeFunction("not", 16) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        return ClassEntity.booleanClass;
      }
    };
    new NativeFunction("multiplication", 14) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        return type0.toClass().getSubtractType(type1.toClass());
      }
    };
    new NativeFunction("division", 14) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        return type0.toClass().getSubtractType(type1.toClass());
      }
    };
    new NativeFunction("mod", 14) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        return ClassEntity.intClass;
      }
    };
    new NativeFunction("addition", 13) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        return type0.toClass().getAddType(type1.toClass());
      }
    };
    new NativeFunction("subtraction", 13) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        return type0.toClass().getSubtractType(type1.toClass());
      }
    };
    new NativeFunction("bitAnd", 11) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        return ClassEntity.intClass;
      }
    };
    new NativeFunction("bitOr", 9) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        return ClassEntity.intClass;
      }
    };
    new NativeFunction("notequal", 7) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        return ClassEntity.booleanClass;
      }
    };
    new NativeFunction("equal", 7) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        return ClassEntity.booleanClass;
      }
    };
    new NativeFunction("less", 7) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        return ClassEntity.booleanClass;
      }
    };
    new NativeFunction("lessOrEqual", 7) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        return ClassEntity.booleanClass;
      }
    };
    new NativeFunction("more", 7) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        return ClassEntity.booleanClass;
      }
    };
    new NativeFunction("moreOrEqual", 7) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        return ClassEntity.booleanClass;
      }
    };
    new NativeFunction("and", 6) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        return ClassEntity.booleanClass;
      }
    };
    new NativeFunction("or", 6) {
      @Override
      public Entity calculateType(Entity type0, Entity type1) {
        return ClassEntity.booleanClass;
      }
    };
    new NativeFunction("ifOp", 4) {
      @Override
      public Entity setCallTypes(LinkedList<Entity> parameters, Scope parentScope) {
        return parameters.get(1).setTypes(parentScope);
      }
    };
    new NativeFunction("elseOp", 4);
  }
  
  public static EntityStack get(String name) {
    return get(ID.get(name));
  }
  
  public static EntityStack get(ID name) {
    EntityStack stack = all.get(name);
    if(stack == null) error("Invalid entity name \"" + name.string + "\"");
    return stack;
  }

  public final ID name;
  public final Stack<EntityType> stack;

  public EntityStack(ID name) {
    this.name = name;
    this.stack = new Stack<>();
    all.put(name, this);
  }

  public EntityStack(ID name, EntityStack entityStack) {
    this.name = name;
    this.stack = entityStack.stack;
    all.put(name, this);
  }

  public EntityType pop() {
    if(stack.isEmpty()) error("Trying to pop entity from empty " + name.string + " stack");
    return stack.pop();
  }

  public void push(EntityType entity) {
    stack.push(entity);
  }  
  
  public EntityType create() {
    error("Cannot create " + name);
    return null;
  }
  
  public EntityType createFromString(String string) {
    error("Cannot create " + name);
    return null;
  }

  public boolean isStringBased() {
    return false;
  }

  public EntityType peek() {
    if(stack.isEmpty()) error("Trying to peek entity from empty " + name.string + " stack");
    return stack.peek();
  }

  @Override
  public String toString() {
    return name.string;
  }
}
