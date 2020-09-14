package parser.structure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;
import parser.ParserBase;
import static parser.structure.Entity.addCommand;
import vm.Command;
import vm.I64Add;
import vm.I64Deallocate;
import vm.I64Equate;
import vm.I64IsEqual;
import vm.I64Less;
import vm.I64More;
import vm.I64Multiply;
import vm.I64StackMoveReturnValue;
import vm.I64Subtract;
import vm.IfFalseGoto;
import vm.StringAdd;
import vm.VMBase;
import vm.VMEnd;
import vm.VMGoto;
import vm.VMReturn;

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
  
  public static final NativeFunction end = new NativeFunction("end", 17) {
    @Override
    public void functionToByteCode(FunctionCall call) {
      addCommand(new VMEnd());
    }
  };
  public static final NativeFunction ret = new NativeFunction("return", 17) {
    @Override
    public void functionToByteCode(FunctionCall call) {
      int i64quantity = VMBase.currentFunction.i64ParamIndex
          + VMBase.currentFunction.i64VarIndex + 2;
      if(VMBase.currentFunction.type == i64Class && i64quantity > 0)
        addCommand(new I64StackMoveReturnValue());
      if(i64quantity > 0) addCommand(new I64Deallocate(i64quantity));
      addCommand(new VMReturn());
    }
  };
  public static final NativeFunction equate = new NativeFunction("equate", 18) {
    @Override
    public void functionToByteCode(FunctionCall call) {
      int index = call.parameters.getFirst().getStackIndex();
      if(index >= 0) {
        Entity type0 = call.getType();
        if(type0 == ClassEntity.i64Class) {
          addCommand(new I64Equate(index));
        } else {
          error("Equate of " + type0.toString() + " is not implemented.");
        }
      }
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
    
    new EntityStack(ID.i8ID, valueStack) {
      @Override
      public boolean isStringBased() {
        return true;
      }

      @Override
      public I8Value createFromString(String string) {
        if(string.endsWith("i8"))
          string = string.substring(0, string.length() - 2);
        return new I8Value(Byte.parseByte(string));
      }
    };
    
    new EntityStack(ID.i16ID, valueStack) {
      @Override
      public boolean isStringBased() {
        return true;
      }

      @Override
      public I16Value createFromString(String string) {
        if(string.endsWith("i16"))
          string = string.substring(0, string.length() - 3);
        return new I16Value(Short.parseShort(string));
      }
    };
    
    new EntityStack(ID.i32ID, valueStack) {
      @Override
      public boolean isStringBased() {
        return true;
      }

      @Override
      public I32Value createFromString(String string) {
        if(string.endsWith("i32"))
          string = string.substring(0, string.length() - 3);
        return new I32Value(Integer.parseInt(string));
      }
    };
    
    new EntityStack(ID.i64ID, valueStack) {
      @Override
      public boolean isStringBased() {
        return true;
      }

      @Override
      public I64Value createFromString(String string) {
        if(string.endsWith("i64"))
          string = string.substring(0, string.length() - 3);
        return new I64Value(Long.parseLong(string));
      }
    };
    
    new EntityStack(ID.f32ID, valueStack) {
      @Override
      public boolean isStringBased() {
        return true;
      }

      @Override
      public F32Value createFromString(String string) {
        if(string.endsWith("f32"))
          string = string.substring(0, string.length() - 3);
        return new F32Value(Float.parseFloat(string));
      }
    };
    
    new EntityStack(ID.f64ID, valueStack) {
      @Override
      public boolean isStringBased() {
        return true;
      }

      @Override
      public F64Value createFromString(String string) {
        if(string.endsWith("f64"))
          string = string.substring(0, string.length() - 3);
        return new F64Value(Double.parseDouble(string));
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
      public Entity calculateType(Entity param0, Entity param1) {
        if(!param0.getType().isNumber())
          error(param0.toString() + " cannot be incremented");
        return null;
      }
    };
    new NativeFunction("decrement", 18) {
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        if(!param0.getType().isNumber())
          error(param0.toString() + " cannot be decremented");
        return null;
      }
    };
    new NativeFunction("add", 18) {
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        if(!param0.getType().isNumber())
          error(param0.toString() + " cannot be added");
        return null;
      }
    };
    new NativeFunction("subtract", 18) {
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        if(!param0.getType().isNumber())
          error(param0.toString() + " cannot be subtracted");
        return null;
      }
    };
    new NativeFunction("multiply", 18) {
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        if(!param0.getType().isNumber())
          error(param0.toString() + " cannot be multiplied");
        return null;
      }
    };
    new NativeFunction("divide", 18) {
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        if(!param0.getType().isNumber())
          error(param0.toString() + " cannot be divided");
        return null;
      }
    };
    new NativeFunction("dot", 17) {
      @Override
      public void setCallTypes(LinkedList<Entity> parameters, Scope parentScope) {
      }
    };
    new NativeFunction("atIndex", 17);
    new NativeFunction("brackets", 17);
    new NativeFunction("negative", 16) {
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        Entity type0 = param0.getType();
        if(!type0.isNumber())
          error(param0.toString() + " cannot be negated");
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
      public Entity calculateType(Entity param0, Entity param1) {
        return getPriorityType(param0, param1, NUMBER);
      }
      
      @Override
      public void functionToByteCode(FunctionCall call) {
        Entity type0 = call.getType();
        if(type0 == ClassEntity.i64Class) {
          addCommand(new I64Multiply());
        } else {
          error("Multiplication of " + type0.toString()
              + " is not implemented.");
        }
      }

      @Override
      public String getActionName() {
        return "multiplied";
      }
    };
    new NativeFunction("division", 14) {
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        return getPriorityType(param0, param1, NUMBER);
      }

      @Override
      public String getActionName() {
        return "divided";
      }
    };
    new NativeFunction("mod", 14) {
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        return getPriorityType(param0, param1, INTEGER);
      }
    };
    new NativeFunction("addition", 13) {
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        return getPriorityType(param0, param1, STRING);
      }
      
      @Override
      public void functionToByteCode(FunctionCall call) {
        Entity type0 = call.getType();
        if(type0 == ClassEntity.i64Class) {
          addCommand(new I64Add());
        } else if(type0 == ClassEntity.stringClass) {
          addCommand(new StringAdd());
        } else {
          error("Addition of " + type0.toString() + " is not implemented.");
        }
      }

      @Override
      public String getActionName() {
        return "added";
      }
    };
    new NativeFunction("subtraction", 13) {
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        return getPriorityType(param0, param1, NUMBER);
      }
      
      @Override
      public void functionToByteCode(FunctionCall call) {
        Entity type0 = call.getType();
        if(type0 == ClassEntity.i64Class) {
          addCommand(new I64Subtract());
        } else {
          error("Subtraction of " + type0.toString() + " is not implemented.");
        }
      }

      @Override
      public String getActionName() {
        return "subtracted";
      }
    };
    new NativeFunction("bitAnd", 11) {
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        return getPriorityType(param0, param1, INTEGER);
      }
    };
    new NativeFunction("bitOr", 9) {
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        return getPriorityType(param0, param1, INTEGER);
      }
    };
    new NativeFunction("notequal", 7) {
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        return ClassEntity.booleanClass;
      }
    };
    new NativeFunction("equal", 7) {
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        return ClassEntity.booleanClass;
      }
      
      @Override
      public void functionToByteCode(FunctionCall call) {
        Entity type0 = getPriorityType(call.parameters.getFirst()
            , call.parameters.getLast(), ANY);
        if(type0 == ClassEntity.i64Class) {
          addCommand(new I64IsEqual());
        } else {
          error("IsEqual of " + type0.toString() + " is not implemented.");
        }
      }

      @Override
      public String getActionName() {
        return "compared";
      }
    };
    new NativeFunction("less", 7) {      
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        getPriorityType(param0, param1, NUMBER);
        return ClassEntity.booleanClass;
      }
      
      @Override
      public void functionToByteCode(FunctionCall call) {
        Entity type0 = getPriorityType(call.parameters.getFirst()
            , call.parameters.getLast(), NUMBER);
        if(type0 == ClassEntity.i64Class) {
          addCommand(new I64Less());
        } else {
          error("Less of " + type0.toString() + " is not implemented.");
        }
      }

      @Override
      public String getActionName() {
        return "compared";
      }
    };
    new NativeFunction("lessOrEqual", 7) {
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        getPriorityType(param0, param1, NUMBER);
        return ClassEntity.booleanClass;
      }

      @Override
      public String getActionName() {
        return "compared";
      }
    };
    new NativeFunction("more", 7) {
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        getPriorityType(param0, param1, NUMBER);
        return ClassEntity.booleanClass;
      }
      
      @Override
      public void functionToByteCode(FunctionCall call) {
        Entity type0 = getPriorityType(call.parameters.getFirst()
            , call.parameters.getLast(), NUMBER);
        if(type0 == ClassEntity.i64Class) {
          addCommand(new I64More());
        } else {
          error("Addition of " + type0.toString() + " is not implemented.");
        }
      }

      @Override
      public String getActionName() {
        return "compared";
      }
    };
    new NativeFunction("moreOrEqual", 7) {
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        getPriorityType(param0, param1, NUMBER);
        return ClassEntity.booleanClass;
      }

      @Override
      public String getActionName() {
        return "compared";
      }
    };
    new NativeFunction("and", 6) {
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        return ClassEntity.booleanClass;
      }
    };
    new NativeFunction("or", 6) {
      @Override
      public Entity calculateType(Entity param0, Entity param1) {
        return ClassEntity.booleanClass;
      }
    };
    new NativeFunction("ifOp", 4) {
      @Override
      public void setCallTypes(LinkedList<Entity> parameters, Scope parentScope) {
        type = getPriorityType(parameters.get(1), parameters.getLast(), ANY);
      }
      
      @Override
      public void toByteCode(FunctionCall call) {
        call.parameters.getFirst().toByteCode();
        Command ifFalse = new IfFalseGoto();
        addCommand(ifFalse);
        Entity param0 = call.parameters.get(1);
        param0.toByteCode();
        conversion(param0.getType(), type);
        Command thenGoto = new VMGoto();
        addCommand(thenGoto);
        VMBase.gotos.add(ifFalse);
        VMBase.currentCommand = null;
        Entity param1 = call.parameters.getLast();
        param1.toByteCode();
        conversion(param1.getType(), type);
        VMBase.gotos.add(thenGoto);
      }

      @Override
      public String getActionName() {
        return "subtracted";
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
