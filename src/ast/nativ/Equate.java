package ast.nativ;

import ast.FunctionCall;
import ast.NativeFunction;

public class Equate extends NativeFunction {
  public Equate() {
    super("equate");
  }
  
  @Override
  public void toByteCode(FunctionCall call) {
    call.parameters.getLast().toByteCode();
    call.parameters.getFirst().equationByteCode();
    /*if(fieldType == ClassEntity.i64Class) {
      addCommand(new I64FieldEquate(objectIndex, fieldIndex));
    } else if(fieldType == ClassEntity.stringClass) {
      addCommand(new StringFieldEquate(objectIndex, fieldIndex));
    } else if(fieldType.isNative) {
      throw new Error("Equate of " + fieldType.toString()
          + " field is not implemented.");
    } else {
      throw new Error("Equate of object field is not implemented.");
      //addCommand(new ObjectFieldEquate(objectIndex, fieldIndex));
    }*/
  }
}
