package ast;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;
import parser.ParserBase;
import static ast.Entity.addCommand;
import ast.nativ.*;
import vm.*;

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
  
  public static final NativeFunction end = new End();
  public static final NativeFunction ret = new Return();
  public static final NativeFunction equate = new Equate();

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
        throw new Error("Value is abstract and cannot be created");
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
        return new StringValue(string);
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
    
    new Break();
    new Continue();
    new Increment();
    new Decrement();
    new Add();
    new Subtract();
    new Multiply();
    new Divide();
    new Dot();
    new AtIndex();
    new Brackets();
    new Negative();
    new Not();
    new Multiplication();
    new Division();
    new Mod();
    new Addition();
    new Subtraction();
    new BitAnd();
    new BitOr();
    new NotEqual();
    new Equal();
    new Less();
    new LessOrEqual();
    new More();
    new MoreOrEqual();
    new And();
    new Or();
    new IfOp();
    new ElseOp();
  }
  
  public static EntityStack get(String name) {
    return get(ID.get(name));
  }
  
  public static EntityStack get(ID name) {
    EntityStack stack = all.get(name);
    if(stack == null) throw new Error("Invalid entity name \"" + name.string
        + "\"");
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
    if(stack.isEmpty()) throw new Error("Trying to pop entity from empty "
        + name.string + " stack");
    return stack.pop();
  }

  public void push(EntityType entity) {
    stack.push(entity);
  }  
  
  public EntityType create() {
    throw new Error("Cannot create " + name);
  }
  
  public EntityType createFromString(String string) {
    throw new Error("Cannot create " + name);
  }

  public boolean isStringBased() {
    return false;
  }

  public EntityType peek() {
    if(stack.isEmpty()) throw new Error("Trying to peek entity from empty "
        + name.string + " stack");
    return stack.peek();
  }

  @Override
  public String toString() {
    return name.string;
  }
}
