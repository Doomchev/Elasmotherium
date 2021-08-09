package ast;

import vm.VMValue;

public class ObjectEntity extends Entity {
  public Entity type;
  public VMValue[] fields;

  public ObjectEntity(Entity type) {
    this.type = type;
  }
}
