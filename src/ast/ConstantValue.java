package ast;

import base.ElException;

public class ConstantValue extends Value {
  public static ID id = ID.get("const");
  public static ID intID = ID.get("int");
  public static ID stringID = ID.get("string");
  
  public ID type;
  public String value;

  public ConstantValue(ID type, String value) {
    this.type = type;
    this.value = value;
  }
  
  // processor fields

  @Override
  public ID getObject() throws ElException {
    return id;
  }
  
  @Override
  public ClassEntity getType() throws ElException {
    if(type == intID) return ClassEntity.Int;
    if(type == stringID) return ClassEntity.String;
    throw new ElException("Unknown const type " + type);
  }
  
  @Override
  public String getStringValue() throws ElException {
    return value;
  }
  
  // moving functions

  @Override
  public void moveToStringSequence(StringSequence sequence) {
    if(!value.isEmpty()) sequence.chunks.add(this);
  }
  
  // other

  @Override
  public String toString() {
    return type == stringID ? "\"" + value + "\"" : type + "(" + value + ")";
  }
}
