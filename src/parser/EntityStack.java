package parser;

import ast.*;
import ast.function.*;
import base.Base;
import ast.exception.ElException;
import ast.exception.ElException.CannotCreate;
import ast.exception.ElException.MethodException;
import java.util.HashMap;
import java.util.Stack;

public class EntityStack<EntityType> extends Base {
  public static final HashMap<ID, EntityStack> all = new HashMap<>();
  public static final EntityStack<IDEntity> id;
  public static final EntityStack<Block> block;
  public static final EntityStack<Code> code;
  public static final EntityStack<ConstantValue> constant;
  public static final EntityStack<CustomFunction> function;

  static {
    id = new EntityStack<IDEntity>("id") {
      @Override
      public boolean isStringBased() {
        return true;
      }

      @Override
      public IDEntity create(String string, ID type, int textStart
          , int textEnd) {
        return new IDEntity(ID.get(string), textStart, textEnd);
      }
    };

    final EntityStack<Value> valueStack = new EntityStack<Value>("value") {
      @Override
      public Value create() throws ElException {
        throw new MethodException(this, "create"
            , "Value is abstract and cannot be created");
      }
    };

    block = new EntityStack<>("block");
    
    constant = new EntityStack("const", valueStack) {
      @Override
      public boolean isStringBased() {
        return true;
      }

      @Override
      public ConstantValue create(String string, ID type, int textStart
          , int textEnd) {
        return new ConstantValue(type, string, textStart, textEnd);
      }
    };
    
    new EntityStack<FunctionCall>("call") {
      @Override
      public FunctionCall create() {
        return new FunctionCall(null);
      }
    };
    
    EntityStack<Link> link = new EntityStack<Link>("link") {
      @Override
      public Link create() throws ElException {
        return new Link(id.pop(), false);
      }
    };
    
    new EntityStack("thislink", link) {
      @Override
      public Link create() throws ElException {
        return new Link(id.pop(), true);
      }
    };
    
    new EntityStack<This>("this") {
      @Override
      public This create() throws ElException {
        return new This();
      }
    };
    
    function = new EntityStack<CustomFunction>("function") {
      @Override
      public CustomFunction create() throws ElException {
        return StaticFunction.createStaticFunction(id.pop());
      }
    };
    
    new EntityStack<CustomFunction>("constructor", function) {
      @Override
      public CustomFunction create() throws ElException {
        return Constructor.createConstructor();
      }
    };
    
    new EntityStack<CustomFunction>("method", function) {
      @Override
      public CustomFunction create() throws ElException {
        return Method.createMethod(id.pop());
      }
    };
    
    code = new EntityStack<Code>("code") {
      @Override
      public Code create() {
        return Code.create();
      }
    };
    
    new EntityStack<ClassEntity>("class") {
      @Override
      public ClassEntity create() throws ElException {
        return ClassEntity.create(id.pop());
      }
    };
  
    EntityStack var = new EntityStack<Variable>("variable") {
      @Override
      public Variable create() throws ElException {
        return new Variable(id.pop());
      }
    };
    
    new EntityStack<Variable>("field", var) {
      @Override
      public Variable create() throws ElException {
        return new Variable(id.pop(), true);
      }      
    };
  
    new EntityStack<ClassParameter>("classParameter") {
      @Override
      public ClassParameter create() throws ElException {
        return new ClassParameter(id.pop());
      }
    };
    
    new EntityStack<Formula>("formula") {
      @Override
      public Formula create() {
        return new Formula();
      }
    };
    
    new EntityStack<Parameters>("parameters") {
      @Override
      public Parameters create() {
        return new Parameters();
      }
    };
    
    new EntityStack("string", valueStack) {
      @Override
      public boolean isStringBased() {
        return true;
      }

      @Override
      public ConstantValue create(String string, ID type, int textStart
          , int textEnd) {
        return new ConstantValue(ConstantValue.stringID, string, textStart
            , textEnd);
      }
    };
    
    new EntityStack("stringSequence", valueStack) {
      @Override
      public StringSequence create() {
        return new StringSequence();
      }
    };
    
    new EntityStack("entry", valueStack) {
      @Override
      public ObjectEntry create() throws ElException {
        return new ObjectEntry(id.pop());
      }
    };
    
    new EntityStack("list", valueStack) {
      @Override
      public ListEntity create() {
        return new ListEntity();
      }
    };
    
    new EntityStack("map", valueStack) {
      @Override
      public MapEntity create() {
        return new MapEntity();
      }
    };
  }
  
  // retrieving
  
  public static EntityStack get(String name) throws ElException {
    return get(ID.get(name));
  }
  
  public static EntityStack get(ID name) throws ElException {
    EntityStack stack = all.get(name);
    if(stack == null)
      throw new MethodException("EntityStack", "get", "Invalid entity name \""
        + name.string + "\"");
    return stack;
  }
  
  // class fields

  final ID name;
  private final Stack<EntityType> stack;
  
  // creating

  public EntityStack(String name) {
    this.name = ID.get(name);
    this.stack = new Stack<>();
    all.put(this.name, this);
  }

  public EntityStack(String name, EntityStack entityStack) {
    this.name = ID.get(name);
    this.stack = entityStack.stack;
    all.put(this.name, this);
  }
  
  // properties

  public boolean isStringBased() {
    return false;
  }
  
  // creating objects
  
  public EntityType create() throws ElException {
    throw new CannotCreate(this, name);
  }
  
  public EntityType create(String string, ID type, int textStart, int textEnd)
    throws ElException {
    throw new CannotCreate(this, name);
  }
  
  // commands

  public void clear() {
    stack.clear();
  }

  public EntityType pop() throws ElException {
    if(stack.isEmpty())
      throw new MethodException(this, "pop"
        , "Trying to pop entity from empty " + name.string + " stack");
    return stack.pop();
  }

  public void push(EntityType entity) {
    stack.push(entity);
  }

  public EntityType peek() throws ElException {
    if(stack.isEmpty())
      throw new MethodException(this, "peek"
        , "Trying to peek entity from empty " + name.string + " stack");
    return stack.peek();
  }
  
  // other

  @Override
  public String toString() {
    return name.string;
  }
}
