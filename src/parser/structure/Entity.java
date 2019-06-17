package parser.structure;

import java.util.HashMap;
import java.util.LinkedList;
import parser.ParserBase;

public abstract class Entity extends ParserBase {
  public static HashMap<String, ID> allIDs = new HashMap<>();
  
  public static final ID idID = ID.get("id"), blockID = ID.get("block")
      , classID = ID.get("class"), codeID = ID.get("code")
      , valueID = ID.get("value"), formulaID = ID.get("formula")
      , integerID = ID.get("integer"), decimalID = ID.get("decimal")
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
      , fieldID = ID.get("field");
  
  public boolean isEmptyFunction() {
    return false;
  }

  boolean isClassField() {
    return false;
  }
  
  boolean isNumber() {
    return  false;
  }
  
  public boolean isNativeFunction() {
    return false;
  }
  
  public String getName() {
    return getID().string;
  }
  
  public ID getNameID() {
    error(getName() + " has no name");
    return null;
  }
  
  public abstract ID getID();

  public ID getFormId() {
    return getID();
  }

  int getPriority() {
    return 0;
  }

  public ClassEntity toClass() {
    error(getName() + " has no class");
    return null;
  }
  
  public LinkedList<? extends Entity> getChildren() {
    error(getName() + " has no children");
    return null;
  }

  public Entity getChild(ID id) {
    return null;
  }

  public boolean hasChild(ID id) {
    return getChild(id) != null;
  }

  public Entity getChild(int index) {
    error(getName() + " is not a function call");
    return null;
  }
  
  public Entity getType() {
    return null;
  }

  public Entity getReturnType(Scope parentScope) {
    setTypes(parentScope);
    return null;
  }

  Entity getFieldType(ID fieldName) {
    error("Cannot get field types for " + getName());
    return null;
  }

  public FunctionCall toCall() {
    return null;
  }
  
  public Entity toValue() {
    return this;
  }

  public Entity setTypes(Scope parentScope) {
    return null;
  }

  public Entity setCallTypes(LinkedList<Entity> parameters, Scope parentScope) {
    return setTypes(parentScope);
  }
  
  public Variable createVariable(Scope parentScope) {
    return null;
  }

  public void addToScope(Scope scope) {
  }
  
  public void move(Entity entity) {
    error("Cannot insert anything into " + getName());
  }

  void moveToCode(Code code) {
    error("Cannot insert " + getName() + " into code");
  }

  void moveToBlock(Block block) {
    error("Cannot insert " + getName() + " into block");
  }

  void moveToFormula(Formula formula) {
    error("Cannot insert " + getName() + " into formula");
  }

  void moveToFunctionCall(FunctionCall call) {
    error("Cannot insert " + getName() + " into function call");
  }

  void moveToClass(ClassEntity classEntity) {
    error("Cannot insert " + getName() + " into class");
  }

  void moveToFunction(Function function) {
    error("Cannot insert " + getName() + " into function");
  }

  void moveToVariable(Variable variable) {
    error("Cannot insert " + getName() + " into variable");
  }

  void moveToType(Type type) {
    error("Cannot insert " + getName() + " into type");
  }

  void moveToParameters(Parameters parameters) {
    error("Cannot insert " + getName() + " into parameters");
  }

  void moveToStringSequence(StringSequence aThis) {
    error("Cannot insert " + getName() + " into string sequence");
  }

  void moveToMap(MapEntity aThis) {
    error("Cannot insert " + getName() + " into map");
  }

  void moveToObjectEntry(ObjectEntry entry) {
    error("Cannot insert " + getName() + " into object entry");
  }
  
  public void logScope(String indent) {
  }

  @Override
  public String toString() {
    return "";
  }
}
