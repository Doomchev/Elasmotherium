package ast;

import base.ElException;
import base.SimpleMap;
import java.util.HashMap;
import java.util.Stack;
import parser.Action;
import parser.ParserBase;

public class EntityStack<EntityType> extends ParserBase {
  public static final HashMap<ID, EntityStack> all = new HashMap<>();
  public static final EntityStack<ID> id;
  public static final EntityStack<Block> block;
  public static final EntityStack<Code> code;
  public static final EntityStack<ConstantValue> constant;
  public static final EntityStack<FunctionCall> call;
  public static final EntityStack<ClassEntity> classStack;

  static {
    id = new EntityStack<ID>(ID.get("id")) {
      @Override
      public boolean isStringBased() {
        return true;
      }

      @Override
      public ID create(String string, ID type) {
        return ID.get(string);
      }
    };

    final EntityStack<Value> valueStack = new EntityStack<Value>(ID.valueID) {
      @Override
      public Value create() throws ElException {
        throw new ElException("Value is abstract and cannot be created");
      }
    };

    block = new EntityStack<>(ID.blockID);
    
    constant = new EntityStack(ID.constID, valueStack) {
      @Override
      public boolean isStringBased() {
        return true;
      }

      @Override
      public StringValue create(String string, ID type) {
        return new ConstantValue(type, string);
      }
    };
    
    call = new EntityStack<FunctionCall>(ID.callID) {
      @Override
      public FunctionCall create() {
        return new FunctionCall(null);
      }
    };
    
    new EntityStack<Link>(ID.linkID) {
      @Override
      public Link create() throws ElException {
        return new Link(id.pop());
      }
    };
    
    EntityStack<Function> function = new EntityStack<Function>(ID.functionID) {
      @Override
      public Function create() throws ElException {
        return Function.create(id.pop());
      }
    };
    
    new EntityStack<Function>(ID.get("constructor"), function) {
      @Override
      public Function create() throws ElException {
        return Function.create(null);
      }      
    };
    
    code = new EntityStack<Code>(ID.codeID) {
      @Override
      public Code create() {
        allocations.add(currentAllocation);
        return new Code();
      }
    };
    
    classStack = new EntityStack<ClassEntity>(ID.classID) {
      @Override
      public ClassEntity create() throws ElException {
        return new ClassEntity(id.pop());
      }
    };
  
    EntityStack var = new EntityStack<Variable>(ID.variableID) {
      @Override
      public Variable create() throws ElException {
        return new Variable(id.pop());
      }
    };
    
    new EntityStack<Variable>(ID.get("thisvar"), var) {
      @Override
      public Variable create() throws ElException {
        return new Variable(id.pop(), true);
      }      
    };
    
    new EntityStack<Type>(ID.typeID) {
      @Override
      public Type create() throws ElException {
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
    
    new EntityStack(ID.stringID, valueStack) {
      @Override
      public boolean isStringBased() {
        return true;
      }

      @Override
      public StringValue create(String string, ID type) {
        return new StringValue(string);
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
      public ObjectEntry create() throws ElException {
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
  }
  
  public static EntityStack get(String name) throws ElException {
    return get(ID.get(name));
  }
  
  public static EntityStack get(ID name) throws ElException {
    EntityStack stack = all.get(name);
    if(stack == null) throw new ElException("Invalid entity name \""
        + name.string + "\"");
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

  public EntityType pop() throws ElException {
    if(stack.isEmpty()) throw new ElException(Action.currentAction
        , "Trying to pop entity from empty " + name.string + " stack");
    return stack.pop();
  }

  public void push(EntityType entity) {
    stack.push(entity);
  }
  
  public EntityType create() throws ElException {
    throw new ElException(name);
  }
  
  public EntityType create(String string, ID type) throws ElException {
    throw new ElException(name);
  }

  public boolean isStringBased() {
    return false;
  }

  public EntityType peek() throws ElException {
    if(stack.isEmpty()) throw new ElException(Action.currentAction
        , "Trying to peek entity from empty " + name.string + " stack");
    return stack.peek();
  }

  @Override
  public String toString() {
    return name.string;
  }
}
