package vm.values;

import ast.Entity;

public class ObjectEntity extends Entity {
  public final Entity type;
  public final VMValue[] fields;

  public ObjectEntity(Entity type, VMValue[] fields) {
    this.type = type;
    this.fields = fields;
  }
}
