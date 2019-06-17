package parser.structure;

public class StringValue extends Value {
  public ID string;

  public StringValue(ID string) {
    this.string = string;
  }

  @Override
  public Entity getType() {
    return ClassEntity.stringClass;
  }
  
  @Override
  public Entity setTypes(Scope parentScope) {
    return ClassEntity.stringClass;
  }
  
  @Override
  public ID getID() {
    return stringID;
  }

  @Override
  void moveToStringSequence(StringSequence sequence) {
    if(!string.string.isEmpty()) sequence.chunks.add(this);
  }

  @Override
  public String toString() {
    return string.string;
  }
}
