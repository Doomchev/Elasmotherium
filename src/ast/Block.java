package ast;

import base.ElException;
import base.SimpleMap;

public class Block extends Entity {
  private final ID type;
  private final SimpleMap<ID, Entity> entries = new SimpleMap<>();

  public Block(ID type) {
    this.type = type;
  }
  
  // processor fields
  
  @Override
  public Entity getBlockParameter(ID name) throws ElException {
    Entity entity = entries.get(name);
    if(entity == null)
      throw new ElException(name + " block parameter is not found.");
    return entity;
  }
  
  @Override
  public ID getObject() throws ElException {
    return type;
  }
  
  // processing
    
  @Override
  public void process() throws ElException {
    if(log) println(type.string);
    currentProcessor.call(this);
  }
  
  // moving functions

  @Override
  public void moveToBlock() throws ElException {
    deallocate();
  }

  @Override
  public void moveToCode(Code code) {
    deallocate();
    code.addLine(this);
  }
  
  // other

  @Override
  public void print(String indent, String prefix) {
    println(indent + prefix + type.string + " {");
    indent += "  ";
    for(SimpleMap.Entry<ID, Entity> entry : entries)
      entry.value.print(indent, entry.key + ": ");
  }

  public void set(ID id, Entity val) throws ElException {
    val.moveToBlock();
    entries.put(id, val);
  }
}
