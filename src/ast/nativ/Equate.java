package ast.nativ;

import ast.ClassEntity;
import ast.Entity;
import static ast.Entity.addCommand;
import static ast.Entity.fieldIndex;
import static ast.Entity.fieldType;
import static ast.Entity.objectIndex;
import ast.FunctionCall;
import ast.NativeFunction;
import static parser.ParserBase.error;
import vm.I64Equate;
import vm.I64FieldEquate;
import vm.StringFieldEquate;

public class Equate extends NativeFunction {
  public Equate() {
    super("equate");
  }
  
  @Override
  public void functionToByteCode(FunctionCall call) {
    Entity param0 = call.parameters.getFirst();
    fieldIndex = -1;
    param0.objectToByteCode(call);
    if(fieldIndex == -1) {
      if(fieldType == ClassEntity.i64Class) {
        addCommand(new I64Equate(objectIndex));
      } else {
        error("Equate of " + fieldType.toString() + " is not implemented.");
      }
    } else {
      if(fieldType == ClassEntity.i64Class) {
        addCommand(new I64FieldEquate(objectIndex, fieldIndex));
      } else if(fieldType == ClassEntity.stringClass) {
        addCommand(new StringFieldEquate(objectIndex, fieldIndex));
      } else if(fieldType.isNative) {
        error("Equate of " + fieldType.toString()
            + " field is not implemented.");
      } else {
        error("Equate of object field is not implemented.");
        //addCommand(new ObjectFieldEquate(objectIndex, fieldIndex));
      }

    }
  }
}
