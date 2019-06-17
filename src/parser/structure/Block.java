package parser.structure;

import java.util.LinkedList;

public class Block extends Entity {
  ID type;
  public final LinkedList<Entry> entries = new LinkedList<>();

  public static class Entry {
    ID key;
    Entity value;

    public Entry(ID key, Entity value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public String toString() {
      return key + " = " + value;
    }
  }
  
  public Block(ID type) {
    this.type = type;
  }
  
  @Override
  public ID getID() {
    return blockID;
  }

  @Override
  public Entity getChild(ID id) {
    for(Entry entry : entries) if(entry.key == id) return entry.value;
    return null;
  }

  @Override
  public ID getFormId() {
    return type;
  }

  @Override
  public Entity getReturnType(Scope parentScope) {
    for(Entry entry : entries) {
      Entity returnType = entry.value.getReturnType(parentScope);
      if(type != null) return returnType;
    }
    return null;
  }

  @Override
  public Entity setTypes(Scope parentScope) {
    for(Entry entry : entries) entry.value.setTypes(parentScope);
    return null;
  }

  @Override
  public void move(Entity entity) {
    entity.moveToBlock(this);
  }

  @Override
  void moveToCode(Code code) {
    code.lines.add(this);
  }

  @Override
  public void addToScope(Scope scope) {
    for(Entry entry : entries) entry.value.addToScope(scope);
  }
  
  @Override
  public void logScope(String indent) {
    System.out.println(type.string + ":");
    indent += "  ";
    for(Entry entry : entries) {
      System.out.println(indent + entry.key.string + ":");
      entry.value.logScope(indent);
    }
    
  }

  @Override
  public String toString() {
    String str = "";
    for(Entry entry : entries) {
      if(!str.isEmpty()) str += ", ";
      str += entry.key.string + " = " + entry.value.toString();
    }
    return type.string + "(" + str + ")";
  }
}
