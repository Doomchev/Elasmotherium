package parser.structure;

public class MapEntry extends Entity {
  Value key, value;
  
  @Override
  public ID getID() {
    return mapEntryID;
  }
}
