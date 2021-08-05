package ast;

import base.ElException;
import base.SimpleMap;

public class Block extends Entity {
  ID type;
  public final SimpleMap<ID, Entity> entries = new SimpleMap<>();

  public final ID doID = ID.get("do"), ifID = ID.get("if")
      , conditionID = ID.get("condition"), elseID = ID.get("else");
  
  public Block(ID type) {
    this.type = type;
  }
  
  @Override
  public ID getID() {
    return blockID;
  }

  @Override
  public void moveToBlock() throws ElException {
    removeAllocation();
  }

  @Override
  public void moveToCode(Code code) {
    removeAllocation();
    code.lines.add(this);
  }

  @Override
  public void print(String indent, String prefix) {
    println(indent + prefix + type.string + " {");
    indent += "  ";
    for(SimpleMap.Entry<ID, Entity> entry : entries.entries)
      entry.value.print(indent, entry.key + ": ");
  }

  public void set(ID id, Entity val) throws ElException {
    val.moveToBlock();
    entries.put(id, val);
  }
}
