package processor;

import ast.Entity;
import ast.ID;
import base.ElException;

public class ProBlockParameter extends ProParameter {
  private final ID name;

  public ProBlockParameter(String name) {
    this.name = ID.get(name);
  }

  @Override
  public Entity getValue() throws ElException {
    return current.getBlockParameter(name);
  }

  @Override
  public String toString() {
    return name.string;
  }
}
