package processor;

import ast.ClassEntity;
import ast.Entity;
import base.ElException;

public class ProClass extends ProParameter {
  ClassEntity entity;

  public ProClass(ClassEntity classEntity) {
    this.entity = classEntity;
  }

  @Override
  public Entity getValue() throws ElException {
    return entity;
  }
}
