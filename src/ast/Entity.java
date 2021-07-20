package ast;

import base.ElException;
import java.util.HashMap;
import parser.ParserBase;

public abstract class Entity extends ParserBase {
  public static HashMap<String, ID> allIDs = new HashMap<>();
  public static Function currentFunction;
  public static ClassEntity currentClass;
  public static final ID idID = ID.get("id"), blockID = ID.get("block")
      , classID = ID.get("class"), codeID = ID.get("code")
      , valueID = ID.get("value"), formulaID = ID.get("formula")
      , functionID = ID.get("function"), callID = ID.get("call")
      , listID = ID.get("list"), mapID = ID.get("map")
      , mapEntryID = ID.get("mapEntry"), objectID = ID.get("object")
      , entryID = ID.get("entry"), parametersID = ID.get("parameters")
      , stringID = ID.get("string"), stringSequenceID = ID.get("stringSequence")
      , typeID = ID.get("type"), linkID = ID.get("link")
      , returnID = ID.get("return"), parentID = ID.get("parent")
      , classParameterID = ID.get("classParameter"), constID = ID.get("const")
      , definitionID = ID.get("definition"), thisID = ID.get("this")
      , variableID = ID.get("variable"), nativeID = ID.get("native")
      , fieldID = ID.get("field"), moduleID = ID.get("module")
      , lineID = ID.get("line");
  
  public String getName() {
    return getID().string;
  }
  
  public abstract ID getID();
  
  public static final byte VALUE = -1;
  
  public byte getPriority() {
    return VALUE;
  }
  
  // type conversion

  public ClassEntity toClass() throws ElException {
    throw new ElException(toString() + " cannot be converted to class.");
  }

  public FunctionCall toCall() {
    return null;
  }

  public Function toFunction() {
    return null;
  }
  
  public Entity toValue() throws ElException {
    return this;
  }

  public Variable toVariable() {
    return null;
  }
  
  
  
  public void move(Entity entity) throws ElException {
    throw new ElException("Cannot move anything to", this);
  }

  public void moveToCode(Code code) throws ElException {
    throw new ElException(this, "code");
  }

  public void moveToBlock(Block block) throws ElException {
    throw new ElException(this, "block");
  }

  public void moveToFormula(Formula formula) throws ElException {
    throw new ElException(this, "formula");
  }

  public void moveToFunctionCall(FunctionCall call) throws ElException {
    throw new ElException(this, "function call");
  }

  public void moveToClass(ClassEntity classEntity) throws ElException {
    throw new ElException(this, "class");
  }

  public void moveToFunction(Function function) throws ElException {
    throw new ElException(this, "function");
  }

  public void moveToVariable(Variable variable) throws ElException {
    throw new ElException(this, "variable");
  }

  public void moveToType(Type type) throws ElException {
    throw new ElException(this, "type");
  }

  public void moveToParameters(Parameters parameters) throws ElException {
    throw new ElException(this, "parameters");
  }

  public void moveToStringSequence(StringSequence seq) throws ElException {
    throw new ElException(this, "string sequence");
  }

  public void moveToList(ListEntity list) throws ElException {
    throw new ElException(this, "list");
  }

  public void moveToMap(MapEntity map) throws ElException {
    throw new ElException(this, "map");
  }

  public void moveToObjectEntry(ObjectEntry entry) throws ElException {
    throw new ElException(this, "object entry");
  }

  void moveToLink(Link link) throws ElException {
    throw new ElException(this, "link");
  }
  
  
  
  public Entity createValue() {
    return null;
  }
    
  public long i64Get() throws ElException {
    throw new ElException("Cannot get i64 from ", this);
  }
  
  public void i64Set(long value) throws ElException {
    throw new ElException("Cannot set i64 of ", this);
  }
  
  public String stringGet() throws ElException {
    throw new ElException("Cannot get String from ", this);
  }
  
  public void stringSet(String value) throws ElException {
    throw new ElException("Cannot set String of ", this);
  }

  public void increment() throws ElException {
    throw new ElException("Cannot increment ", this);
  }

  @Override
  public String toString() {
    return "";
  }
  
  public void print(String indent, String prefix) {
    println(indent + prefix + toString() + ";");
  }
}
