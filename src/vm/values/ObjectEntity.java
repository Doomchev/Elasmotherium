package vm.values;

import ast.Entity;

public class ObjectEntity extends VMValue {
  public final Entity type;
  public final VMValue[] fields;

  public ObjectEntity(Entity type, VMValue[] fields) {
    this.type = type;
    this.fields = fields;
  }

  @Override
  public VMValue create() {
    return new ObjectEntity(type, fields);
  }

  @Override
  public VMValue getField(int fieldIndex) {
    return fields[fieldIndex];
  }

  @Override
  public void setField(int fieldIndex, VMValue value) {
    fields[fieldIndex] = value;
  }

  @Override
  public String toString() {
    return type.toString();
  }
}
