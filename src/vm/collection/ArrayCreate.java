package vm.collection;

import ast.ClassEntity;
import ast.Entity;
import exception.ElException;
import vm.VMBase;
import vm.VMCommand;
import vm.values.I64ArrayValue;
import vm.values.ValueArrayValue;

public class ArrayCreate extends VMCommand {
  private ClassEntity elementType;

  public ArrayCreate(ClassEntity elementType, int proLine, Entity entity)
      throws ElException {
    super(proLine, entity);
    this.elementType = elementType;
  }

  public void execute() throws ElException {
    int size = (int) i64Stack[stackPointer];
    if(elementType == ClassEntity.Int) {
      valueStack[stackPointer] = new I64ArrayValue(size);
      if(log) typeStack[stackPointer] = ValueType.I64;
    } else if(elementType == ClassEntity.Object) {
      valueStack[stackPointer] = new ValueArrayValue(size);
      if(log) typeStack[stackPointer] = VMBase.ValueType.OBJECT;
    }
    currentCommand++;
  }
}
