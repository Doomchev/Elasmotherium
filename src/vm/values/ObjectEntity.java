package vm.values;

import ast.Entity;
import vm.values.VMValue;

public class ObjectEntity extends Entity {
  public Entity type;
  public VMValue[] fields;

  public ObjectEntity(Entity type) {
    this.type = type;
  }
}
