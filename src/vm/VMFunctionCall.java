package vm;

public class VMFunctionCall extends VMBase {
  final int returnPoint, paramPosition, deallocation;
  
  public VMFunctionCall(int paramPosition, int deallocation) {
    this.paramPosition = paramPosition;
    this.returnPoint = currentCommand + 1;
    this.deallocation = deallocation;
  }  
}
