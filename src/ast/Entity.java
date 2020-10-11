package ast;

import export.Chunk;
import java.util.HashMap;
import java.util.LinkedList;
import parser.ParserBase;
import vm.Command;
import vm.I64ToString;
import vm.StringToI64;
import vm.VMBase;

public abstract class Entity extends ParserBase {
  public static HashMap<String, ID> allIDs = new HashMap<>();
  public static Function currentFunction;
  public static final ID idID = ID.get("id"), blockID = ID.get("block")
      , classID = ID.get("class"), codeID = ID.get("code")
      , valueID = ID.get("value"), formulaID = ID.get("formula")
      , i8ID = ID.get("i8"), i16ID = ID.get("i16"), i32ID = ID.get("i32")
      , i64ID = ID.get("i64"), f32ID = ID.get("f32"), f64ID = ID.get("f64")
      , functionID = ID.get("function"), callID = ID.get("call")
      , listID = ID.get("list"), mapID = ID.get("map")
      , mapEntryID = ID.get("mapEntry"), objectID = ID.get("object")
      , entryID = ID.get("entry"), parametersID = ID.get("parameters")
      , stringID = ID.get("string"), stringSequenceID = ID.get("stringSequence")
      , typeID = ID.get("type"), linkID = ID.get("link")
      , returnID = ID.get("return"), parentID = ID.get("parent")
      , classParameterID = ID.get("classParameter"), dotID = ID.get("dot")
      , definitionID = ID.get("definition"), thisID = ID.get("this")
      , variableID = ID.get("variable"), nativeID = ID.get("native")
      , fieldID = ID.get("field"), moduleID = ID.get("module")
      , lineID = ID.get("line");
  
  public static ClassEntity voidClass = new ClassEntity(ID.get("Void"), false);
  public static ClassEntity classClass = new ClassEntity(ID.get("Class"), true);
  public static ClassEntity unknownClass = new ClassEntity(ID.get("unknown")
      , false);
  public static ClassEntity i8Class = new ClassEntity(ID.get("I8"), true) {
    @Override
    public boolean isNumber() {
      return true;
    }

    @Override
    public int getClassPriority() {
      return 0;
    }
  };
  public static ClassEntity i16Class = new ClassEntity(ID.get("I16"), true) {
    @Override
    public boolean isNumber() {
      return true;
    }

    @Override
    public int getClassPriority() {
      return 1;
    }
  };
  public static ClassEntity i32Class = new ClassEntity(ID.get("I32"), true) {
    @Override
    public boolean isNumber() {
      return true;
    }

    @Override
    public int getClassPriority() {
      return 2;
    }    
  };
  public static ClassEntity i64Class = new ClassEntity(ID.get("I64"), true) {
    @Override
    public boolean isNumber() {
      return true;
    }
  
    @Override
    public Entity createValue() {
      return new I64Value(0);
    }

    @Override
    public int getClassPriority() {
      return 3;
    }    
  };
  public static ClassEntity f32Class = new ClassEntity(ID.get("F32"), true) {
    @Override
    public boolean isNumber() {
      return true;
    }

    @Override
    public int getClassPriority() {
      return 10;
    }    
  };
  public static ClassEntity f64Class = new ClassEntity(ID.get("F64"), true) {
    @Override
    public boolean isNumber() {
      return true;
    }

    @Override
    public int getClassPriority() {
      return 11;
    }    
  };
  public static ClassEntity stringClass = new ClassEntity(ID.get("String")
      , true) {
    @Override
    public Entity createValue() {
      return new StringValue("");
    }
    
    @Override
    public int getClassPriority() {
      return 20;
    }    
  };
  public static ClassEntity booleanClass = new ClassEntity(ID.get("Boolean")
      , true);
  
  public boolean isEmptyFunction() {
    return false;
  }

  public boolean isClassField() {
    return false;
  }
  
  public boolean isNumber() {
    return false;
  }
  
  public boolean isNativeFunction() {
    return false;
  }
  
  public Entity createValue() {
    return null;
  }
  
  public String getName() {
    return getID().string;
  }
  
  public ID getNameID() {
    throw new Error(getName() + " has no name");
  }
  
  public abstract ID getID();

  public ID getFormId() {
    return getID();
  }

  public Chunk getForm() {
    return null;
  }

  public int getPriority() {
    return 0;
  }

  public int getClassPriority() {
    return 30;
  }

  public ClassEntity toClass() {
    //error(getName() + " has no class");
    return null;
  }
  
  public LinkedList<? extends Entity> getChildren() {
    throw new Error(getName() + " has no children");
  }

  public Entity getChild(ID id) {
    return null;
  }
  
  public boolean hasFlag(ID id) {
    return false;
  }

  public boolean hasChild(ID id) {
    return getChild(id) != null;
  }

  public Entity getChild(int index) {
    throw new Error(getName() + " is not a function call");
  }
  
  public Entity getType() {
    return null;
  }

  Entity getFieldType(ID fieldName) {
    throw new Error("Cannot get field types for " + getName());
  }

  public int getIndex() {
    return -1;
  }

  public Chunk getCallForm() {
    return null;
  }

  public Scope getScope() {
    return null;
  }

  public FunctionCall toCall() {
    return null;
  }

  public Function toFunction() {
    return null;
  }

  public FunctionCall toFunctionCall() {
    return null;
  }
  
  public Entity toValue() {
    return this;
  }

  public Variable toVariable() {
    return null;
  }
  
  public void setIndex(int index) {
  }

  public void setConvertTo(Entity type) {
  }

  public void setTypes(Scope parentScope) {
  }

  public Entity setCallTypes(LinkedList<Entity> parameters, Scope parentScope) {
    setTypes(parentScope);
    return null;
  }

  public void addToScope(Scope scope) {
  }
  
  public void move(Entity entity) {
    throw new Error("Cannot insert anything into " + getName());
  }

  public void moveToCode(Code code) {
    throw new Error("Cannot insert " + getName() + " into code");
  }

  public void moveToBlock(Block block) {
    throw new Error("Cannot insert " + getName() + " into block");
  }

  public void moveToFormula(Formula formula) {
    throw new Error("Cannot insert " + getName() + " into formula");
  }

  public void moveToFunctionCall(FunctionCall call) {
    throw new Error("Cannot insert " + getName() + " into function call");
  }

  public void moveToClass(ClassEntity classEntity) {
    throw new Error("Cannot insert " + getName() + " into class");
  }

  public void moveToFunction(Function function) {
    throw new Error("Cannot insert " + getName() + " into function");
  }

  public void moveToVariable(Variable variable) {
    throw new Error("Cannot insert " + getName() + " into variable");
  }

  public void moveToType(Type type) {
    throw new Error("Cannot insert " + getName() + " into type");
  }

  public void moveToParameters(Parameters parameters) {
    throw new Error("Cannot insert " + getName() + " into parameters");
  }

  public void moveToStringSequence(StringSequence aThis) {
    throw new Error("Cannot insert " + getName() + " into string sequence");
  }

  public void moveToMap(MapEntity aThis) {
    throw new Error("Cannot insert " + getName() + " into map");
  }

  public void moveToObjectEntry(ObjectEntry entry) {
    throw new Error("Cannot insert " + getName() + " into object entry");
  }
  
  public long i64Get() {
    return 0;
  }
  
  public void i64Set(long value) {
  }
  
  public String stringGet() {
    return "";
  }
  
  public void stringSet(String value) {
  }

  public void toByteCode() {
  }
  
  public void functionToByteCode(FunctionCall call) {
  }
  
  public void functionToByteCode() {
  }

  public void equationByteCode() {
    throw new Error(toString() + " cannot be equated.");
  }
  
  public static void addCommand(Command command) {
    VMBase.commandNumber++;
    command.number = VMBase.commandNumber;
    if(VMBase.currentCommand == null) {
      if(VMBase.currentFunction.startingCommand == null) 
        VMBase.currentFunction.startingCommand = command;
    } else {
      VMBase.currentCommand.nextCommand = command;
    }
    if(!VMBase.gotos.isEmpty()) {
      for(Command gotoCommand : VMBase.gotos) gotoCommand.setGoto(command);
      VMBase.gotos.clear();
    }
    VMBase.currentCommand = command;
    VMBase.commands[VMBase.commandNumber] = command;
  }

  // Adds conversion command from one type to another
  public void conversion(Entity from, Entity to) {
    if(from == to || to == null) return;
    if(from == ClassEntity.i64Class) {
      if(to == ClassEntity.stringClass) {
        addCommand(new I64ToString());
        return;
      }
    } else if(from == ClassEntity.stringClass) {
      if(to == ClassEntity.i64Class) {
        addCommand(new StringToI64());
        return;        
      }
    }
    throw new Error("Conversion from " + from.toString() + " to "
        + to.toString() + " is not implemented.");
  }
  
  public void logScope(String indent) {
  }

  @Override
  public String toString() {
    return "";
  }
}
