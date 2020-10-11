package ast;

import vm.StringPush;

public class StringValue extends Value {
  public String value;

  public StringValue(String value) {
    this.value = value;
  }

  @Override
  public Entity getType() {
    return ClassEntity.stringClass;
  }
  
  @Override
  public ID getID() {
    return stringID;
  }

  @Override
  public void moveToStringSequence(StringSequence sequence) {
    if(!value.isEmpty()) sequence.chunks.add(this);
  }
  
  @Override
  public String stringGet() {
    return value;
  }
  
  @Override
  public void stringSet(String value) {
    this.value = value;
  }

  @Override
  public void toByteCode() {
    addCommand(new StringPush(value));
  }

  @Override
  public String toString() {
    return value;
  }
}
