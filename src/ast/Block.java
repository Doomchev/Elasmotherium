package ast;

import java.util.LinkedList;
import vm.Command;
import vm.IfFalseGoto;
import vm.VMBase;
import vm.VMGoto;

public class Block extends Entity {
  ID type;
  public final LinkedList<Entry> entries = new LinkedList<>();

  public final ID doID = ID.get("do"), ifID = ID.get("if")
      , conditionID = ID.get("condition"), elseID = ID.get("else");
  
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
  public void move(Entity entity) {
    entity.moveToBlock(this);
  }

  @Override
  public void moveToCode(Code code) {
    code.lines.add(this);
  }

  @Override
  public void resolveLinks(Variables variables) {
    for(Entry entry : entries) entry.value.resolveLinks(variables);
  }

  @Override
  public void toByteCode() {
    if(type == doID) {
      Command command = VMBase.currentCommand;
      getChild(codeID).toByteCode();
      addCommand(new VMGoto(command.nextCommand));
    } else if(type == ifID) {
      getChild(conditionID).toByteCode();
      Command ifFalse = new IfFalseGoto();
      addCommand(ifFalse);
      getChild(codeID).toByteCode();
      Entity elseCode = getChild(elseID);
      if(elseCode != null) {
        Command thenGoto = new VMGoto();
        addCommand(thenGoto);
        VMBase.gotos.add(ifFalse);
        VMBase.currentCommand = null;
        elseCode.toByteCode();
        VMBase.gotos.add(thenGoto);
      } else {
        VMBase.gotos.add(ifFalse);
      }
    } else {
      throw new Error(type.string + " is not implemented.");
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
