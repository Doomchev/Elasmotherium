package parser.structure;

import vm.StringPush;

public class StringValue extends Value {
  public ID value;

  public StringValue(ID string) {
    this.value = string;
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
  void moveToStringSequence(StringSequence sequence) {
    if(!value.string.isEmpty()) sequence.chunks.add(this);
  }

  @Override
  public void toByteCode() {
    addCommand(new StringPush(value.string));
  }

  @Override
  public String toString() {
    return value.string;
  }
}
