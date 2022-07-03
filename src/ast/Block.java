package ast;

import exception.ElException;
import exception.EntityException;
import exception.EntityException.NotFound;
import base.LinkedMap;
import java.util.LinkedList;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class Block extends Entity {
  public static class Label extends ProParameter {
    private final ID name;
    private final LinkedList<VMCommand> commands = new LinkedList<>();
    private int position = -1;

    Label(ID name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return name + "(" + position + ")";
    }
  }
  
  public Block parentBlock;
  public ID type;
  private final LinkedList<Label> labels = new LinkedList<>();
  private final LinkedList<Variable> variables = new LinkedList<>();
  private final LinkedMap<ID, Entity> entries = new LinkedMap<>();

  public Block(ID type) {
    super(0, 0);
    this.type = type;
  }
  
  // labels

  public void createLabel(ID name) {
    labels.add(new Label(name));
  }

  public Label getLabel(String name) throws EntityException {
    return getLabel(ID.get(name));
  }
  
  public Label getLabel(ID name) throws EntityException {
    for(Label label: labels) if(label.name == name) return label;
    if(parentBlock == null)
      throw new NotFound(this, "Label " + name);
    return parentBlock.getLabel(name);
  }

  public void addLabelCommand(ID id, VMCommand command) throws EntityException {
    getLabel(id).commands.add(command);
  }

  public void setLabelPosition(ID id, int pos) throws EntityException {
    getLabel(id).position = pos;
  }

  public void applyLabels() throws EntityException {
    for(Label label: labels) {
      for(VMCommand command: label.commands) {
        ID name = label.name;
        Block block = this;
        while(label.position < 0) {
          block = block.parentBlock;
          if(block == null) throw new NotFound(this, "Label " + name);
          label = block.getLabel(name);
        }
        command.setPosition(label.position);
      }
    }
  }
  
  // variables

  public void add(Variable variable) throws EntityException {
    variable.moveToBlock();
    variables.add(variable);
  }

  public Variable getVariable(String name) throws EntityException {
    return getVariable(ID.get(name));
  }
  
  public Variable getVariable(ID name) throws EntityException {
    for(Variable variable: variables)
      if(variable.name == name) return variable;
    throw new NotFound(this, "Variable " + name);
  }
  
  // properties
  
  @Override
  public Entity getBlockParameter(ID name) throws EntityException {
    Entity entity = entries.get(name);
    if(entity == null)
      throw new NotFound(this, name + " block parameter");
    return entity;
  }
  
  @Override
  public ID getID() throws EntityException {
    return type;
  }
  
  // compiling
  
  @Override
  public void compile() throws EntityException {
    if(log) println(type.string);
    try {
      currentProcessor.compileBlock(this, type);
      applyLabels();
    } catch (ElException ex) {
      throw new EntityException(this, ex.message);
    }
  }
  
  // moving functions

  @Override
  public void moveToBlock() {
    deallocate();
  }

  public static Block create(ID type) {
    allocate();
    return new Block(type);
  }

  @Override
  public void moveToCode(Code code) {
    deallocate();
    code.addLine(this);
  }
  
  // other

  @Override
  public void print(StringBuilder indent, String prefix) {
    println(indent + prefix + type.string + ":" + variables.size() + " {");
    indent.append("  ");
    for(Variable variable: variables) variable.print(indent, "");
    for(LinkedMap.Entry<ID, Entity> entry : entries)
      entry.value.print(indent, entry.key + ": ");
    indent.delete(0, 2);
  }

  public void set(ID id, Entity val) throws ElException {
    val.moveToBlock();
    entries.put(id, val);
  }
}
